package com.neustar.reservations.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neustar.reservations.data.model.Reservation;
import com.neustar.reservations.data.model.Seat;

@Service
@Transactional
public class ReservationsDAOImpl implements ReservationsDAO {

	
	//@Inject()
	@PersistenceContext(unitName="Walmart_Seats1")
	private EntityManager entMgr;
	
	//@Resource
	//private SessionContext sessionContext;

	
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
	//@Transactional
	public Boolean makeReservation(Reservation rsv) throws DataException {
		
		Reservation resDat = null;
		//HibernateEntityManager hem = entMgr.unwrap(HibernateEntityManager.class);
		//Session session = (Session)entMgr.getDelegate();
		//session.getTransaction().begin();
		try {
		if ((resDat=entMgr.find(Reservation.class, rsv.getReservation_id())) == null ) {
			return reconcilliateSeats(rsv);
		} else {
			if (resDat.getConfirmed()) {
				return false;
			}
			else if ( (Calendar.getInstance().getTimeInMillis() - resDat.getConfirmed_dt().getTime()) > 10*60 ) {
				for ( Seat seat : rsv.getSeats()) {
					seat.setReservation(null);
				}
				entMgr.persist(rsv);
				for (Seat seat : rsv.getSeats()) {
					entMgr.persist(seat);
				}
				return false; // Expire the Old Resrvation to facilitate the others to go for reservation.
			} 
			else {
				return reconcilliateSeats(rsv);
			}
		} 
		} catch (Exception ex) {
			//session.getTransaction().rollback();
			throw ex;
		} finally {
			//session.getTransaction().commit();
		}
	}

	/**
	 * @param rsv
	 * @return
	 * @throws DataException 
	 */
	//@Transactional
	private Boolean reconcilliateSeats(Reservation rsv) throws DataException {
		List<Seat>  seatsDat = new ArrayList<>();
		for ( Seat seat : rsv.getSeats()) {
			Seat seatDat = (Seat) entMgr.createQuery("from com.neustar.reservations.data.model.Seat S where S.rowNum="+seat.getRowNum()+" and S.seatNum="+seat.getSeatNum()).getSingleResult();
			if (seatDat.getReservation() != null && seatDat.getReservation().getReservation_id() != rsv.getReservation_id() && (seatDat.getReservation().getConfirmed() ||
					(Calendar.getInstance().getTimeInMillis() - seatDat.getReservation().getConfirmed_dt().getTime()) < 10*60)) {
				return false;// Error condition as the seat is already reserved by others or confirmed in waiting.
			}
			//seat.setReservation(rsv);
			seatsDat.add(seatDat);
		}
		if (seatsDat.size() <= 0) {
			throw new DataException("There are no Seats avaialble or setup for the reservation.");
		}
		rsv.getSeats().clear();
		rsv.getSeats().addAll(seatsDat);
		entMgr.persist(rsv);
		for (Seat seat : rsv.getSeats()) {
			seat.setVersion(seat.getVersion()+1);
			seat.setReservation(rsv);
			entMgr.merge(seat);
		}
		return true;
	}

	@Override
	//@Transactional
	public void cancelReservation(Reservation rsv) throws DataException {
		//HibernateEntityManager hem = entMgr.unwrap(HibernateEntityManager.class);
		//Session session = (Session)entMgr.getDelegate();
		//session.getTransaction().begin();
		entMgr.remove(rsv);	
		//session.getTransaction().commit();
	}

	@Override
	public Reservation  findReservationOnHold(int id) throws DataException {
		return findReservation(id,false);
		
	}
	
	@Override
	public Reservation  findReservationConfirmed(int id) throws DataException {
		return findReservation(id,true);
		
	}
	
	
	private Reservation  findReservation(int id, boolean confirmed) throws DataException {
		Query qry = entMgr.createQuery("from com.neustar.reservations.data.model.Reservation R where  R.reservation_id="+id+" and R.confirmed="+confirmed+"");
		return (Reservation) qry.getSingleResult();
		
	}
	
	@Override
	public Reservation  findReservationBySeat(int rowNum,int id) throws DataException {
		Query qry = entMgr.createNativeQuery("select R.* from Reservation R, Seat S  where S.id="+id+" and S.rowNum="+rowNum+" and S.reservation_id is not null  and  R.reservation_id=S.reservation_id");
		return (Reservation) qry.getSingleResult();
	}
		
		

	@Override
	//@Transactional
	public void updateReservation(Reservation rsv) throws DataException {
		//HibernateEntityManager hem = entMgr.unwrap(HibernateEntityManager.class);
		//Session session = (Session)entMgr.getDelegate();
		//session.getTransaction().begin();
		reconcilliateSeats(rsv);
		entMgr.merge(rsv);
		//session.getTransaction().commit();
	}

	@Override
	public List<Reservation> findReservations() {
		
		return entMgr.createQuery("from com.neustar.reservations.data.model.Reservation R").getResultList();
	}

}
