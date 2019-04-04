package com.maxgfr.utils;

import java.io.File;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class JsonConverter {

  private static JsonConverter single_instance = null;

  private JsonConverter() {
  }

  public static JsonConverter getInstance() {
          if (single_instance == null)
                  single_instance = new JsonConverter();
          return single_instance;
  }

	public JSONObject parse (String path) {

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
		     System.err.println(e);
		}

		return json;

	}

}
