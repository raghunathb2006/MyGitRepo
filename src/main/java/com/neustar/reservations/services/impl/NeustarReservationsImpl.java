package com.neustar.reservations.services.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neustar.reservations.dao.DataException;
import com.neustar.reservations.dao.ReservationsDAO;
import com.neustar.reservations.dao.SeatDAO;
import com.neustar.reservations.data.model.Reservation;
import com.neustar.reservations.data.model.Seat;
import com.neustar.reservations.services.IReservations;

@Service
public class NeustarReservationsImpl implements IReservations{
	
	@Autowired
	private SeatDAO  seatsDAO;
	
	@Autowired
	private ReservationsDAO  reservationsDAO;

	@Override
	public Set<com.neustar.reservations.services.model.Reservation> getAllReservationsConfirmed() throws Exception{
		List<com.neustar.reservations.services.model.Seat>  seats = new ArrayList<>();
		List<Seat> seatsDat = seatsDAO.findSeatsConfirmed();
		Set<com.neustar.reservations.services.model.Reservation> reservations = dtoReservation(seats, seatsDat);
		return reservations;
	}

	/**
	 * @param seats
	 * @param seatsDat
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private Set<com.neustar.reservations.services.model.Reservation> dtoReservation(
			List<com.neustar.reservations.services.model.Seat> seats, List<Seat> seatsDat)
					throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		Map<Integer,com.neustar.reservations.services.model.Reservation> reservations = dtoSeats(seats, seatsDat);		
		Set<com.neustar.reservations.services.model.Reservation> setRsrvs = new HashSet<>();
		setRsrvs.addAll(reservations.values());
		return setRsrvs;
	}

	@Override
	public Set<com.neustar.reservations.services.model.Reservation> getAllReservationsOnHold() throws Exception{
		List<com.neustar.reservations.services.model.Seat>  seats = new ArrayList<>();
		List<Seat> seatsDat = seatsDAO.findSeatsOnHold();
		Set<com.neustar.reservations.services.model.Reservation> reservations = dtoReservation(seats, seatsDat);
		return reservations;
	}

	@Override
	public List<com.neustar.reservations.services.model.Seat> getAllAvaialableSeats() throws DataException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		List<com.neustar.reservations.services.model.Seat>  seats = new ArrayList<>();
		List<Seat> seatsDat = seatsDAO.getAvailableSeats();
		dtoSeats(seats, seatsDat);
		
		return seats;
	}



	@Override
	public List<com.neustar.reservations.services.model.Seat> getAvailableSeatsByRow(int rowNum) throws Exception{
		// TODO Auto-generated method stub
		List<com.neustar.reservations.services.model.Seat>  seats = new ArrayList<>();
		List<Seat> seatsDat = seatsDAO.findAvailableSeatByRowNum(rowNum);
		dtoSeats(seats, seatsDat);
		
		return seats;
	}

	@Override
	public Boolean setHoldOrCnfrm(com.neustar.reservations.services.model.Reservation rsv, Boolean confirmed) throws Exception{
		// TODO Auto-generated method stub
		rsv.setConfirmed(confirmed);
		Reservation rsvDat = new Reservation();
		PropertyUtils.copyProperties( rsvDat, rsv);
		for (com.neustar.reservations.services.model.Seat seat : rsv.getSeats()) {
			Seat seatDat = new Seat();
			PropertyUtils.copyProperties( seatDat , seat);
			seatDat.setReservation(rsvDat);
			rsvDat.getSeats().add(seatDat);
		}
		return reservationsDAO.makeReservation(rsvDat);
	}
	
	private Map<Integer,com.neustar.reservations.services.model.Reservation> dtoSeats(List<com.neustar.reservations.services.model.Seat> seats, List<Seat> seatsDat)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<Integer,com.neustar.reservations.services.model.Reservation> rsvs = new HashMap<>();

		for (Seat seatDat : seatsDat) {
			com.neustar.reservations.services.model.Seat  seatSvc = new com.neustar.reservations.services.model.Seat();
			PropertyUtils.copyProperties(seatSvc,seatDat );
			
			seats.add(seatSvc);
			
			com.neustar.reservations.services.model.Reservation tmpRsrv = rsvs.get(seatDat.getReservation().getReservation_id());
			if (tmpRsrv == null) {
				com.neustar.reservations.services.model.Reservation dest = new com.neustar.reservations.services.model.Reservation();
				PropertyUtils.copyProperties(dest , seatDat.getReservation());
				rsvs.put(dest.getReservation_id(), dest);
				tmpRsrv = dest;
			}
			tmpRsrv.getSeats().add(seatSvc);
		}
		return rsvs;
	}

	@Override
	public List<com.neustar.reservations.services.model.Reservation> getResrvationsHB() throws Exception {
		List<com.neustar.reservations.services.model.Reservation> lstRsrv = new ArrayList<>();
		for (Reservation rsrvDat : reservationsDAO.findReservations() ) {
			com.neustar.reservations.services.model.Reservation  dest = new com.neustar.reservations.services.model.Reservation();
			PropertyUtils.copyProperties(dest, rsrvDat);
			lstRsrv.add(dest);
		}
		
		return lstRsrv;
	}

}
