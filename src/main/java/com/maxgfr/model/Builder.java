package com.maxgfr.model;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Builder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("rabbitmq://localhost/A?routingKey=B")
      .to("rabbitmq://localhost/B");
  }

}
