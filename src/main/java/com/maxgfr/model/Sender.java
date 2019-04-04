package com.maxgfr.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

  private static Sender single_instance = null;
  private String queue_name = null;

  private Sender(String queue_name) {
    this.queue_name = queue_name;
  }

  public static Sender getInstance(String queue_name) {
      if (single_instance == null)
          single_instance = new Sender(queue_name);
      return single_instance;
  }

  public void sendData (String msg) {
    try{
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      channel.queueDeclare(this.queue_name, false, false, false, null);
      channel.basicPublish("", this.queue_name, null, msg.getBytes("UTF-8"));
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
