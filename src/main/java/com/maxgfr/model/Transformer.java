package com.maxgfr.model;

public class Transformer {

    public Transformer(String text) {
      System.out.println(text);

    }

    public String transformContent(String body) {
      System.out.println(body);
      return body.toUpperCase();
    }


}
