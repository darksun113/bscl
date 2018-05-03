package vin.kell;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FindRelevantMeetup {
	
	public boolean isInterestedIn(String link, String[] keywords) {
		try {
			Thread.sleep(1000);
			Document doc = Jsoup.connect(link).get();
			Elements meetups = doc.select("div[class=D_name bold]");
			for (Element element : meetups) {
				for (String keyword : keywords) {
					if(element.text().toLowerCase().contains(keyword))
						return true;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		FindRelevantMeetup finder = new FindRelevantMeetup();
		String[] keywords = {"blockchain","smart contracts","smart contract","ethereum","bitcoin"};
		try {
			//parameterized later
			for (int i = 0; i < 980; i+=20) {
				Document doc = Jsoup.connect("https://www.meetup.com/The-NewYork-SupplyChain-Meetup/members/?offset="+i).get();
				Elements eleMembers = doc.select("a[class=memName]");
				for (Element element : eleMembers) {
					User u = new User();
					u.setName(element.text());
					String link = element.attr("href");
					u.setLink(link);
					String[] tokens = link.split("/");
					u.setId(tokens[tokens.length-1]);
					if(finder.isInterestedIn(u.getLink(), keywords)) 
						System.out.println(u);
				}
				Thread.sleep(1000, 0);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
