package vin.kell;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ZoomInfoGoogle {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try {
			JSONArray rows = (JSONArray) parser.parse(new FileReader("/Users/kelvin/Desktop/risk-manager.json"));
			for (Object object : rows) {
				JSONObject jObject = (JSONObject) object;
				//System.out.println(jObject.get("jobtitle"));
				Company c = new Company();
				c.name = (String)jObject.get("company");
				Founder founder = new Founder();
				founder.company = c;
				String link = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
