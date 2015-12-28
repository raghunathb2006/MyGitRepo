package com.neustar.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.Calendar;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.neustar.reservations.data.model.Customer;
import com.neustar.reservations.services.impl.NeustarReservationsImpl;
import com.neustar.reservations.services.impl.SpringMongoConfig;
import com.neustar.reservations.services.model.Reservation;
import com.neustar.reservations.services.model.Seat;

@Test
public class RestServiceTestNG {
     
  //  @Test
    @Test
	public void testngInsert() {
         
        
    	RestAssured.baseURI  = "http://127.0.0.1:8080/reservations/walmart/reservations";	

    	Response r = given()
    	.contentType("application/json").
    	body(prseJson()).
        when().
        post("");

    	String body = r.getBody().asString();
    	System.out.println(body);
    }
    
    @Test
	public void testngGet() {
         
        
    	RestAssured.baseURI  = "http://127.0.0.1:8080/reservations/walmart/reservations/availableseats";	

    	Response r = given()
    	.contentType("application/json").
        get("");

    	String body = r.getBody().asString();
    	System.out.println(body);
    }
    
	//lue 'Dec 6, 2015 11:15:45 PM': not a valid representation (error: Can not parse date "Dec 6, 2015 11:15:45 PM":."yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"))
    public String prseJson() {
    	Reservation rsrv = new Reservation();
    	rsrv.setReservation_id(1);
    	rsrv.setConfirmed(true);
    	rsrv.setConfirmed_dt(Calendar.getInstance().getTime());
    	Seat seat = new Seat();
    	seat.setId(1);
    	seat.setRowNum(1);
    	seat.setSeatNum(1);
    	//seat.
		rsrv.addSeat(seat );
		Gson gson = new GsonBuilder().create();
		String payloadStr = gson.toJson(rsrv);
		System.out.println("JSON:"+payloadStr);
		payloadStr = "{\"reservation_id\":1,\"seats\":[{\"rowNum\":1,\"seatNum\":1,\"id\":1}],\"confirmed\":true,\"confirmed_dt\":\"2015-12-06\"}";
		return payloadStr;
    }
    
    @Test
    public void testSpringMongoDataTest() throws Exception {
    	//ApplicationContext ctx = 
               // new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    	 ApplicationContext ctx = new GenericXmlApplicationContext("SpringContext.xml");
    	   MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");	

    	   NeustarReservationsImpl  resImpl = (NeustarReservationsImpl) ctx.getBean("neustarReservationsImpl");
    	  System.out.println("All Resrvations:"+ resImpl.getAllReservationsConfirmed());
   	Customer user = new Customer();
   	
   	user.setId("1");
   	user.setPassword("zzzzz");
   	user.setUsername("RaghuB");

   	// save
   	mongoOperation.save(user);

   	// now user object got the created id.
   	System.out.println("1. user : " + user);

   	// query to search user
   	Query searchUserQuery = new Query(Criteria.where("username").is("RaghuB"));

   	// find the saved user again.
   	Customer savedUser = (Customer) mongoOperation.findOne(searchUserQuery, Customer.class);
   	System.out.println("2. find - savedUser : " + savedUser);

   	// update password
   	mongoOperation.updateFirst(searchUserQuery, 
                            Update.update("password", "new password"),Customer.class);

   	// find the updated user object
   	Customer updatedUser = mongoOperation.findOne(searchUserQuery, Customer.class);

   	System.out.println("3. updatedUser : " + updatedUser);

   	// delete
   	mongoOperation.remove(searchUserQuery, Customer.class);

   	// List, it should be empty now.
   	List<Customer> listUser = mongoOperation.findAll(Customer.class);
   	System.out.println("4. Number of user = " + listUser.size());

    }
}