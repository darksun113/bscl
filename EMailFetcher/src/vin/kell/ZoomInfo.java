package vin.kell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZoomInfo {
	public static final String BaseUrl = "https://www.zoominfo.com/people-search/";

	public String getDomain(Company c) throws InterruptedException, IOException {
		Thread.sleep(5000);
		Document document = Jsoup.connect(c.link).get();
		Element element = document.selectFirst("div[class=content-box_row website]");
		if (element == null)
			return null;
		Element a = element.selectFirst("a");
		if (a == null)
			return null;
		else
			return a.attr("href");
	}

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length < 2) {
			System.err.println("Usage: locaion page_number keywords");
			System.exit(-1);
		}
		PrintWriter writer = new PrintWriter("/Users/kelvin/Desktop/out.csv");
		writer.println("name,position,company,domain");
		writer.flush();
		ZoomInfo zi = new ZoomInfo();
		int page = Integer.parseInt(args[1]);
		StringBuilder builder = new StringBuilder(BaseUrl);
		builder.append(args[0]);
		builder.append("--");
		for (int i = 2; i < args.length; i++) {
			builder.append('+');
			builder.append(args[i]);
		}

		String queryUrl = builder.toString();
		for (int i = 1; i <= page; i++) {
			String query_w_page = queryUrl + "?pageNum=" + i;
			System.out.println(query_w_page);

			try {
				Document document = Jsoup.connect(query_w_page).get();
				Elements elements = document.select("div[class=people_search-table-content]");
				if(elements==null)
					System.exit(-1);
				for (Element element : elements) {
					Founder founder = new Founder();
					founder.name = element.selectFirst("div[class=name]").text();
					founder.company = new Company();
					Element titleElement = element.selectFirst("div[class=title]");
					if (titleElement == null)
						continue;
					Element positionEle = titleElement.selectFirst("p");
					if (positionEle == null)
						continue;				
					Element companyEle = element.selectFirst("div[class=title]").selectFirst("a");
					if (companyEle == null)
						continue;
					founder.company.name = companyEle.text();
					founder.position = positionEle.text().replace(" "+founder.company.name, "");
					founder.company.link = "https://www.zoominfo.com" + companyEle.attr("href");
					String domain = zi.getDomain(founder.company);
					founder.company.domain = domain.substring(2, domain.length()).replaceAll(".*\\.(?=.*\\.)", "");
					;
					String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\"", founder.name, founder.position,
							founder.company.name, founder.company.domain);
					writer.println(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			writer.flush();
		}
		writer.close();
	}

}
