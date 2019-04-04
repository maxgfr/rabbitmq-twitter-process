package com.maxgfr;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import com.rabbitmq.client.DeliverCallback;

import com.maxgfr.model.Builder;
import com.maxgfr.model.Receiver;
import com.maxgfr.model.Sender;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [x] Received '" + message + "'");
      };
      Sender sender = Sender.getInstance("mqpending");
      Receiver receiver = Receiver.getInstance("mqpending", deliverCallback);
      Builder routeBuilder = new Builder();
      CamelContext ctx = new DefaultCamelContext();
      System.out.println("cdddsqsqdsqdsqdsqddsqds");
      /*
      try {
        ctx.addRoutes(routeBuilder);
        ctx.start();
        Thread.sleep(5*60*1000);
        ctx.stop();
      } catch(Exception e) {
        e.printStackTrace();
      }
      */
    }
}
