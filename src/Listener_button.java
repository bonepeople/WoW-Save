import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener_button implements ActionListener,Client_ui
{
	
	String __frame_name = "";
	
	
	Listener_button(String __frame_name)
	{
		this.__frame_name = __frame_name;
	}
	
	public void actionPerformed(ActionEvent __event)
	{
		//System.out.println(__event.getSource());
		System.out.println(this.__frame_name);
		if(this.__frame_name.equalsIgnoreCase("login"))
		{
			if(__event.getActionCommand().equalsIgnoreCase("连接"))
			{
				System.out.println("start");
				
			}
			if(__event.getActionCommand().equalsIgnoreCase("退出"))
			{
				System.exit(0);
			}
		}
		if(this.__frame_name.equalsIgnoreCase("main"))
		{
			//	JDK7之前的版本不支持将String类型的数据作为switch的条件
			switch(__event.getActionCommand())
			{
			case"安装目录":
				System.out.println("安装目录");
				Bonepeople.button_click_path(__frame_main);
				break;
				
			case"刷新":
				System.out.println("刷新");
				Bonepeople.button_click_fresh(__frame_main);
				break;
				
			case"退出":
				System.out.println("退出");
				System.exit(0);
			}
		}
	}
	
	
	
	
	
	
}