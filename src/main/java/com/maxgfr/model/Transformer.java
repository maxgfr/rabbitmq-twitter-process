package com.maxgfr.model;

public class Transformer {

    public Transformer() {

    }

    public String transformContent(String body) {
      System.out.println(body);
      return body.toUpperCase();
    }


}
