import java.util.ArrayList;

public interface Client_ui
{
	// Listener_button __buttonaction_login = new Listener_button("login");
	Listener_button __buttonaction_main = new Listener_button("main");
	Listener_mouse __mouseaction = new Listener_mouse();

	ArrayList<Data_task> __tasks = new ArrayList<Data_task>();

	// ��������ʹ���˼������ʵ����Ҫ�ڼ�����֮����г�ʼ��
	// Frame_login __frame_login = new Frame_login("��¼");
	Frame_main __frame_main = new Frame_main();

}
