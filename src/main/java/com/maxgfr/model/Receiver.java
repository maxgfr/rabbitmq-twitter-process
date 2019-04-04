package com.maxgfr.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {

  private static Receiver single_instance = null;
  private String queue_name = null;
  private DeliverCallback deliver_callback = null;

  private Receiver(String queue_name, DeliverCallback deliver_callback) {
    this.queue_name = queue_name;
    this.deliver_callback = deliver_callback;
  }

  public static Receiver getInstance(String queue_name, DeliverCallback deliver_callback) {
      if (single_instance == null)
          single_instance = new Receiver(queue_name, deliver_callback);
      return single_instance;
  }

  public void launch () {
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      channel.queueDeclare(this.queue_name, false, false, false, null);
      channel.basicConsume(this.queue_name, true, this.deliver_callback, consumerTag -> { });
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
