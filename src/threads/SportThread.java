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
import org.jsoup.select.Elements;

public class SportThread implements Runnable {
	Document doc; //문서가 임시 저장될 공간
	ArrayList<String> list=new ArrayList(); //크롤링 문서를 저장할 리스트
	StringBuffer htmlBuffer=new StringBuffer(); // 원하는 부분을 추출해서 저장할 버퍼
	StringBuffer pageNumBuffer=new StringBuffer(); // 주소의 페이지넘버를 저장할 버퍼
	int newDocNum=100003; //본인 웹사이트에 저장할 글 번호
	String modulNum="186"; //게시판의 모듈번호
	String overlap; //파싱한 글의 번호를 저장해놓고 비교할 변수
	
	public void parse(String overlap)  {

		pageNumBuffer.append("https://www.fmkorea.com/football_news");
		try {
			String pageNum;
			doc=Jsoup.connect(pageNumBuffer.toString()).get();
			htmlBuffer.append(doc.select("td.hotdeal_var8 a").toString());
			pageNum=htmlBuffer.substring(10,20);
			System.out.println(pageNum+"=="+overlap);
			if(!pageNum.equals(overlap))
			{
				/*글번호 주소 조합 및 연결*/
				htmlBuffer.delete(0,htmlBuffer.length());
				pageNumBuffer.delete(24,pageNumBuffer.length());
				pageNumBuffer.append(pageNum);
				doc=Jsoup.connect(pageNumBuffer.toString()).get();
				
				/*글 제목 파싱 index0*/
				list.add(doc.select("span.np_18px_span").html());
				
				/*글 내용 파싱 index1*/
				htmlBuffer.append(doc.select("div.rd_body").html());
				htmlBuffer.append("</br>출처:에펨코리아 </br> <a href=\"http://issuemoa.kr/sport\"> 이슈모아</a>");
				list.add(htmlBuffer.substring(245, htmlBuffer.length()));
				
				
				/*작성글 번호 저장 index2*/
				pageNumBuffer.delete(0,pageNumBuffer.length());
				pageNumBuffer.append(newDocNum);
				list.add(pageNumBuffer.toString());
				/*모듈번호 저장 index3*/
				list.add(modulNum);
				
				Phpconn.getInstance();
				Phpconn.trans(list);
				
				/*후처리*/
				newDocNum++;
				this.overlap=pageNum;
				
				
			}else
			{
				System.out.println("이미 파싱한 글 입니다.");
			}
		}catch(Exception e){
			
		}
		
		
		
		
		
	}

	public void clean() {
		pageNumBuffer.delete(0, pageNumBuffer.length());
		htmlBuffer.delete(0, htmlBuffer.length());
		list.clear();
	}

	@Override
	public void run() {
		while(true)
		{
			parse(overlap);
			clean();
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
	}

}
