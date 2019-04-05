package com.maxgfr.model;

import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class LogProcessor implements Processor {

  public void process (Exchange exchange) throws Exception {
    System.out.println("it process...");
    return;
  }

}
