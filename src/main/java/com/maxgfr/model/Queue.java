package com.maxgfr.model;

import com.maxgfr.utils.FileManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Queue {

  private static Queue single_instance = null;
  private String queue_name = null;
  private String exchange_name = null;
  private Channel channel = null;

  private Queue(String queue_name, String exchange_name) {
      try {
          this.exchange_name = exchange_name;
          this.queue_name = queue_name;
          ConnectionFactory factory = new ConnectionFactory();
          factory.setHost("127.0.0.1");
          Connection connection = factory.newConnection();
          Channel channel = connection.createChannel();
          channel.queueDelete(queue_name);
          channel.exchangeDeclare(exchange_name, "direct", true);
          channel.queueDeclare(queue_name, true, false, false, null);
          channel.queueBind(queue_name, exchange_name, "black");
          this.channel = channel;
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  public static Queue getInstance (String queue_name, String exchange_name) {
      if (single_instance == null)
          single_instance = new Queue(queue_name, exchange_name);
      return single_instance;
  }

  public void basicPublish (ArrayList<String> list_files) {
    FileManager converter = FileManager.getInstance();
    for(int i= 0; i<list_files.size(); i++) {
        try {
            JSONObject obj = converter.toJson(list_files.get(i));
            byte[] to_send = obj.toString().getBytes("UTF-8");
            System.out.println(this.channel);
            this.channel.basicPublish("", this.queue_name, null, to_send);
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
