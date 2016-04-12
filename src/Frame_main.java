
import java.awt.*;
// import java.awt.Dimension;
import javax.swing.*;
// import javax.swing.JOptionPane;
// import javax.swing.JFileChooser;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Enumeration;


public class Frame_main extends JFrame implements Client_ui
{
	JButton __button_path = new JButton("安装目录");
	JButton __button_fresh = new JButton("刷新");
	JButton __button_quit = new JButton("退出");
	JTextArea __lable_note = new JTextArea("");
	JPanel __panel_component_account = new JPanel();
	JScrollPane __scrollpane_account = new JScrollPane(__panel_component_account);
	// JTextArea __text_receive = new JTextArea();
	// JScrollPane __scrollpane_account = new JScrollPane(__text_receive);
	JTextField __text_path = new JTextField("");
	Hashtable<String, Panel_account> __panel_accounts = new Hashtable<String, Panel_account>();
	Hashtable<String, Data_account> __data_account_online = new Hashtable<String, Data_account>();
	ArrayList<String> __account_saved = new ArrayList<String>();
	public static Thread_actor __thread_actor = null;
	public Properties __pp_config = new Properties();
	
	
	Frame_main()
	{
		//设置窗体
		
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception __e){}
		
		int __temp_w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int __temp_h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(760,470);
		this.setLocation((__temp_w - this.getWidth()) / 2,(__temp_h - this.getHeight()) / 2);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("WoW Save");
		
		//添加组件
		this.add(__button_path);
		this.add(__button_fresh);
		this.add(__button_quit);
		this.add(__scrollpane_account);
		this.add(__text_path);
		this.add(__lable_note);
		
		//设置按钮(x,y,w,h)
		__button_path.setBounds(10,10,100,20);
		__button_path.addActionListener(__buttonaction_main);
		__button_fresh.setBounds(430,10,60,20);
		__button_fresh.addActionListener(__buttonaction_main);
		__button_fresh.setVisible(false);
		__button_quit.setBounds(680,390,60,20);
		__button_quit.addActionListener(__buttonaction_main);
		
		//设置文本框
		__text_path.setEditable(false);
		__text_path.enableInputMethods(false);
		__text_path.setBounds(120,10,300,20);
		
		//设置文本标签	需要初始化宽度和高度，否则将无显示
		__lable_note.setBounds(10,370,650,60);
		// __lable_note.setBackground(Color.RED);
		__lable_note.setText(" 作者：bonepeople\n版本 v1.0.2\n发布时间：2015-10-17");
		__lable_note.setEditable(false);
		__lable_note.enableInputMethods(false);
		
		//设置面板
		__panel_component_account.setLayout(null);
		// __panel_component_account.addMouseListener(__mouseaction);
		__scrollpane_account.setBounds(10,40,730,320);
		__scrollpane_account.getHorizontalScrollBar().setUnitIncrement(60);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show_frame()
	{
		Bonepeople.frame_init(this);
	}
	
	
	public void clear()
	{
		//更新目录后需要将之前已经创建的JPanel清除
		if(this.__panel_accounts.size() != 0)
		{
			for(Enumeration<Panel_account> __temp_enum = this.__panel_accounts.elements();__temp_enum.hasMoreElements();)
			{
				this.__panel_component_account.remove(__temp_enum.nextElement());
			}
			this.__panel_accounts.clear();
			this.__panel_component_account.setPreferredSize(new Dimension(180 * __panel_accounts.size(),300));
			this.__scrollpane_account.revalidate();
			this.repaint();
		}
	}
	
	
	public void add_account(String __name,String __path)
	{
		Panel_account __temp_panel = new Panel_account(__name,__path);
		this.__panel_component_account.add(__temp_panel);
		__temp_panel.setBounds(180 * __panel_accounts.size(),0,170,300);
		this.__panel_accounts.put(__temp_panel.__name,__temp_panel);
		this.__panel_component_account.setPreferredSize(new Dimension(180 * __panel_accounts.size(),300));
		this.__scrollpane_account.revalidate();
	}
	
	
	public boolean change_path()
	{
		JFileChooser __parseDir = new JFileChooser();
		__parseDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		__parseDir.showOpenDialog(this);
		if(__parseDir.getSelectedFile() != null)//用户直接点击取消会返回一个空值，直接设置会报空指针异常。
		{
			System.out.println(__parseDir.getSelectedFile().getAbsolutePath());//得到的是绝对路径
			this.__text_path.setText(__parseDir.getSelectedFile().getAbsolutePath());
			this.__pp_config.setProperty("wow_path",__parseDir.getSelectedFile().getAbsolutePath());
			return true;
		}
		return false;
	}
	
	
	public void alert(String __text)
	{
		//JOptionPane.showMessageDialog(frame_main, "窗口已经初始化完毕", "错误", JOptionPane.ERROR_MESSAGE);
		//JOptionPane.showMessageDialog(frame_main, "窗口已经初始化完毕", "信息", JOptionPane.INFORMATION_MESSAGE);
		//JOptionPane.showMessageDialog(frame_main, "窗口已经初始化完毕", "警告", JOptionPane.WARNING_MESSAGE);
		//JOptionPane.showMessageDialog(frame_main, "窗口已经初始化完毕", "问题", JOptionPane.QUESTION_MESSAGE);
		//JOptionPane.showMessageDialog(frame_main, "窗口已经初始化完毕", "计划", JOptionPane.PLAIN_MESSAGE);
		
		JOptionPane.showMessageDialog(this, __text, "警告", JOptionPane.WARNING_MESSAGE);
	}
}













