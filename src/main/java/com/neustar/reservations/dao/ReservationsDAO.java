package com.neustar.reservations.dao;

import java.util.List;

import com.neustar.reservations.data.model.Reservation;

public interface ReservationsDAO {
	
	public Boolean makeReservation(Reservation rsv) throws DataException;
	public void cancelReservation(Reservation rsv) throws DataException;
	public Reservation findReservationOnHold(int id) throws DataException;
	public void updateReservation(Reservation rsv) throws DataException;
	Reservation findReservationBySeat(int rowNum, int id) throws DataException;
	Reservation findReservationConfirmed(int id) throws DataException;
	public List<Reservation> findReservations();

}



