package com.ayu.filter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;
@Service
public class RegularService {

//@Autowired
//private MailUtility mailUtility ;

@Async
public void registerUser(String ip,String date,String type,String document){

	System.out.println(" Attack from  "+ip +" captured at"+ date+"type of attack is"+" "+type);
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

	DB db = mongoClient.getDB(document);
	DBCollection coll;
	coll = db.getCollection(document);
	BasicDBObject doc = new BasicDBObject("Ip-Address", ip).
	        append("Date", date).append("Attack-Type", type);

	//System.out.println("Data Display");
	coll.insert(doc);
	mongoClient.close();


	System.out.println(" Asynchronous method call of database — Complete ");

	}
@Async
public void camCall(){
	Webcam webcam = Webcam.getDefault();
	if (webcam != null) {
	try{
	webcam.open();
	BufferedImage image = webcam.getImage();
	ImageIO.write(image, "PNG", new File("/home/ayushman/workspace/CloudDenialFilter/Attacker_Image.png"));
	webcam.close();
	}
	catch(Exception e){System.out.println(e);}
	webcam.close();
	} else {
	System.out.println("No webcam detected");
	}

}
}
