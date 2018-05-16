package vin.kell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Angel {
	
	public ArrayList<Founder> getFounders(Company c) {
		ArrayList<Founder> founders = new ArrayList<>();
		try {
			int random = new Random().nextInt(15)+30;
			Thread.sleep(random*1000);
			System.out.println("wait for " + random + " seconds.");
			Document document = Jsoup.connect(c.link).get();
			Element domain_ele = document.selectFirst("a[class=u-uncoloredLink company_url]");
			if(domain_ele!=null)
				c.domain = domain_ele.text();
			Elements founder_ele = document.select("a[class=profile-link]");
			for (Element element : founder_ele) {
				Founder founder = new Founder();
				founder.name = element.text();
				founder.company = c;
				System.out.println(founder.name);
				founders.add(founder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return founders;
	}
	/*
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.parse(new File("/Users/kelvin/Desktop/angel/angel.html"), "utf8");
			Elements list = doc.select("a[data-type=Startup]");
			for (Element element : list) {
				//System.out.println("Processing " + (i++) + " out of " + list.size());
				Company c = new Company();
				c.name = element.text();
				if(!c.name.isEmpty()) {
					System.out.print(c.name);
					System.out.print("|||");
					c.link = element.attr("href");
					System.out.println(c.link);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
	
	public static void main(String[] args) {
		try {
			Angel angel = new Angel();
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			PrintWriter writer = new PrintWriter("result.csv");
			writer.println("name,company,domain");
			String line = null;
			int i = 1;
			while((line=reader.readLine())!=null) {
				System.out.println("Processing " + (i++) + " out of 200.");
				String[] tokens = line.split("\\|\\|\\|");
				Company c = new Company();
				c.name=tokens[0];
				c.link=tokens[1];
				ArrayList<Founder> temp = angel.getFounders(c);
				if(temp.isEmpty())
					continue;
				for (Founder founder : temp) {
					writer.print(founder.name);
					writer.print(',');
					writer.print(founder.company.name);
					writer.print(',');
					writer.println(founder.company.domain);
				}
				if(i%50==0)
					writer.flush();
			}
			writer.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*	public static void main(String[] args) {
		try {
			Angel angel = new Angel();
			PrintWriter writer = new PrintWriter("result.csv");
			writer.println("name,company,domain");
			//ArrayList<Founder> found_founders = new ArrayList<>();
			Document doc = Jsoup.parse(new File("/Users/kelvin/Desktop/angel/angel.html"), "utf8");
			Elements list = doc.select("a[data-type=Startup]");
			//System.out.println("We have " + list.size() + " companies.");
			int i=1;
			for (Element element : list) {
				//System.out.println("Processing " + (i++) + " out of " + list.size());
				Company c = new Company();
				c.name = element.text();
				if(!c.name.isEmpty()) {
					System.out.println("----------"+c.name+"----------");
					c.link = element.attr("href");
					System.out.println(c.link);
					ArrayList<Founder> temp = new ArrayList<>(); //= angel.getFounders(c);
					if(temp.isEmpty())
						continue;
					for (Founder founder : temp) {
						writer.print(founder.name);
						writer.print(',');
						writer.print(founder.company.name);
						writer.print(',');
						writer.println(founder.company.domain);
					}
				}
				if(i%50==0)
					writer.flush();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
*/
}
