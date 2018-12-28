package handler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import threads.SportThread;

public class Handler {

	
	
	public static void main(String args[]) throws IOException {
		SportThread r1=new SportThread();
		Thread t1 = new Thread(r1);
		t1.start();
	
	}
}
