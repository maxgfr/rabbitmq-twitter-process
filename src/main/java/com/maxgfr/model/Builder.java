package com.maxgfr.model;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Builder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("rabbitmq:localhost:5672/tasks?username=guest&password=guest&autoDelete=false&routingKey=routing_key&queue=mqpending\n")
            .to("mock:result");
  }

}
