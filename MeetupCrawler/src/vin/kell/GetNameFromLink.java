package vin.kell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetNameFromLink {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/kelvin/Dropbox/crypto2018uh/The-NewYork-SupplyChain-Meetup-members.csv")));
			String line = null;
			while((line=reader.readLine())!=null) {
				String[] tokens = line.split(",");
				if(tokens.length<3)
					break;
				//System.out.println(tokens[2]);
				Document document = Jsoup.connect(tokens[2])
						.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
						.get();
				Element nameEle = document.selectFirst("span[itemprop=name]");
				if(nameEle!=null)
					System.out.println(nameEle.text());
				Thread.sleep(1000);
				
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
