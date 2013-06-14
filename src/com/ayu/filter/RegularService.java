package com.ayu.filter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.github.sarxos.webcam.Webcam;
/*
 * @Author
 * Ayushman Dutta
 * Email ayushman999@gmail.com
 * CopyRight Ayushman Dutta,2013
 *  This file is part of CloudIDS.
    CloudIDS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CloudIDS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CloudIDS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
@Service
public class RegularService {

//@Autowired
//private MailUtility mailUtility ;

@Async
public void registerUser(String ip,String date,String type,String document){

	//System.out.println(" Attack from  "+ip +" captured at"+ date+" "+"type of attack is"+" "+type);
	//System.out.println(" Database Insertion ");


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


	//System.out.println(" Asynchronous method call of database — Complete ");

	}
@Async
public void camCall(String ip){

	Webcam webcam = Webcam.getDefault();
	if (webcam != null) {
	try{
	webcam.open();
	BufferedImage image = webcam.getImage();
	ImageIO.write(image, "PNG", new File("/home/ayushman/workspace/CloudDenialFilter/"+ip+".png"));
	webcam.close();
	}
	catch(Exception e){System.out.println(e);}
	webcam.close();
	} else {
	System.out.println("No webcam detected");
	}

}

@Async
public void sendSSLMail(String text,String toMail) {

	final String username = "clouddefenceids";
	final String password = "";

	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getInstance(props,
	  new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	  });

	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(toMail));
		message.setSubject("Forgot Password");
		message.setText(text);

		Transport.send(message);

		System.out.println("Done");

	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}
}
}
