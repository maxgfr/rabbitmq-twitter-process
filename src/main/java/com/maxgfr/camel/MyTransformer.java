package com.maxgfr.camel;

public class MyTransformer {

  public String transformContent(String body) {
    return body.toUpperCase();
  }

}
