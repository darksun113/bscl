package vin.kell;

import java.io.BufferedReader;
import java.io.FileReader;

public class RemoevInvalidLine {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/kelvin/Desktop/angel/res.txt"));
			String line =null;
			System.out.println("name,company,domain");
			while((line=reader.readLine())!=null) {
				if(line.startsWith(","))
					continue;
				if(line.startsWith("name,"))
					continue;
				System.out.println(line);
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
