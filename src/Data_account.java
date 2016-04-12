import java.util.Hashtable;

public class Data_account
{
	public String __name;//账号名称189519602#1
	public String __path;//账号路径D:\World of Warcraft\WTF\Account\189519602#1
	// public boolean __online = true;
	public int __num_file = 0;
	public int __num_dir = 0;
	public int __total_file = 0;
	public int __total_dir = 0;
	public Hashtable<String, Long> __files = new Hashtable<String, Long>();//文件列表
	public Hashtable<String, Long> __dirs = new Hashtable<String, Long>();//文件夹列表
	public String __lastmodify = "bonepeople";
	
	//Data_account(){}
	
	
	Data_account(String __name)
	{
		this.__name = __name;
	}
	
	
	public void add(String __path_file, long __time, boolean __isDirectory)
	{
		if(__isDirectory)
		{
			__dirs.put(__path_file,new Long(__time));
			__num_dir ++;
		}
		else
		{
			__files.put(__path_file,new Long(__time));
			__num_file ++;
		}
	}
	
	
	public void add(String __value,boolean __isDirectory)
	{
		String[] __temp_string =  __value.split("\\|");
		if(__isDirectory)
		{
			__dirs.put(__temp_string[0],new Long(__temp_string[1]));
			__num_dir ++;
		}
		else
		{
			__files.put(__temp_string[0],new Long(__temp_string[1]));
			__num_file ++;
		}
	}
	
	
	public void clear()
	{
		this.__num_file = 0;
		this.__num_dir = 0;
		this.__total_file = 0;
		this.__total_dir = 0;
		this.__lastmodify = "bonepeople";
		this.__files.clear();
		this.__dirs.clear();
	}
	
	
	
	/*
	public String toString()
	{
		return __path + "|" + __modify_time;
	}
	*/
}