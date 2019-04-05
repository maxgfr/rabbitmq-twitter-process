package com.maxgfr.model;

import com.maxgfr.utils.FileManager;
import com.rabbitmq.client.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Queue {

  private static Queue single_instance = null;
  private String queue_name = null;
  private String exchange_name = null;
  private Channel channel = null;
  private String routing_key = null;

  private Queue(String queue_name, String exchange_name, String routing_key) {
      try {
          this.exchange_name = exchange_name;
          this.queue_name = queue_name;
          this.routing_key = routing_key;
          ConnectionFactory factory = new ConnectionFactory();
          factory.setHost("127.0.0.1");
          Connection connection = factory.newConnection();
          Channel channel = connection.createChannel();
          channel.queueDelete(queue_name);
          channel.exchangeDeclare(exchange_name, "direct", true);
          channel.queueDeclare(queue_name, true, false, false, null);
          channel.queueBind(queue_name, exchange_name, routing_key);
          this.channel = channel;
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  public static Queue getInstance (String queue_name, String exchange_name, String routing_key) {
      if (single_instance == null)
          single_instance = new Queue(queue_name, exchange_name, routing_key);
      return single_instance;
  }

  public void basicPublish (ArrayList<String> list_files) {
    FileManager converter = FileManager.getInstance();
    for(int i= 0; i<list_files.size(); i++) {
        try {
            JSONObject obj = converter.toJson(list_files.get(i));
            byte[] to_send = obj.toString().getBytes("UTF-8");
            this.channel.basicPublish(this.exchange_name, this.routing_key, MessageProperties.PERSISTENT_TEXT_PLAIN, to_send);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  }

  public void setDeliverCallback (DeliverCallback deliver_callback) {
      try {
          this.channel.basicConsume(this.queue_name, true, deliver_callback, consumerTag -> { });
      } catch (Exception e) {
          e.printStackTrace();
      }

  }
  
}
