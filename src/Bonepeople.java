
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Enumeration;

import org.json.JSONObject;
import org.json.JSONArray;
public class Bonepeople
{
	private static String __upload_locate = "http://p5.dfs.kuaipan.cn:8080/cdlnode";//仍需处理
	private static long __time_upload = 0L;//暂时无用
	
	Bonepeople(){}
	
	static
	{
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
	}
	
	public static void button_click_fresh(Frame_main __frame_main)
	//主窗体的“刷新”按钮
	//清空主窗体里之前所有的Panel_account，根据文本框所给路径生成新的Panel_account。
	{
		//更新目录后需要将之前已经创建的JPanel清除
		__frame_main.clear();
		
		String __root_path = __frame_main.__text_path.getText() + "\\WTF\\Account";
		File __file_root = new File(__root_path);
		File[] __files_account = __file_root.listFiles();
		if(__files_account != null)
		{
			__frame_main.__account_saved = get_accounts();
			
			if(__frame_main.__account_saved.contains("v1.0.2"))
			{
				for(int __temp_i = 0;__temp_i < __files_account.length;__temp_i++)
				{
					if (__files_account[__temp_i].isDirectory())
					{
						//System.out.println(__files_account[__temp_i].getName());//账号遍历
						if(__frame_main.__pp_config.getProperty(__files_account[__temp_i].getName()) != null && __frame_main.__pp_config.getProperty(__files_account[__temp_i].getName()).equalsIgnoreCase("true") || __files_account[__temp_i].getName().equalsIgnoreCase("SavedVariables"))
						//此判断是要排除某些软件产生的SavedVariables无用文件夹和在忽略列表的帐号(可以仅仅进行忽略列表是否存在的判断，取消忽略直接将帐号字段删除而不是设置为false)
						//此处也可以使用getProperty(String key, String defaultValue)方法防止空值异常
						{
							//返回true则表示忽略此帐号
							System.out.println("此帐号已被忽略：" + __files_account[__temp_i].getName());
						}
						else
						{
							__frame_main.add_account(__files_account[__temp_i].getName(), __files_account[__temp_i].getAbsolutePath());
						}
					}
				}
				__frame_main.setVisible(true);
			}
			else
			{
				__frame_main.alert("已有新版本，请更新后使用。");
				// System.out.println("已有新版本，请更新后使用。");
			}
		}
		else
		{
			System.out.println("没有读取到有效的设置文件，请确认游戏目录设置是否正确。");
		}
	}
	
	
	public static void button_click_path(Frame_main __frame_main)
	{
		if(__frame_main.change_path())
		{
			set_config(__frame_main.__pp_config);
			button_click_fresh(__frame_main);
		}
	}
	
	
	public static void labelbutton_click_upload(String __account_name, Frame_main __frame_main, ArrayList<Data_task> __tasks)
	{
		fresh_account_data_online(__account_name,__frame_main);
		fresh_account_data_local(__account_name,__frame_main);
		System.out.println("已经完成Data的刷新");
		
		Data_account __bone_online = __frame_main.__data_account_online.get(__account_name);
		Data_account __bone_local = __frame_main.__data_account_online.get("temp_bone");
		__tasks.clear();
		Data_account __bone_upload = new Data_account(__account_name);//调试使用
		Data_account __bone_unupload = new Data_account("bone_unupload");//调试使用
		
		//dirs
		for(Enumeration<String> __temp_enum = __bone_local.__dirs.keys();__temp_enum.hasMoreElements();)
		{
			String __temp_path = __temp_enum.nextElement();
			
			if(!__bone_online.__dirs.containsKey(__temp_path))
			{
				__tasks.add(new Data_task("创建网盘文件夹",__temp_path,__account_name + "/" + __temp_path.replaceAll("\\\\","/"),null));
			}
			else
			{
				__bone_upload.add(__temp_path,0L,true);
			}
		}
		//files
		for(Enumeration<String> __temp_enum = __bone_local.__files.keys();__temp_enum.hasMoreElements();)
		{
			String __temp_path = __temp_enum.nextElement();
			
			if(!__bone_online.__files.containsKey(__temp_path))
			{
				__tasks.add(new Data_task("上传",__temp_path,__account_name + "/" + __temp_path.replaceAll("\\\\","/"),__bone_local.__path + "\\" + __temp_path,__bone_local.__files.get(__temp_path).longValue()));
			}
			else
			{
				if(!__bone_local.__files.get(__temp_path).equals(__bone_online.__files.get(__temp_path)))
				{
					__tasks.add(new Data_task("上传",__temp_path,__account_name + "/" + __temp_path.replaceAll("\\\\","/"),__bone_local.__path + "\\" + __temp_path,__bone_local.__files.get(__temp_path).longValue()));
				}
				else
				{
					__bone_upload.add(__temp_path,__bone_local.__files.get(__temp_path).longValue(),false);
				}
			}
		}
		ArrayList<Data_task> __temp_tasks = work(__tasks,__account_name,__frame_main);
		//构造正确的bone文件
		for(int __temp_i = 0;__temp_i < __tasks.size();__temp_i ++)
		{
			if(__tasks.get(__temp_i).__seccessful)
			{
				switch(__tasks.get(__temp_i).__task_type)
				{
				case"创建网盘文件夹":
					__bone_upload.add(__tasks.get(__temp_i).__file_path,0L,true);
					break;
					
				case"上传":
					__bone_upload.add(__tasks.get(__temp_i).__file_path,__tasks.get(__temp_i).__task_time,false);
				}
			}
			else
			{
				switch(__tasks.get(__temp_i).__task_type)
				{
				case"创建网盘文件夹":
					__bone_unupload.add(__tasks.get(__temp_i).__file_path,0L,true);
					break;
					
				case"上传":
					__bone_unupload.add(__tasks.get(__temp_i).__file_path,__tasks.get(__temp_i).__task_time,false);
				}
			}
		}
		
		__bone_upload.__total_file = __bone_local.__num_file;
		__bone_upload.__total_dir = __bone_local.__num_dir;
		//在本地生成bone文件
		write_FileData(__bone_upload);
		write_FileData(__bone_unupload);//测试使用
		
		//将bone文件上传到网盘
		if(task_bone(__account_name,__frame_main))
		{
			__frame_main.__account_saved.add(__account_name);//文件成功上传之后，将此账号添加到已保存账号列表中。(如果有重复的项也是可以添加的，不影响判断)
			if(__bone_unupload.__num_file != 0 || __bone_unupload.__num_dir != 0)//此处的__bone_unupload可以由__temp_tasks替代，进行错误统计
			{
				int __temp_n = __bone_unupload.__num_file + __bone_unupload.__num_dir;
				__frame_main.__panel_accounts.get(__account_name).set_status("本次操作共出现" + __temp_n + "处错误。");
			}
			else
			{
				__frame_main.__panel_accounts.get(__account_name).set_status("操作全部完成");
			}
		}
		else
		{
			__frame_main.__panel_accounts.get(__account_name).set_status("出现一个严重错误，请重新上传。");
		}
	}
	
	
	public static void labelbutton_click_download(String __account_name, Frame_main __frame_main, ArrayList<Data_task> __tasks)
	{
		fresh_account_data_online(__account_name,__frame_main);
		fresh_account_data_local(__account_name,__frame_main);
		System.out.println("已经完成Data的刷新");
		
		Data_account __bone_online = __frame_main.__data_account_online.get(__account_name);
		Data_account __bone_local = __frame_main.__data_account_online.get("temp_bone");
		__tasks.clear();
		
		//文件夹下载
		for(Enumeration<String> __temp_enum = __bone_online.__dirs.keys();__temp_enum.hasMoreElements();)
		{
			String __temp_path = __temp_enum.nextElement();
			
			if(!__bone_local.__dirs.containsKey(__temp_path))
			{
				//task	创建目录
				__tasks.add(new Data_task("创建本地文件夹",__temp_path,__bone_local.__path + "\\" + __temp_path,null));
			}
		}
		//文件下载
		for(Enumeration<String> __temp_enum = __bone_online.__files.keys();__temp_enum.hasMoreElements();)
		{
			String __temp_path = __temp_enum.nextElement();
			
			if(!__bone_local.__files.containsKey(__temp_path))
			{
				//task	下载
				__tasks.add(new Data_task("下载",__temp_path,__account_name + "/" + __temp_path.replaceAll("\\\\","/"),__bone_local.__path + "\\" + __temp_path,__bone_online.__files.get(__temp_path).longValue()));
			}
			else
			{
				if(!__bone_local.__files.get(__temp_path).equals(__bone_online.__files.get(__temp_path)))
				{
					__tasks.add(new Data_task("下载",__temp_path,__account_name + "/" + __temp_path.replaceAll("\\\\","/"),__bone_local.__path + "\\" + __temp_path,__bone_online.__files.get(__temp_path).longValue()));
				}
			}
		}
		
		ArrayList<Data_task> __temp_tasks = work(__tasks,__account_name,__frame_main);
		
		if(__temp_tasks.size() == 0)
		{
			__frame_main.__panel_accounts.get(__account_name).set_status("操作全部完成");
		}
		else
		{
			__frame_main.__panel_accounts.get(__account_name).set_status("本次操作共出现" + __temp_tasks.size() + "处错误。");
		}
	}
	
	
	public static void labelbutton_click_sync(String __account_name, Frame_main __frame_main, ArrayList<Data_task> __tasks, Thread_actor __thread_actor)
	{
		fresh_account_data_online(__account_name,__frame_main);
		fresh_account_data_local(__account_name,__frame_main);
		
		
		
		//已经获取两个账号信息，准备对比信息，一个是__account_name，一个是temp_bone
	}
	
	
	public static void labelbutton_click_fresh(String __account_name, Frame_main __frame_main)
	{
		
		String __temp_str = get_account_status(__account_name,__frame_main.__panel_accounts.get(__account_name).__path,__frame_main);
		__frame_main.__panel_accounts.get(__account_name).set_status(__temp_str);
	}
	
	
	public static void frame_init(Frame_main __frame_main)
	{
		__frame_main.setVisible(true);
		
		__frame_main.__text_path.setText("");
		__frame_main.__pp_config = get_config();
		
		if(__frame_main.__pp_config.size() != 0)
		{
			__frame_main.__text_path.setText(__frame_main.__pp_config.getProperty("wow_path"));
			button_click_fresh(__frame_main);
		}
		else
		{
			//default
			System.out.println("没有配置文件");
		}
	}
	
	
	private static Properties get_config()
	//获取config.ini中的设置信息。
	{
		FileInputStream __fis = null;
		Properties __pp = new Properties();
		
		try
		{
			__fis = new FileInputStream("config.ini");
			__pp.load(__fis);
			__fis.close();
		}
		catch(FileNotFoundException __e1)
		{
			System.out.println("file not exists");
		}
		catch(Exception __e2){__e2.printStackTrace();}
		return __pp;
	}
	
	
	private static void set_config(Properties __pp)
	//保存设置信息至config.ini。
	{
		OutputStream __fos;
		
		try
		{
			__fos=new FileOutputStream("config.ini");
			__pp.store(__fos, null);
			__fos.close();
		}
		catch(Exception __e)
		{
			__e.printStackTrace();
		}
	}
	
	
	private static String get_account_status(String __account_name, String __account_path, Frame_main __frame_main)
	{
		
		File __file_cache_local = new File(__account_path + "\\cache.md5");
		if(__file_cache_local.exists())
		{
			if(__frame_main.__account_saved.contains(__account_name))
			{
				if(fresh_account_data_online(__account_name,__frame_main))
				{
					Data_account __bone_online = __frame_main.__data_account_online.get(__account_name);
					if(__bone_online.__files.containsKey("cache.md5") && __bone_online.__total_file == __bone_online.__num_file)
					{
						long __time_local = __file_cache_local.lastModified();
						long __time_online = __bone_online.__files.get("cache.md5").longValue();
						
						if(__time_online > __time_local)
						{
							return "服务器文件为最新";
						}
						else if(__time_online == __time_local)
						{
							fresh_account_data_local(__account_name,__frame_main);
							//已经获取两个账号信息，准备对比信息，一个是__account_name，一个是temp_bone
							
							Data_account __bone_local = __frame_main.__data_account_online.get("temp_bone");
							
							//此处仅仅判断文件数量是不够的，如果人为的把本地文件增加了n个，又减少了n个，最终结果是文件一致，实际上文件已经改变。
							int __temp_i = __bone_online.__num_file - __bone_local.__num_file;
							if(__temp_i > 0)
							{
								return "本地缺少" + __temp_i + "个配置文件";
							}
							else if(__temp_i == 0)
							{
								return "云端和本地文件一致。";
							}
							else
							{
								__temp_i = 0 - __temp_i;
								return "云端缺少" + __temp_i + "个配置文件";
							}
						}
						else
						{
							return "本地文件为最新。";
						}
					}
					else
					{
						return "云端保存的配置不完整。";
					}
				}
				else
				{
					return "云端信息获取失败。";//有可能是bone丢失
				}
			}
			else
			{
				return "帐号在云端没有备份。";
			}
		}
		else
		{
			return "帐号的配置信息不完整。";
		}
	}
	
	
	private static boolean fresh_account_data_online(String __account_name, Frame_main __frame_main)
	//仅仅是确保本地保存的Data_account变量刷新为最新的，如果没有相应的信息，创建一个新的并且进行更新。
	{
		if(!__frame_main.__data_account_online.containsKey(__account_name))
		{
			__frame_main.__data_account_online.put(__account_name,new Data_account(__account_name));
		}
		__frame_main.__data_account_online.get(__account_name).__path = __frame_main.__text_path.getText() + "\\WTF\\Account\\" + __account_name;
		
		if(!__frame_main.__account_saved.contains(__account_name))//若网上不存在此账号的文件夹，就已经算是刷新完成了。
			return true;
		
		Data_account __temp_account = __frame_main.__data_account_online.get(__account_name);
		
		String __json_string;
		String __online_time = "bonepeople";
		
		__json_string = sendGet(Data_url.get_metadata(__account_name + "/" + __account_name + ".bone"));
		
		if(json_read(__json_string))
		{
			try
			{
				JSONObject __json_object = new JSONObject(__json_string);
				__online_time = __json_object.getString("modify_time");
				
				
				if(__temp_account.__lastmodify.equalsIgnoreCase(__online_time))
				{
					return true;
				}
				else
				{
					//fresh account info
					int __temp_i = file_download("/" + __account_name + "/" + __account_name + ".bone",__account_name + ".bone",System.currentTimeMillis());
					if(__temp_i == 1)
					{
						// File __file_bone = new File(__account_name + ".bone");
						
						// __temp_account.clear();//已经包含在read_FileData中了
						read_FileData(__account_name, __frame_main);
						__temp_account.__lastmodify = __online_time;
						return true;
						// __file_bone.delete();
					}
					else
					{
						System.out.println("获取账号信息失败，请重新刷新");//需要明显的告知用户
					}
				}
			}
			catch(Exception __e)
			{
				__e.printStackTrace();
			}
		}
		else
		{
			try
			{
				JSONObject __json_object = new JSONObject(__json_string);
				String __temp_str = __json_object.getString("msg");
				if(__temp_str.equalsIgnoreCase("file not exist"))
				{
					//网上的配置信息残缺，需要清除整个文件夹。并且重新刷新本地信息。
					//网上的bone文件丢失，通常是上传操作没成功。
				}
				else
				{
					System.out.println("json字符串异常：" + __json_string);
				}
			}
			catch(Exception __e)
			{
				//链接超时
				__e.printStackTrace();
			}
		}
		return false;
	}
	
	
	private static void fresh_account_data_local(String __account_name, Frame_main __frame_main)
	//刷新所给账号的本地文件信息，储存在temp_bone对应的Data_account中。
	{
		if(!__frame_main.__data_account_online.containsKey("temp_bone"))
		{
			Data_account __temp_account = new Data_account(__account_name);
			__frame_main.__data_account_online.put("temp_bone",__temp_account);
		}
		__frame_main.__data_account_online.get("temp_bone").__path = __frame_main.__text_path.getText() + "\\WTF\\Account\\" + __account_name;
		
		String __path_account = __frame_main.__data_account_online.get("temp_bone").__path;
		String __path_ingore = __path_account + "\\";
		__frame_main.__data_account_online.get("temp_bone").clear();
		file_load(__path_account, __path_ingore.replaceAll("\\\\","\\\\\\\\"), __frame_main.__data_account_online.get("temp_bone"));
	}
	
	
	private static void write_FileData(Data_account __temp_account)
	//将传入的Data_account信息写入当前目录的账号文件中。
	//获取本地帐号下文件夹和文件的统计数据，写入FileData.bone文件。
	{
		OutputStream __fos;
		Properties __pp = null;
		int __num_directory = 1;
		int __num_file = 1;
		
		__pp = new Properties();
		
		for(Enumeration<String> __temp_enum = __temp_account.__dirs.keys();__temp_enum.hasMoreElements();__num_directory ++)
		{
			String __temp_path = __temp_enum.nextElement();
			String __temp_time = __temp_account.__dirs.get(__temp_path).toString();
			String __temp_value = __temp_path + "|" + __temp_time;
			__pp.setProperty("dir" + __num_directory, __temp_value);
		}
		
		for(Enumeration<String> __temp_enum = __temp_account.__files.keys();__temp_enum.hasMoreElements();__num_file ++)
		{
			String __temp_path = __temp_enum.nextElement();
			String __temp_time = __temp_account.__files.get(__temp_path).toString();
			String __temp_value = __temp_path + "|" + __temp_time;
			__pp.setProperty("file" + __num_file, __temp_value);
		}
		
		__pp.setProperty("num_dir", String.valueOf(__num_directory - 1));
		__pp.setProperty("num_file", String.valueOf(__num_file - 1));
		__pp.setProperty("total_dir", String.valueOf(__temp_account.__total_dir));
		__pp.setProperty("total_file", String.valueOf(__temp_account.__total_file));
		

		try
		{
			__fos=new FileOutputStream(__temp_account.__name + ".bone");//有\是当前盘符根目录，没有\是当前文件夹
			__pp.store(__fos, null);
			__fos.close();
		}
		catch(Exception __e)
		{
			__e.printStackTrace();
		}
	}
	
	
	private static void read_FileData(String __account_name, Frame_main __frame_main)
	//读取所给账号名文件夹下的文件信息，保存在__frame_main.__data_account_online中，之前要确保table中已有此账号的变量。
	{
		String __file_path = __account_name + ".bone";
		FileInputStream __fis = null;
		Properties __pp = null;
		Data_account __temp_account = __frame_main.__data_account_online.get(__account_name);
		int __num_directory = 0;
		int __num_file = 0;
		// int __total_dir = 0;
		// int __total_file = 0;
		
		__pp = new Properties();
		__temp_account.clear();
		try
		{
			__fis = new FileInputStream(__file_path);
			__pp.load(__fis);
			
			__num_directory = Integer.parseInt(__pp.getProperty("num_dir"));
			__num_file = Integer.parseInt(__pp.getProperty("num_file"));
			__temp_account.__total_dir = Integer.parseInt(__pp.getProperty("total_dir"));
			__temp_account.__total_file = Integer.parseInt(__pp.getProperty("total_file"));
			
			for(int __temp_i = 1;__temp_i <=__num_directory;__temp_i ++)
			{
				__temp_account.add(__pp.getProperty("dir" + __temp_i),true);
			}
			
			for(int __temp_j = 1;__temp_j <=__num_file;__temp_j ++)
			{
				__temp_account.add(__pp.getProperty("file" + __temp_j),false);
			}
			
			__fis.close();
			
		}
		catch(NumberFormatException __e1)
		{
			//读取__num_directory和__num_file在转化为int类型时出现异常
			System.out.println("文件异常");
		}
		catch(FileNotFoundException __e2)
		{
			System.out.println("file not exists");
		}
		catch(Exception __e){__e.printStackTrace();}
	}
	
	
	private static void file_load(String __path_account, String __path_ingore, Data_account __account_files)
	//读取__account_path路径下的文件和文件夹信息，存入__account_files。在调用file_load之前请先使用clear函数清空Data_account的信息。
	{
		//System.out.println(__path_account);
		
		File __file_root = new File(__path_account);
		File[] __f1 = __file_root.listFiles();
		for(int __temp_i = 0;__temp_i < __f1.length;__temp_i++)
		{
			if (__f1[__temp_i].isDirectory())
			{
				String __temp_str_path = __f1[__temp_i].getAbsolutePath().replaceFirst(__path_ingore, "");
				__account_files.add(__temp_str_path,__f1[__temp_i].lastModified(),true);
				file_load(__f1[__temp_i].getAbsolutePath(), __path_ingore,__account_files);
			}
			else
			{
				if(__f1[__temp_i].getName().endsWith(".bak"))continue;
				String __temp_str_path = __f1[__temp_i].getAbsolutePath().replaceFirst(__path_ingore, "");
				__account_files.add(__temp_str_path,__f1[__temp_i].lastModified(),false);
			}
		}
	}
	
	
	private static String sendGet(String __url)
	{
		String __result = "";
		HttpURLConnection __connection = null;
		BufferedReader __in = null;
		System.out.println("正准备链接:" + __url);
		
		try
		{
			URL __realUrl = new URL(__url);
			// 打开和URL之间的连接
			__connection = (HttpURLConnection)__realUrl.openConnection();
			__connection.setRequestMethod("GET");
			__connection.setConnectTimeout(5000);
			// 建立实际的连接
			__connection.connect();
			/*
			// 获取所有响应头字段
			Map<String, List<String>> __map = __connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String __key : __map.keySet())
			{
				System.out.println(__key + "--->" + __map.get(__key));
			}
			*/
			// 定义 BufferedReader输入流来读取URL的响应
			
			int __status = __connection.getResponseCode();
			if(__status ==200)
			{
				__in = new BufferedReader(new InputStreamReader(__connection.getInputStream()));
			}
			else
			{
				__in = new BufferedReader(new InputStreamReader(__connection.getErrorStream()));
			}
			String __line;
			while ((__line = __in.readLine()) != null)
			{
				__result += __line;
			}
		 }
		catch (Exception __e)
		{
			System.out.println("发送GET请求出现异常！");
			__result = __e.getMessage();
			__e.printStackTrace();
		}
		finally
		{
			if (__connection != null)__connection.disconnect();
			try
			{
				if (__in != null){__in.close();}
			}
			catch (Exception __e2)
			{
				__e2.printStackTrace();
			}
		}
		System.out.println("链接结束:" + __result);
		return __result;
	}
	
	
	private static boolean json_read(String __json_string)
	{
		try
		{
			JSONObject __json_object = new JSONObject(__json_string);
			return !__json_object.has("msg");
		}
		catch(Exception __e)
		{
			return false;
		}
	}
	
	
	private static ArrayList<String> get_accounts()
	{
		String __json_string;
		ArrayList<String> __result = new ArrayList<String>();
		
		__json_string = sendGet(Data_url.get_metadata(""));
		if(json_read(__json_string))
		{
			try
			{
				System.out.println(__json_string);
				JSONObject __json_object = new JSONObject(__json_string);
				
				JSONArray __json_array = __json_object.getJSONArray("files");
				
				for(int __temp_i = 0;__temp_i < __json_array.length();__temp_i ++)
				{
					if(__json_array.getJSONObject(__temp_i).getString("type").equalsIgnoreCase("folder"))
					{
						__result.add(__json_array.getJSONObject(__temp_i).getString("name"));
					}
				}
			}
			catch(Exception __e)
			{
				__e.printStackTrace();
			}
		}
		else
		{
			System.out.println("json字符串异常：" + __json_string);
		}
		return __result;
	}
	
	
	private static int file_download(String __path_download, String __path_file, long __file_time)//("/TradeLog.txt","TradeLog.txt",1435460740282)
	{
		URL __realUrl = null;
		HttpURLConnection __connection = null;
		ByteArrayOutputStream __os = new ByteArrayOutputStream();
		InputStream __is = null;
		int __status = 0;
		//long __file_length = 0L;
		
		try
		{
			__realUrl = new URL(Data_url.get_download_file(__path_download));
			__connection = (HttpURLConnection)__realUrl.openConnection();
			__connection.setRequestMethod("GET");
			
			__status = __connection.getResponseCode();
			if(__status == 200)
			{
				__is = __connection.getInputStream();
				//__file_length = __connection.getContentLengthLong();
				//System.out.println("数据总长度为：" + __file_length);
				bufferedWriting(__os, __is);
				//System.out.println(__os);	
				File __file_download = new File(__path_file);//"trade" + System.currentTimeMillis() + ".txt"
				//文件不存在则创建文件，文件已存在则覆盖文件，修改时间会以最后给的之间为准。
				FileOutputStream __os_file = new FileOutputStream(__file_download);
				__os_file.write(__os.toByteArray());
				__os_file.close();
				__file_download.setLastModified(__file_time);
				__status = 1;
				System.out.println("下载了" + __path_file);
			}
			else
			{
				System.out.println("HTTP下载返回值异常：" + __status);
				//System.out.println(getStringDataFromConnection(__connection));
			}
		}
		catch(MalformedURLException __e1)
		{
			//from __realUrl = new URL(Data_url.get_download_file(__path_download));
			__e1.printStackTrace();
		}
		catch(ProtocolException __e2)
		{
			//from __connection.setRequestMethod("GET");
			__e2.printStackTrace();
		}
		catch(FileNotFoundException __e3)
		{
			//from FileOutputStream __os_file = new FileOutputStream(__file_download);
			__e3.printStackTrace();
		}
		catch(IOException __e4)
		{
			//from __connection = (HttpURLConnection)__realUrl.openConnection();
			//from int __temp_code = __connection.getResponseCode();
			//from __is = __connection.getInputStream();
			//from __os_file.write(__os.toByteArray());
			//from __os_file.close();
			__e4.printStackTrace();
		}
		catch(IllegalArgumentException __e5)
		{
			//from __file_download.setLastModified(__file_time);
			__e5.printStackTrace();
		}
		catch(Exception __e)
		{
			System.out.println("other exception catched");
			__e.printStackTrace();
		}
		finally
		{
			if (__connection != null)__connection.disconnect();
			if(__is != null)
				try
				{
					__is.close();
				}catch(IOException __e){}
		}
		
		return __status;
	}
	
	
	private static int file_upload(String __path_upload, String __path_file)//("/TradeLog.txt","TradeLog.txt")
	{
		//获取上传地址
		//根据系统时间判断过期问题
		URL __realUrl = null;
		HttpURLConnection __connection = null;
		FileInputStream __is = null;
		int __status = 0;
		
		try
		{
			File __file_upload = new File(__path_file);
			__is = new FileInputStream(__file_upload);
			
			String __url_upload_file = Data_url.get_upload_file(__upload_locate,__path_upload);
			
			System.out.println(__url_upload_file);
			__realUrl = new URL(__url_upload_file);
			__connection = (HttpURLConnection)__realUrl.openConnection();
			__connection.setConnectTimeout(5000);
			__connection.setReadTimeout(5000);
			__connection.setRequestMethod("POST");
			multipartUploadData(__connection, __is);
			
			
			__status = __connection.getResponseCode();
			if(__status == 200)
			{
				BufferedReader __in = null;
				__in = new BufferedReader(new InputStreamReader(__connection.getInputStream()));
				String __result = "";
				String __line;
				while ((__line = __in.readLine()) != null)
				{
					__result += __line;
				}
				System.out.println("result=" + __result);
				__status = 1;
			}
			else
			{
				BufferedReader __in_err = null;
				__in_err = new BufferedReader(new InputStreamReader(__connection.getErrorStream()));
				String __result_err = "";
				String __line_err;
				while ((__line_err = __in_err.readLine()) != null)
				{
					__result_err += __line_err;
				}
				System.out.println("HTTP上传返回值异常：" + __status);
				System.out.println("result=" + __result_err);
			}
		}
		catch(NullPointerException __e1)
		{
			//from File __file_upload = new File(__path_file);
			__e1.printStackTrace();
		}
		catch(FileNotFoundException __e2)
		{
			//from FileInputStream __is = new FileInputStream(__file_upload);
			__e2.printStackTrace();
		}
		catch(MalformedURLException __e3)
		{
			//from __realUrl = new URL(__url_upload_file);
			__e3.printStackTrace();
		}
		catch(ProtocolException __e4)
		{
			//from __connection.setRequestMethod("POST");
			__e4.printStackTrace();
		}
		catch(IOException __e5)
		{
			//from __connection = (HttpURLConnection)__realUrl.openConnection();
			//from __in = new BufferedReader(new InputStreamReader(__connection.getInputStream()));
			__e5.printStackTrace();
		}
		catch(Exception __e)
		{
			System.out.println("other exception catched");
			__e.printStackTrace();
		}
		finally
		{
			if (__connection != null)__connection.disconnect();
			if(__is != null)
				try
				{
					__is.close();
				}catch(IOException __e){}
		}
		return __status;
	}
	
	
	private static void multipartUploadData(HttpURLConnection __con, FileInputStream __datastream) throws Exception
	{
		__con.setDoOutput(true);
		String __boundary = "--B_O_N_E_P_E_O_P_L_E--" + System.currentTimeMillis();
		__con.setRequestProperty("connection", "keep-alive");
        __con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + __boundary);
        __con.setRequestProperty("Cache-Control", "no-cache");
		__boundary = "--" + __boundary;
		StringBuffer __str_buf = new StringBuffer();
        __str_buf.append(__boundary);
        __str_buf.append("\r\n");
        //__str_buf.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "\"\r\n");(filename对上传文件的名字没影响)
        __str_buf.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + "myfile" + "\"\r\n");
        __str_buf.append("Content-Type: application/octet-stream\r\n\r\n");
        String __endStr = "\r\n" + __boundary + "--\r\n";
        byte[] __endData = __endStr.getBytes();
        OutputStream __os = null;
        try
		{
			__con.connect();
            __os = __con.getOutputStream();
            __os.write(__str_buf.toString().getBytes());
            
    		bufferedWriting(__os, __datastream);
	        __os.write(__endData);
	        //System.out.println("已经完成数据传输");
        }
		catch(Exception __e)
		{
			throw __e;
        	// __e.printStackTrace();
        }
		finally
		{
        	try
			{
        		if (__os != null)
				{
			        __os.flush();
			        __os.close();
	        	}
        	}
			catch(Exception __e){}
        }
	}
	
	
	private static void bufferedWriting(OutputStream __to_write, InputStream __to_read)
	{
		int __len = 0;
		byte[] __buf = new byte[4048];
		try
		{
	        while((__len = __to_read.read(__buf)) != -1)
			{
	        	__to_write.write(__buf, 0, __len);
	        }	        
        } catch (Exception __e)
		{
        	__e.printStackTrace();
        }
	}
	
	
	private static ArrayList<Data_task> work(ArrayList<Data_task> __temp_tasks, String __account_name, Frame_main __frame_main)
	{
		int __temp_wrong = 0;//暂时无用
		ArrayList<Data_task> __tasks_wrong = new ArrayList<Data_task>();
		
		for(int __temp_i = 1;__temp_i <= __temp_tasks.size();__temp_i ++)
		{
			Data_task __temp_task = __temp_tasks.get(__temp_i - 1);
			System.out.println(__temp_i + "/" + __temp_tasks.size() + "|" + __temp_task.toString());
			
			switch(__temp_task.__task_type)
			{
			case"创建网盘文件夹":
				__temp_task.__seccessful = task_creatonlinedir(__temp_task);
				break;
				
			case"上传":
				__temp_task.__seccessful = task_upload(__temp_task);
				break;
				
			case"创建本地文件夹":
				__temp_task.__seccessful = task_creatlocaldir(__temp_task);
				break;
				
			case"下载":
				__temp_task.__seccessful = task_download(__temp_task);
				break;
				
			case"上传配置文件":
				__temp_task.__seccessful = task_bone(__account_name,__frame_main);
			}
			
			
			__frame_main.__panel_accounts.get(__account_name).set_status("已完成操作：" + __temp_i + "/" + __temp_tasks.size());
			if(!__temp_task.__seccessful)
			{
				__temp_wrong ++;
				__tasks_wrong.add(__temp_task);
			}
		}
		return __tasks_wrong;
	}
	
	
	private static boolean task_creatonlinedir(Data_task __task)
	{
		String __json_string;
		__json_string = sendGet(Data_url.get_create_folder(__task.__task_value1));
		
		try
		{
			JSONObject __json_object = new JSONObject(__json_string);
			if(__json_object.getString("msg").equalsIgnoreCase("ok"))
				return true;
			else
				return false;
		}
		catch(Exception __e)
		{
			__e.printStackTrace();
			return false;
		}
	}
	
	
	private static boolean task_creatlocaldir(Data_task __task)
	{
		File folder = new File(__task.__task_value1);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}
	
	
	private static boolean task_upload(Data_task __task)
	{
		int __temp_status = 0;
		__temp_status = file_upload(__task.__task_value1,__task.__task_value2);
		if(__temp_status == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	private static boolean task_download(Data_task __task)
	{
		int __temp_i = file_download(__task.__task_value1,__task.__task_value2,__task.__task_time);
		if(__temp_i == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	private static boolean task_bone(String __account_name, Frame_main __frame_main)
	{
		int __temp_status = file_upload(__account_name + "/" + __account_name + ".bone",__account_name + ".bone");
		if(__temp_status == 1)
		{
			// __frame_main.__data_account_online.get(__account_name).clear();//已经包含在read_FileData中了
			read_FileData(__account_name,__frame_main);
			
			//从网盘获取modify时间，避免重复下载
			String __json_string;
			String __online_time = "bonepeople";
			
			__json_string = sendGet(Data_url.get_metadata(__account_name + "/" + __account_name + ".bone"));
			
			if(json_read(__json_string))
			{
				try
				{
					JSONObject __json_object = new JSONObject(__json_string);
					__online_time = __json_object.getString("modify_time");
					__frame_main.__data_account_online.get(__account_name).__lastmodify = __online_time;
					return true;
				}
				catch(Exception __e)
				{
					__e.printStackTrace();
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			__frame_main.__data_account_online.remove(__account_name);
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	public static String test()
	{
		
		
		
		
		String __json_string;
		
		__json_string = sendGet(Data_url.get_upload_locate());
		System.out.println(__json_string);
		
		
		file_upload("/TradeLog.txt","TradeLog.txt");//("/TradeLog.txt","TradeLog.txt")
		
		
		//System.out.println(Data_url.get_metadata("189519602#2/189519602#1.bone"));
		
		
		/*
		__json_string = sendGet(Data_url.get_metadata("189519602#2/189519602#1.bone"));
		System.out.println(__json_string);
		
		if(json_read(__json_string))
		{
			try
			{
				JSONObject __json_object = new JSONObject(__json_string);
				return __json_object.getString("modify_time");
			}
			catch(Exception __e)
			{
				__e.printStackTrace();
			}
		}
		else
		{
			System.out.println("json字符串异常：" + __json_string);
			//file not exist
		}
		
		*/
		
		return "bad";
		
	}
	
	
}