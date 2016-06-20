package com.demo.music.bootstrap;

public interface Configuration {
	static final String DASH="\n-----------------------------------------------------------------------------------\n";
	public static final String LIKE_BOX_URL="http://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fmediadownloader&show_faces=true&colorscheme=light&stream=true&border_color&header=true";
	public static final String LOGIN_URL="https://www.facebook.com/login.php?api_key=457114007670546&skip_api_login=1&display=page&cancel_url=https%3A%2F%2Fwww.facebook.com%2Fconnect%2Flogin_success.html%3Ferror_reason%3Duser_denied%26error%3Daccess_denied%26error_description%3DThe%2Buser%2Bdenied%2Byour%2Brequest.&fbconnect=1&next=https%3A%2F%2Fwww.facebook.com%2Fdialog%2Fpermissions.request%3F_path%3Dpermissions.request%26app_id%3D457114007670546%26redirect_uri%3Dhttps%253A%252F%252Fwww.facebook.com%252Fconnect%252Flogin_success.html%26display%3Dpage%26response_type%3Dtoken%26fbconnect%3D1%26from_login%3D1%26client_id%3D457114007670546&rcount=1";

	public static final String LIKED_STRING="connect_confirmation_cell connect_confirmation_cell_like";
	public static final String LOGIN_STRING="Success";
	
	public static final String NOT_LIKE_MESSAGE=DASH+"Bấm like để cỗ vũ nhà phát triển :D\n"+DASH;
	public static final String LIKE_MESSAGE=DASH+"Bạn đã like, tất cả chức năng của ứng dụng sẽ được kích hoạt\n"+DASH;
	
	public static final String URL_DEST_REQUIRED_TITLE="Nhập thông tin";
	public static final String URL_DEST_REQUIRED_CONTENT="Vui lòng nhập 'Link web' và 'Directory' !";
	
	
	public static final String WRONG_SITE_TITLE="Lỗi";
	public static final String WRONG_SITE_CONTENT="Chương trình chỉ hỗ trợ download nhạc từ 2 trang: \n-Nhaccuatui.com \n-Mp3.zing.vn";
	
	
}
