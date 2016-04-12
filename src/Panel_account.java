
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
// import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.io.File;
//import javax.swing.text.AbstractDocument.BranchElement;

public class Panel_account extends JPanel implements Client_ui
{
	public String __name;//189519602#1
	public String __path;//D:\柔斌\WTF\Account\189519602#1
	private JLabel __label_state = new JLabel("");
	private JLabel __label_wait = new JLabel(Data_image.get_picture("等待"));
	private Label_button __label_fresh;
	private Label_button __label_upload;
	private Label_button __label_download;
	private Label_button __label_sync;
	private JTree __tree;
	
	// private JButton __button_upload = new JButton("上传");
	// private JButton __button_download = new JButton("下载");
	// private JButton __button_sync = new JButton("同步");
	
	
	Panel_account(String __name, String __path)
	{
		this.__name = __name;
		this.__path = __path;
		__label_fresh = new Label_button(__name,"刷新");
		__label_upload = new Label_button(__name,"上传");
		__label_download = new Label_button(__name,"下载");
		__label_sync = new Label_button(__name,"同步");
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.init();
	}
	
	
	private void init()
	{
		
		File __file_root = new File(__path);
		DefaultMutableTreeNode __node_toot = new DefaultMutableTreeNode (__file_root.getName());
		File[] __files_server = __file_root.listFiles();
		for(int __temp_j = 0;__files_server != null && __temp_j < __files_server.length;__temp_j++)
		{
			if (__files_server[__temp_j].isDirectory() && !__files_server[__temp_j].getName().equalsIgnoreCase("SavedVariables"))
			{
				//System.out.println("\t" + __files_server[__temp_j].getName());//服务器遍历
				DefaultMutableTreeNode __temp_node_server = new DefaultMutableTreeNode (__files_server[__temp_j].getName());
				__node_toot.add(__temp_node_server);
				File[] __files_role = __files_server[__temp_j].listFiles();
				for(int __temp_k = 0;__files_role != null && __temp_k < __files_role.length;__temp_k++)
				{
					if (__files_role[__temp_k].isDirectory())
					{
						//System.out.println("\t\t" + __files_role[__temp_k].getName());//角色遍历
						DefaultMutableTreeNode __temp_node_role = new DefaultMutableTreeNode (__files_role[__temp_k].getName());
						__temp_node_server.add(__temp_node_role);
					}
				}
			}
		}
		__tree = new JTree(__node_toot);
		__tree.setBounds(0,0,150,240);
		//__tree.setBackground(Color.CYAN);难看
		__label_state.setText("请刷新...");
		__label_state.setBounds(0,250,150,20);
		
		__label_wait.setBounds(0,60,180,180);
		__label_wait.setVisible(false);
		
		__label_fresh.setBounds(150,0,20,20);
		__label_fresh.addMouseListener(__mouseaction);
		__label_upload.setBounds(20,280,50,20);
		__label_upload.addMouseListener(__mouseaction);
		__label_upload.setEnabled(false);
		__label_download.setBounds(100,280,50,20);
		__label_download.addMouseListener(__mouseaction);
		__label_download.setEnabled(false);
		__label_sync.setBounds(100,280,50,20);
		__label_sync.addMouseListener(__mouseaction);
		
		
		this.add(__label_wait);
		this.add(__tree);
		this.add(__label_state);
		this.add(__label_fresh);
		this.add(__label_upload);
		this.add(__label_download);
		// this.add(__label_sync);
		
		//检查帐号状态
		// String __temp_str = Bonepeople.get_account_status(__name,__path,__frame_main);
		// this.__label_state.setText(__temp_str);
	}
	
	
	public void set_status(String __status)
	{
		this.__label_state.setText(__status);
		//upload
		switch(__status)
		{
		case"请刷新...":
		case"云端和本地文件一致":
		case"帐号的配置信息不完整。":
		case"操作全部完成":
			if(__label_upload.__active)__label_upload.setEnabled(false);
			break;
		default:
			if(!__label_upload.__active)__label_upload.setEnabled(true);
		}
		//download
		switch(__status)
		{
		case"请刷新...":
		case"云端和本地文件一致":
		case"云端保存的配置不完整。":
		case"云端信息获取失败。":
		case"帐号在云端没有备份。":
		case"帐号的配置信息不完整。":
		case"出现一个严重错误，请重新上传。":
			if(__label_download.__active)__label_download.setEnabled(false);
			break;
		default:
			if(!__label_download.__active)__label_download.setEnabled(true);
		}
	}
	
	
	public String get_status()
	{
		return this.__label_state.getText();
	}
	
	
	public void set_wait(boolean __iswait)
	{
		this.__label_wait.setVisible(__iswait);
	}
}





















