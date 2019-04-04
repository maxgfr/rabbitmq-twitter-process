package com.maxgfr.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelOrchestrator {

  public static void launch() {

    MyRouteBuilder routeBuilder = new MyRouteBuilder();
    CamelContext ctx = new DefaultCamelContext();
    try {
      ctx.addRoutes(routeBuilder);
      ctx.start();
      Thread.sleep(5*60*1000);
      ctx.stop();
    } catch(Exception e) {
      e.printStackTrace();
    }

  }

}
