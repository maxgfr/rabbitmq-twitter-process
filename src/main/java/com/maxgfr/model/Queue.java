package com.maxgfr.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Queue {

  private static Queue single_instance = null;
  private String queue_name = null;

  private Queue(String queue_name) {
    this.queue_name = queue_name;
  }

  public static Queue getInstance(String queue_name) {
      if (single_instance == null)
          single_instance = new Queue(queue_name);
      return single_instance;
  }

  public void sendData (String msg) {

  }
}
