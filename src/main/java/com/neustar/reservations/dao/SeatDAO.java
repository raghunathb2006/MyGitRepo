package com.neustar.reservations.dao;

import java.util.List;

import com.neustar.reservations.data.model.Seat;

public interface  SeatDAO {
	
	List<Seat> getAvailableSeats() throws DataException ;
	
	List<Seat>  findSeatsOnHold() throws DataException ;
	
	List<Seat>  findSeatsConfirmed() throws DataException ;
	
	public Seat  findSeatBySeat(int rowNum,int id) throws DataException ;

	List<Seat> findAvailableSeatByRowNum(int rowNum);
	


}
