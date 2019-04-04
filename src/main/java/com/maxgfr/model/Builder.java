package com.maxgfr.model;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Builder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("assets/unclassified/input")
      .process(new LogProcessor())
      .bean(new Transformer(), "transformContent")
      .to("assets/classified");
  }

}
