
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.stream.FileImageInputStream;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Hashtable;
import java.net.URLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class test extends JFrame implements ActionListener
{
	private JButton __button1 = new JButton("get_upload_locate");
	private JButton __button2 = new JButton("shangchuan");
	private JButton __button3 = new JButton("刷新");
	private JLabel __label1 = new JLabel(new ImageIcon("img\\wait1.gif"));
	private JTextField __text1 = new JTextField("");
	private JTextField __text2 = new JTextField("");
	private String __img_tongbu = "";
	ArrayList<String> __account_saved = new ArrayList<String>();

	test()
	{
		this.setBounds(700, 100, 500, 300);
		this.setLayout(null);

		this.add(__button1);
		this.add(__button2);
		this.add(__button3);
		this.add(__text1);
		this.add(__text2);
		this.add(__label1);

		__button1.setBounds(10, 10, 350, 20);
		__button2.setBounds(10, 40, 350, 20);
		__button3.setBounds(10, 70, 350, 20);
		__text1.setBounds(10, 100, 350, 20);
		__text2.setBounds(10, 130, 350, 20);
		__label1.setBounds(370, 100, 180, 180);

		__button1.addActionListener(this);
		__button2.addActionListener(this);
		__button3.addActionListener(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void do_test()
	{
		System.out.println("test begin");

		System.out.println("test over");
	}

	public void actionPerformed(ActionEvent __event)
	{

		do
		{
			if (__event.getActionCommand().equalsIgnoreCase(__button1.getActionCommand()))// 如果按钮名称相同会出现调用错误
			{
				System.out.println(__button1.getActionCommand());
				Bonepeople.test();

				break;
			}

			if (__event.getActionCommand().equalsIgnoreCase(__button2.getActionCommand()))
			{
				System.out.println(__button2.getActionCommand());
				// System.out.println(Data_url.get_account_info());
				String __temp_str = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAUADIDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9DP8Agmb/AMEzf2bvH3/BOH9nzXdd/Z8+CGta3rXw28O3+oahf+BdLubu/uJdLtnlmlleAvJI7szM7EliSSSTXuB/4JPfssHk/s0/s/8A/hvdI/8Akel/4JP/APKLP9mn/slXhf8A9NFrXrfxO+JelfCHwhJrutzG30uG7tLWedmREt/tFzFbrK7OyqsaNKrOxPCKx5IwQUdjyIf8Enf2WSzA/s0/AHH/AGT3SP8A4xXA/AD9hP8AZa+OP/CbY/ZW+AOljwd4rvvDH/Ij6RP9s+zeX+//AOPVdm7f9z5sY+8c17vq37Uvww1HSrq3h+K3gfT5riJ40uoPEFg0tsxUgSIJGZCynkb1ZcjkEZFeG/sZavoX7PureNbnxZ+0D8OPEUPiXW7nVIrWz1XT4IpppmQyXkx4dJnCKPJRjFGA2C5YFAZ3Lf8ABJz9lknI/Zq+AIx6fD7SR/7b07/h07+yx/0bT+z/AP8AhvNI/wDkeu70z9qXwB4g8X6DoWieK9A8R6p4gu5LWGDSdTtrx4NltPcNLIqSFlj2wMu4A/O6Docj0SgD+Hf/AIKZeENL8B/8FIP2gtC0LS9L0XRNF+JXiOx0/T7GJLW1sbeLVLlIoYokwscaIqqqKAFAAAAFFW/+CsX/AClN/aW/7Kr4o/8ATvdUUAfaPwA/4O4/2kP2afgR4J+G+g+Cfgjd6H8P/D9h4c06e/0jVJLua3s7aO3ieVk1BEaQpGpYqigknCgcDrv+I1b9qf8A6EH9n/8A8Eer/wDyzoooAP8AiNW/an/6EH9n/wD8Eer/APyzo/4jVv2p/wDoQf2f/wDwR6v/APLOiigA/wCI1b9qf/oQf2f/APwR6v8A/LOj/iNW/an/AOhB/Z//APBHq/8A8s6KKAPyy/aE+N2q/tJ/Hzxx8RtdttNtNb8f+IL/AMR6hBYRvHaw3F5cyXEqRK7O6xh5GChnYgAZYnklFFAH/9k=";
				byte[] __temp_byte;
				__temp_byte = Base64.getDecoder().decode(__temp_str);
				// this.__label1.setIcon(new ImageIcon(__temp_byte));
				this.__label1.setIcon(Data_image.get_picture("download_disable"));
				System.out.println(__temp_str);

				break;
			}

			if (__event.getActionCommand().equalsIgnoreCase(__button3.getActionCommand()))
			{
				System.out.println(__button3.getActionCommand());

				System.out.println(this.__account_saved);

				// System.out.println(Bonepeople.test());

			}
		} while (false);

	}

	public static void main(String args[])
	{
		test demo = new test();

		demo.do_test();
	}
}
