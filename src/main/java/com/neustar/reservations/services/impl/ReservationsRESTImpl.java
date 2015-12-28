package com.neustar.reservations.services.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
@Path("/")
public class ReservationsRESTImpl {
	
	@Autowired
	private NeustarReservationsImpl  neustarReservationsImpl;
	
	private Gson gson = new GsonBuilder().create();
	
	@GET
    @Path("/reservations/confirmed")
    @Produces({ "application/json" })
	public String  getAllReservationsConfirmed() throws Exception{
		
		String payloadStr = gson.toJson(neustarReservationsImpl.getAllReservationsConfirmed());
		return payloadStr;
	}
	
	@GET
    @Path("/reservations/HB")
    @Produces({ "application/json" })
	public String  getAllReservationsHB() throws Exception{
		
		String payloadStr = gson.toJson(neustarReservationsImpl.getResrvationsHB());
		return payloadStr;
	}

	@GET
    @Path("/reservations/onhold")
    @Produces({ "application/json" })	
	public String  getAllReservationsOnHold() throws Exception{
		String payloadStr = gson.toJson(neustarReservationsImpl.getAllReservationsOnHold());
		return payloadStr;
	}

	@GET
    @Path("/reservations/availableseats")
    @Produces({ "application/json" })	
	public String getAllAvaialableSeats() throws Exception {
		String payloadStr = gson.toJson(neustarReservationsImpl.getAllAvaialableSeats());
		return payloadStr;
	}

	@GET
    @Path("/reservations/availableseats/{rowNum}")
    @Produces({ "application/json" })	
	public String  getAvailableSeatsByRow(@PathParam(value = "rowNum") int rowNum) throws Exception{
		String payloadStr = gson.toJson(neustarReservationsImpl.getAvailableSeatsByRow(rowNum));
		return payloadStr;
	}


	@POST
    @Path("/reservations/")
    @Produces({ "application/json" })	
	@Consumes({ "application/json" })
	public String setReservation(com.neustar.reservations.services.model.Reservation rsv) throws Exception{
		return neustarReservationsImpl.setHoldOrCnfrm(rsv, rsv.getConfirmed()).toString();
	}
	
}
