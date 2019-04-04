package com.maxgfr.utils;

import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseManager {

  private static DatabaseManager single_instance = null;
  private Connection connection = null;

  private DatabaseManager() {
    try{
      // create a database connection
      this.connection = DriverManager.getConnection("jdbc:sqlite:message_bus.db");
    }
    catch(SQLException e){
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
  }

  public static DatabaseManager getInstance() {
      if (single_instance == null)
              single_instance = new DatabaseManager();
      return single_instance;
  }

  public boolean createTable (String name_table) {

    String request = "CREATE TABLE " + name_table  + "("
       + "timestamp text NOT NULL,"
       + "ranking text NOT NULL "
       + ")";

    try {
      Statement statement = this.connection.createStatement();
      statement.execute(request);
      statement.close();
      return true;
    } catch (Exception e) {
        System.err.println(e);
        return false;
    }

  }

  public boolean insertTable (String name_table, String ranking) throws Exception {
    String request = "INSERT INTO " + name_table + "(timestamp, ranking) VALUES(?,?)";

    try {
      PreparedStatement prepared_statement = this.connection.prepareStatement(request);
      Date date = new Date();
      String ts = Long.toString(date.getTime());
      prepared_statement.setString(1, ts);
      prepared_statement.setString(2, ranking);
      prepared_statement.executeUpdate();
      prepared_statement.close();
      return true;
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }
  }

  public void closeConnection () {
    try {
        if(this.connection != null)
          this.connection.close();
    }
    catch(SQLException e) {
      // connection close failed.
      System.err.println(e);
    }

  }
}
