package com.neustar.reservations.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neustar.reservations.data.model.Seat;

@Service
@Transactional
public class SeatsDAOImpl implements SeatDAO {

	//@Inject
	@PersistenceContext(unitName="Walmart_Seats1")
	private EntityManager entMgr;
	

	/**
	 * @return the entMgr
	 */
	public EntityManager getEntMgr() {
		return entMgr;
	}

	/**
	 * @param entMgr the entMgr to set
	 */
	public void setEntMgr(EntityManager entMgr) {
		this.entMgr = entMgr;
	}

	@Override
	public List<Seat>  findSeatsOnHold() throws DataException {
		return findSeats(false);
		
	}
	
	@Override
	public List<Seat>  findSeatsConfirmed() throws DataException {
		return findSeats(true);
		
	}
	
	
	private List<Seat>  findSeats( boolean confirmed) throws DataException {
		
	
		Query qry = entMgr.createQuery("select S from com.neustar.reservations.data.model.Seat S join fetch S.reservation where  S.reservation.reservation_id is not null and S.reservation.confirmed="+confirmed+"");
		return (List<Seat>) qry.getResultList();
		
	}
	
	@Override
	public Seat  findSeatBySeat(int rowNum,int id) throws DataException {
		Query qry = entMgr.createQuery("select S  from  com.neustar.reservations.data.model.Seat S  where S.id="+id+" and S.rowNum="+rowNum);
		return (Seat) qry.getSingleResult();
	}

	@Override
	public List<Seat> getAvailableSeats() throws DataException {
		Query qry = entMgr.createQuery("select S from com.neustar.reservations.data.model.Seat S  where S.reservation.reservation_id is null");
		return (List<Seat>)  qry.getResultList();
	}

	@Override
	public List<Seat> findAvailableSeatByRowNum(int rowNum) {
		Query qry = entMgr.createQuery("select S from com.neustar.reservations.data.model.Seat S  where S.reservation.reservation_id is null and S.rowNum="+rowNum);
		return (List<Seat>)  qry.getResultList();
	}
		
		
/*
	@Override
	@Transactional
	public void updateSeat(Seat rsv) throws DataException {
		entMgr.merge(rsv)		;
	}
	
	@Override
	@Transactional
	public void makeSeat(Seat rsv) throws DataException {
		entMgr.persist(rsv);

	}

	@Override
	@Transactional
	public void cancelSeat(Seat rsv) throws DataException {
		entMgr.remove(rsv);
		
	}
*/

}
