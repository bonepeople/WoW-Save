import javax.swing.JLabel;
// import javax.swing.ImageIcon;

public class Label_button extends JLabel
{
	public String __account_name;
	public String __type;
	public boolean __active;
	
	Label_button(String __name, String __type)
	{
		this.__account_name = __name;
		this.__type = __type;
		this.__active = true;
		this.setPicture();
	}
	
	
	public void setEnabled(boolean __enabled)
	{
		this.__active = __enabled;
		this.setPicture();
	}
	
	
	private void setPicture()
	{
		if(this.__active)
		{
			switch(this.__type)
			{
			case"ˢ��":
				this.setIcon(Data_image.get_picture("fresh_enable"));
				break;
				
			case"�ϴ�":
				this.setIcon(Data_image.get_picture("upload_enable"));
				break;
				
			case"����":
				this.setIcon(Data_image.get_picture("download_enable"));
				break;
				
			case"ͬ��":
				this.setIcon(Data_image.get_picture("ͬ��"));
				
			}
		}
		else
		{
			switch(this.__type)
			{
			case"ˢ��":
				this.setIcon(Data_image.get_picture("fresh_enable"));
				break;
				
			case"�ϴ�":
				this.setIcon(Data_image.get_picture("upload_disable"));
				break;
				
			case"����":
				this.setIcon(Data_image.get_picture("download_disable"));
				break;
				
			case"ͬ��":
				this.setIcon(Data_image.get_picture("ͬ��"));
				
			}
		}
	}
}