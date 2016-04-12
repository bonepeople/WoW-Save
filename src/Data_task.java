
public class Data_task
{
	public String __task_type = "";//"创建网盘文件夹","上传","创建本地文件夹","下载","上传配置文件"
	public String __file_path = "";
	public String __task_value1 = "";
	public String __task_value2 = "";
	public long __task_time = 0L;
	public boolean __seccessful = false;
	
	//task	创建目录
	// __json_string = sendGet(Data_url.get_create_folder(__account_name + "/" + __temp_path.replaceAll("\\\\","/")));
	
	//task  上传.replaceAll("\\\\","/")
	// __temp_status = file_upload(__account_name + "/" + __temp_path.replaceAll("\\\\","/"),__bone_local.__path + "\\" + __temp_path);
	
	//下载
	// int __temp_i = file_download("/" + __account_name + "/" + __account_name + ".bone",__account_name + ".bone",System.currentTimeMillis());
	
	
	
	Data_task(String __type,String __path,String __value1,String __value2)
	{
		this.__task_type = __type;
		this.__file_path = __path;
		this.__task_value1 = __value1;
		if(__value2 != null)this.__task_value2 = __value2;
	}
	
	
	Data_task(String __type,String __path,String __value1,String __value2,long __time)
	{
		this.__task_type = __type;
		this.__file_path = __path;
		this.__task_value1 = __value1;
		if(__value2 != null)this.__task_value2 = __value2;
		this.__task_time = __time;
	}
	
	
	public String toString()
	{
		return __task_type + "|" + __task_value1 + "|" + __task_value2;
	}
	
}