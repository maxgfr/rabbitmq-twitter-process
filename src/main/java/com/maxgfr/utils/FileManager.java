package com.maxgfr.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import java.util.ArrayList;


public class FileManager {

	private static FileManager single_instance = null;

	private FileManager() {
	}

	public static FileManager getInstance() {
		  if (single_instance == null)
				  single_instance = new FileManager();
		  return single_instance;
	}

	public ArrayList<String> giveFiles (String path) {
    	File folder = new File(path);
      File[] all_files = folder.listFiles();
      ArrayList<String> files = new ArrayList<String>();

      for (int i = 0; i < all_files.length; i++) {
    	  if (all_files[i].isFile()) {
    		    files.add(path+"/"+all_files[i].getName());
    	  }
	    }

      return files;
	}

	public JSONObject toJson (String path) {

		File file = new File(path);
		JSONObject json = new JSONObject();

		try {
		    Scanner scanner = new Scanner(file);
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        line = line.replaceAll("[\"'\u2018\u2019\u201c\u201d]", "");
		        if(line.substring(0, 10).contains("title")) {
		            json.put("title", line.substring(7));
		        }
		        if(line.substring(0, 10).contains("author")) {
		            json.put("author", line.substring(8));
		        }
		        if(line.substring(0, 10).contains("date")) {
		            json.put("date", line.substring(6));
		        }
		        if(line.substring(0, 10).contains("content")) {
		            json.put("content", line.substring(9));
		        }
		    }
		} catch(Exception e) {
		     e.printStackTrace();
		}

		return json;

	}

	public void writeJson (String message, String reviewer_name) {
		try {

			JsonObject obj = new JsonParser().parse(message).getAsJsonObject();
			Date date = new Date();
			String ts = Long.toString(date.getTime());

			obj.addProperty("accepted_date", ts);
			obj.addProperty("reviewer_name", reviewer_name);

			String original_title = obj.get("title").toString().replace("\"","");
			original_title = original_title.replace(" ", "_");

			FileWriter file = new FileWriter("src/main/ressources/accepted/" + original_title + ".json");
			
			file.write(obj.toString());
			file.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}



	}

}
