package vin.kell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmailMain {

	public static void main(String[] args) {
		try {
			Document doc = Jsoup.parse(new File("/Users/kelvin/Desktop/index.html"), "utf8");
			//System.out.println(doc.title());
			Elements dls = doc.select("dl");
			int num = 0;
			Element dl = dls.get(0);
			Elements dts = dl.select("dt");
			for (Element element : dts) {
				Element a = element.selectFirst("a");
				String link = a.attr("href");
				String[] tokens = link.split("=");
				String id = tokens[1];
				if(tokens[1].contains("loc"))
					id = tokens[1].split("&")[0];
				//System.out.println(id);
				ReadableByteChannel in=Channels.newChannel(
					    new URL("http://www.uh.edu/search/directory/api/vcard/?id="+id+"&type=person")
					    .openStream());
				FileChannel out = new FileOutputStream("/Users/kelvin/Desktop/abc/"+id+".vcf").getChannel();
				out.transferFrom(in, 0, Long.MAX_VALUE);
				BufferedReader reader = new BufferedReader(
						new FileReader("/Users/kelvin/Desktop/abc/"+id+".vcf"));
				String line = null;
				while((line=reader.readLine())!=null) {
					if(line.contains("email")) {
						System.out.println(line.split(":")[1]);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
