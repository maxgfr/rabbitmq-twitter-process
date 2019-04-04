package com.maxgfr;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import com.maxgfr.model.Builder;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
      Builder routeBuilder = new Builder();
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
