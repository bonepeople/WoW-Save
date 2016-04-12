import java.util.ArrayList;
import java.io.File;
import org.json.JSONObject;

public class Thread_actor extends Thread implements Client_ui
{
	private String __account_name;
	private String __type;
	
	Thread_actor(String __name,String __type)
	{
		this.__account_name = __name;
		this.__type = __type;
	}
	
	
	public void run()
	{
		__frame_main.__button_path.setEnabled(false);
		__frame_main.__button_fresh.setEnabled(false);
		__frame_main.__panel_accounts.get(__account_name).set_wait(true);
		
		
		
		switch(this.__type)
		{
		case "上传":
			System.out.println(this.__type);
			Bonepeople.labelbutton_click_upload(__account_name,__frame_main,__tasks);
			
			break;
		case "下载":
			System.out.println(this.__type);
			Bonepeople.labelbutton_click_download(__account_name,__frame_main,__tasks);
			
			break;
		case "同步":
			System.out.println(this.__type);
			
			break;
		case "刷新":
			System.out.println(this.__type);
			Bonepeople.labelbutton_click_fresh(__account_name,__frame_main);
		}
		__frame_main.__thread_actor = null;
		__frame_main.__button_path.setEnabled(true);
		__frame_main.__button_fresh.setEnabled(true);
		__frame_main.__panel_accounts.get(__account_name).set_wait(false);
	}
	/*
	private ArrayList<Data_task> work(ArrayList<Data_task> __temp_tasks)
	{
		int __temp_wrong = 0;
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
				__temp_task.__seccessful = task_bone(__temp_task);
			}
			
			if(__temp_task.__seccessful)
			{
				__frame_main.__panel_accounts.get(__account_name).set_status("已完成操作：" + __temp_i + "/" + __temp_tasks.size());
			}
			else
			{
				__temp_wrong ++;
				__tasks_wrong.add(__temp_task);
			}
		}
		return __tasks_wrong;
	}
	
	
	
	private boolean task_creatonlinedir(Data_task __task)
	{
		String __json_string;
		__json_string = Bonepeople.sendGet(Data_url.get_create_folder(__task.__task_value1));
		
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
	
	
	private boolean task_creatlocaldir(Data_task __task)
	{
		File folder = new File(__task.__task_value1);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}
	
	
	private boolean task_upload(Data_task __task)
	{
		int __temp_status = 0;
		__temp_status = Bonepeople.file_upload(__task.__task_value1,__task.__task_value2);
		if(__temp_status == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	private boolean task_download(Data_task __task)
	{
		int __temp_i = Bonepeople.file_download(__task.__task_value1,__task.__task_value2,__task.__task_time);
		if(__temp_i == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	private boolean task_bone(Data_task __task)
	{
		int __temp_status = Bonepeople.file_upload(__account_name + "/" + __account_name + ".bone",__account_name + ".bone");
		if(__temp_status == 1)
		{
			// __frame_main.__data_account_online.get(__account_name).clear();//已经包含在read_FileData中了
			Bonepeople.read_FileData(__account_name,__frame_main);
			
			//从网盘获取modify时间，避免重复下载
			String __json_string;
			String __online_time = "bonepeople";
			
			__json_string = Bonepeople.sendGet(Data_url.get_metadata(__account_name + "/" + __account_name + ".bone"));
			
			if(Bonepeople.json_read(__json_string))
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
	*/
}