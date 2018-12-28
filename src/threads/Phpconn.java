package threads;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Phpconn {
	static String[] nickname= {"굽네스","나인골랑","우나이","종발","알바르","로맨틱성공적","라카도랑","리스리스","21base","klei2"};
	static String[] member= {"264","265","266","318","319","321"};
	private Phpconn() {}

	public static Phpconn getInstance() {
		return LazyHolder.INSTANCE;
	}

	private static class LazyHolder {
		private static final Phpconn INSTANCE = new Phpconn();
	}

	public synchronized static void trans(ArrayList<String> list) throws IOException {
		long seed=System.currentTimeMillis( ); //랜덤변수를 위해 시드값을 현재시간의 천분의 1초값으로 줌
		Random random=new Random(seed); //시드를 기반으로 랜덤객체를 만듬
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss"); //글작성시간 데이터포맷 객체 sql문 형식
		Date date=new Date(); //시간을 가져올 객체
		String regdate=dateFormat.format(date); //현재시간을 sql문 형식으로 변경해서 저장
		int i=random.nextInt(10);
		int j=random.nextInt(6);
		
		
	
		Document doc = Jsoup.connect("http://issuemoa.kr/scraping.addon.php").data("id", "admin")
				.data("password", "admin").data("document_srl", list.get(2)).data("module_srl", list.get(3))
				.data("readed_count", Integer.toString(random.nextInt(30))).data("title", list.get(0)).data("content", list.get(1))
				.data("user_id", "'"+nickname[i]+"'").data("user_name", "'"+member[j]+"'").data("nick_name", "'"+nickname[i]+"'")
				.data("regdate", "'"+regdate+"'").data("member_srl", member[j]).userAgent("Mozilla").post();
		System.out.println(doc);
		
	}
}
