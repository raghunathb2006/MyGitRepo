package com.neustar.reservations.services;

import java.util.List;
import java.util.Set;

import com.neustar.reservations.services.model.Reservation;

public interface IReservations {
	
	public Set<com.neustar.reservations.services.model.Reservation> getAllReservationsConfirmed() throws Exception;
	public Set<Reservation> getAllReservationsOnHold()throws Exception;
	public List<com.neustar.reservations.services.model.Seat> getAllAvaialableSeats() throws Exception;
	public List<com.neustar.reservations.services.model.Seat> getAvailableSeatsByRow(int rowName) throws Exception;
	public Boolean setHoldOrCnfrm(com.neustar.reservations.services.model.Reservation t,Boolean confirmed) throws Exception;
	public List<Reservation> getResrvationsHB() throws Exception;

}
