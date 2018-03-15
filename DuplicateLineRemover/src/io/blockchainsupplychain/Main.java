package io.blockchainsupplychain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException{
		if(args.length<1) {
			System.err.println("Please specify a workign folder.");
			System.exit(-1);
		}

		File file = new File(args[0]);
		if(!file.exists()) {
			System.err.println("Working folder doesn't exit.");
			System.exit(-2);
		}
		
		File processed = new File(file, "processed");
		if(processed.exists()) {
			if(processed.isFile()) {
				System.err.println("Redunant name of \"processed\". Please remove any file name \"processed\" in the working folder.");
				System.exit(-3);
			}
		} else {
			processed.mkdir();
		}
		
		ArrayList<String> existing_lines = new ArrayList<>();
		
		File[] files = processed.listFiles();
		for (File f : files) {
			if(f.isDirectory())
				continue;
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line=reader.readLine())!=null)
				existing_lines.add(line);
			reader.close();
			isr.close();
			fis.close();
		}
		int duplicates = 0;
		int processed_files = 0;
		File[] pending_files = file.listFiles();
		for (File f : pending_files) {
			if(f.isDirectory())
				continue;
			File out = new File(processed, f.getName());
			PrintWriter writer = new PrintWriter(out);
			
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line=reader.readLine())!=null) {
				if(existing_lines.contains(line)) {
					System.out.println(String.format("%s was found in existing files. Skipped.", line));
					duplicates++;
					continue;
				}
				else {
					writer.println(line);
				}
			}
			
			writer.close();
			reader.close();
			isr.close();
			fis.close();
			f.delete();
			processed_files++;
		}
		
		System.out.println(String.format("%d files have been processed. %d duplicate lines have been removed.", processed_files, duplicates));
		
	}

}
