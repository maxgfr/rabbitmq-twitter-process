package com.maxgfr.model;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Builder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:/tmp/input?move=./done")
      .process(new LogProcessor())
      .bean(new Transformer(), "transformContent")
      .to("file:/tmp/output");
  }

}
