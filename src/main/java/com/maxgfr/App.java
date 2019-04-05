package com.maxgfr;

import com.maxgfr.model.Queue;
import com.maxgfr.utils.FileManager;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import com.rabbitmq.client.Channel;

import com.maxgfr.model.Builder;
import org.json.simple.JSONObject;


import java.util.ArrayList;


public class App
{
    public static void main( String[] args )
    {
        FileManager manager = FileManager.getInstance();
        ArrayList<String> list_files = manager.giveFiles("src/main/ressources/submitted");
        Queue queue = Queue.getInstance("mqpending", "exchange");
        queue.basicPublish(list_files);
        DeliverCallback deliverCallback = (receive, msg) -> { //Recevied the msg
            String message = new String(msg.getBody(), "UTF-8");
            manager.writeJson(message, "Maxime");
            System.out.println(message);
        };
        queue.setDeliverCallback(deliverCallback);
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
