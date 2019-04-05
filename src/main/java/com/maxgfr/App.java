package com.maxgfr;

import com.maxgfr.model.Queue;
import com.maxgfr.utils.FileManager;
import com.maxgfr.utils.TwitterManager;
import com.rabbitmq.client.DeliverCallback;


import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class App
{
    public static void main( String[] args )
    {
        FileManager manager = FileManager.getInstance();
        TwitterManager twitter_manager = TwitterManager.getInstance();
        ArrayList<String> list_files = manager.giveFiles("src/main/ressources/submitted");

        /* RABBIT MQ */
        Queue queue = Queue.getInstance("mqpending", "exchange", "routing_key");
        queue.basicPublish(list_files);
        DeliverCallback deliverCallback = (receive, msg) -> { //Recevied the msg
            String message = new String(msg.getBody(), "UTF-8");
            manager.writeJson(message, "Maxime");
            System.out.println(message);
        };
        queue.setDeliverCallback(deliverCallback);

        /* TWITTER*/
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
            try {
                twitter_manager.saveTrends("trends");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);

        /* APACHE CAMEL
        Builder routeBuilder = new Builder();
        CamelContext ctx = new DefaultCamelContext();
        try {
            ctx.addRoutes(routeBuilder);
            ctx.start();
            Thread.sleep(5*60*1000);
            ctx.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }
}
