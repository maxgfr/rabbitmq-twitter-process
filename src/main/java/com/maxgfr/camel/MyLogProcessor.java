package com.maxgfr.camel;

import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class MyLogProcessor implements Processor {

  public void process (Exchange exchange) throws Exception {
    System.out.println("ça marche");
    return;
  }

}
