package com.ayu.filter;

import java.net.UnknownHostException;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import com.ayu.filter.MailUtility;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Service
public class RegularService {

//@Autowired
//private MailUtility mailUtility ;

@Async
public void registerUser(String ip,String date){

	System.out.println(" Attack from  "+ip +" captured at"+ date);
	System.out.println(" Database Insertion ");


	try {
	Thread.sleep(5000);

	} catch (InterruptedException e) {

	e.printStackTrace();
	}
	MongoClient mongoClient = null;
	try {
		mongoClient = new MongoClient();
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	// or, to connect to a replica set, supply a seed list of members
	//MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
	  //                                    new ServerAddress("localhost", 27018),
	    //                                  new ServerAddress("localhost", 27019)));

	DB db = mongoClient.getDB( "test" );
	DBCollection coll;
	coll = db.getCollection("test");
	BasicDBObject doc = new BasicDBObject("Ip-Address", ip).
	        append("Date", date);

	//System.out.println("Data Display");
	coll.insert(doc);
	DBCursor cursor = coll.find();
	try {
	   while(cursor.hasNext()) {
	       System.out.println(cursor.next());
	   }
	} finally {
	   //mongoClient.dropDatabase("test");
	   cursor.close();
	   
	}


	System.out.println(" Asynchronous method call of database — Complete ");

	}

}