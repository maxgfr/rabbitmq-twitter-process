package com.maxgfr.model;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Builder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:JavaDSLRouteStart").
      // To route message on the IDE console
              to("stream:out");
  }

}
