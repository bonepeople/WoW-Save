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
			if(__event.getActionCommand().equalsIgnoreCase("����"))
			{
				System.out.println("start");
				
			}
			if(__event.getActionCommand().equalsIgnoreCase("�˳�"))
			{
				System.exit(0);
			}
		}
		if(this.__frame_name.equalsIgnoreCase("main"))
		{
			//	JDK7֮ǰ�İ汾��֧�ֽ�String���͵�������Ϊswitch������
			switch(__event.getActionCommand())
			{
			case"��װĿ¼":
				System.out.println("��װĿ¼");
				Bonepeople.button_click_path(__frame_main);
				break;
				
			case"ˢ��":
				System.out.println("ˢ��");
				Bonepeople.button_click_fresh(__frame_main);
				break;
				
			case"�˳�":
				System.out.println("�˳�");
				System.exit(0);
			}
		}
	}
	
	
	
	
	
	
}