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
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost"); //Set the host
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDelete(this.queue_name); //Delete the queue to avoid multi-answered
    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //Declare the Exchange
    channel.queueDeclare(this.queue_name, true, false, false, null); //Declare the Queue name
    channel.queueBind(this.queue_name, EXCHANGE_NAME, "camel"); //Bind the Queue



    Files.forEach(name -> {
        JSONObject file = ReadJsonFile.read(name); //Put the file in a JSON Object
          try {
        channel.basicPublish("", this.queue_name, null, file.toString().getBytes("UTF-8")); //Send the message
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      });

  DeliverCallback deliverCallback = (RecupFile, messageRecup) -> { //Recevied the msg
      String message = new String(messageRecup.getBody(), "UTF-8");
      com.google.gson.JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();

      Date date = new Date(); //Create a date
      SimpleDateFormat ft = new SimpleDateFormat ("y-MM-d"); //Format the date

      jsonObject.addProperty("accepted_date", ft.format(date)); //Add the acceptedDate to the JSON

      String reviewer_name = "Computer_1"; //Create the Reviewer name
      jsonObject.addProperty("reviewer", reviewer_name); //Add it to the JSON

      String title = jsonObject.get("title").toString(); //Find the title
      title = title.substring(1, title.length()-1); //Erase quotation mark

      //Write the file
      try (FileWriter file = new FileWriter("src/main/resources/articlesAccepted/" + title + ".json")) {
            file.write(jsonObject.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

  };

  channel.basicConsume(this.queue_name, true, deliverCallback, RecupFile -> { }); //Allow the delivering
  }
}
