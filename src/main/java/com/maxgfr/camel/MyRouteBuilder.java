package com.maxgfr.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class MyRouteBuilder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:/tmp/input?move=./done")
      .process(new MyLogProcessor())
      .bean(new MyTransformer(), "transformContent")
      .to("file:/tmp/output");
  }

}
