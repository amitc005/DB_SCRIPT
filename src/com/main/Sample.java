package com.main;


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Sample {

	public static void main(String[] args) {
		 // JDBC driver name and database URL
		   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   final String DB_URL = "jdbc:mysql://localhost:5235";
		   final String DB_NAME = "offsetmanager";
		   final String SQL_SCRIPT_PATH = "C:\\Users\\redhood\\Downloads\\sqlnewId.sql";

		   //  Database credentials
		   final String USER = "root";
		   final String PASS = "amitmanutd@nija.com0052311";
		   
		  
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating database...");
		      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		      
		      String sql = "show databases";
		      ResultSet result = stmt.executeQuery(sql);
		      ArrayList <String> list = new ArrayList<String>(); 
		      while(result.next())
		      {
		    	 list.add(result.getString(1));
		      }
		      
		      if(!list.contains(DB_NAME))
		      {
//		    	  String insertScript = "source C:\\Users\\redhood\\Downloads\\sqlnewId.sql";
//		    	  boolean flag = stmt.execute(insertScript);
//		    	  if(flag){
//		    		  System.out.println("Database is ready");
//		    	  }else{
//		    		  System.out.println("error");
//		    	  }
		    	  BufferedReader br = new BufferedReader(new FileReader(SQL_SCRIPT_PATH));
		    	  
		    	  System.out.println("Reading SQL File...");
		    	  String line="";
		    	  StringBuilder sb = new StringBuilder();

		    	  while( (line=br.readLine())!=null)
		    	  {
		    	     if(line.length()==0 || line.startsWith("--"))
		    	     {
		    	        continue;
		    	     }else
		    	     {
		    	        sb.append(line);
		    	     } 

		    	     if(line.trim().endsWith(";"))
		    	     {
		    	    	stmt.execute(sb.toString());
		    	        sb = new StringBuilder();
		    	     }

		    	  }
		      }else{
		    	  System.out.println("Database is already present in server");
		      }
		      
		  
		      
		     
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Opreation Ended");
	}

}
