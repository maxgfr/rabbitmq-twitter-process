package com.maxgfr.utils;

import java.sql.*;
import java.util.Date;

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
      ResultSet tbl = this.connection.getMetaData().getTables(null, null, name_table, null);
      if(!tbl.next()) {
        statement.execute(request);
      }
      statement.close();
      return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }

  }

  public boolean insertTable (String name_table, String ranking) {
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
      e.printStackTrace();
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
      e.printStackTrace();
    }

  }
}
