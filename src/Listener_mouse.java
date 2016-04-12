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
			if(__frame_main.__thread_actor == null)//先确保没有任何正在执行的任务
			{
				__frame_main.__thread_actor = new Thread_actor(__temp_label.__account_name,__temp_label.__type);
				
				switch(__temp_label.__type)
				{
				case "上传":
					__frame_main.__thread_actor.start();
					// Bonepeople.labelbutton_click_upload(__temp_label.__account_name,__frame_main,__tasks);
					
					break;
				case "下载":
					__frame_main.__thread_actor.start();
					// Bonepeople.labelbutton_click_download(__temp_label.__account_name,__frame_main,__tasks,__frame_main.__thread_actor);
					
					break;
				case "同步":
					// Bonepeople.labelbutton_click_sync(__temp_label.__account_name,__frame_main,__tasks,__frame_main.__thread_actor);
					__frame_main.__panel_accounts.get(__temp_label.__account_name).set_status("此功能暂时不开启");
					__frame_main.__thread_actor = null;
					
					break;
				case "刷新":
					__frame_main.__thread_actor.start();
				}
			}
			else
				__frame_main.__panel_accounts.get(__temp_label.__account_name).set_status("目前正在有其他任务正在执行，请稍后再试。");
		}
		else
		{
			//nothing
		}
	}
	public void mouseEntered(MouseEvent __e)
	{
		// System.out.println("进入：x=" + __e.getX() + "，y=" + __e.getY());
	}
	public void mouseExited(MouseEvent __e)
	{
		// System.out.println("离开：x=" + __e.getX() + "，y=" + __e.getY());
	}
	public void mousePressed(MouseEvent __e)
	{
		// System.out.println("按下：x=" + __e.getX() + "，y=" + __e.getY());
	}
	public void mouseReleased(MouseEvent __e)
	{
		// System.out.println("释放：x=" + __e.getX() + "，y=" + __e.getY());
		//mouseClicked(__e);
	}
	public void mouseMoved(MouseEvent __e)
	{
		// System.out.println("移动：x=" + __e.getX() + "，y=" + __e.getY());
	}
	public void mouseDragged(MouseEvent __e)
	{
		// System.out.println("拖拽：x=" + __e.getX() + "，y=" + __e.getY());
	}
}