import java.awt.event.*;
import javax.swing.JLabel;

public class Listener_mouse implements MouseListener,Client_ui
{
	
	
	public void mouseClicked(MouseEvent __e)
	{
		
		Label_button __temp_label = (Label_button)__e.getComponent();
		System.out.println(__temp_label.__account_name);
		if(__temp_label.__active)
		{
			if(__frame_main.__thread_actor == null)//��ȷ��û���κ�����ִ�е�����
			{
				__frame_main.__thread_actor = new Thread_actor(__temp_label.__account_name,__temp_label.__type);
				
				switch(__temp_label.__type)
				{
				case "�ϴ�":
					__frame_main.__thread_actor.start();
					// Bonepeople.labelbutton_click_upload(__temp_label.__account_name,__frame_main,__tasks);
					
					break;
				case "����":
					__frame_main.__thread_actor.start();
					// Bonepeople.labelbutton_click_download(__temp_label.__account_name,__frame_main,__tasks,__frame_main.__thread_actor);
					
					break;
				case "ͬ��":
					// Bonepeople.labelbutton_click_sync(__temp_label.__account_name,__frame_main,__tasks,__frame_main.__thread_actor);
					__frame_main.__panel_accounts.get(__temp_label.__account_name).set_status("�˹�����ʱ������");
					__frame_main.__thread_actor = null;
					
					break;
				case "ˢ��":
					__frame_main.__thread_actor.start();
				}
			}
			else
				__frame_main.__panel_accounts.get(__temp_label.__account_name).set_status("Ŀǰ������������������ִ�У����Ժ����ԡ�");
		}
		else
		{
			//nothing
		}
	}
	public void mouseEntered(MouseEvent __e)
	{
		// System.out.println("���룺x=" + __e.getX() + "��y=" + __e.getY());
	}
	public void mouseExited(MouseEvent __e)
	{
		// System.out.println("�뿪��x=" + __e.getX() + "��y=" + __e.getY());
	}
	public void mousePressed(MouseEvent __e)
	{
		// System.out.println("���£�x=" + __e.getX() + "��y=" + __e.getY());
	}
	public void mouseReleased(MouseEvent __e)
	{
		// System.out.println("�ͷţ�x=" + __e.getX() + "��y=" + __e.getY());
		//mouseClicked(__e);
	}
	public void mouseMoved(MouseEvent __e)
	{
		// System.out.println("�ƶ���x=" + __e.getX() + "��y=" + __e.getY());
	}
	public void mouseDragged(MouseEvent __e)
	{
		// System.out.println("��ק��x=" + __e.getX() + "��y=" + __e.getY());
	}
}