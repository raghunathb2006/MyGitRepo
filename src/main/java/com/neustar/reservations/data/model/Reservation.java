package com.neustar.reservations.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity(name="reservation")
public class Reservation  implements Serializable{
	

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reservation [reservation_id=" + reservation_id + ", seats=" + seats + ", confirmed=" + confirmed + ", confirmed_dt="
				+ confirmed_dt + ", version=" + version + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((confirmed_dt == null) ? 0 : confirmed_dt.hashCode());
		result = prime * result + reservation_id;
		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Reservation)) {
			return false;
		}
		Reservation other = (Reservation) obj;
		if (confirmed == null) {
			if (other.confirmed != null) {
				return false;
			}
		} else if (!confirmed.equals(other.confirmed)) {
			return false;
		}
		if (confirmed_dt == null) {
			if (other.confirmed_dt != null) {
				return false;
			}
		} else if (!confirmed_dt.equals(other.confirmed_dt)) {
			return false;
		}
		if (reservation_id != other.reservation_id) {
			return false;
		}
		if (seats == null) {
			if (other.seats != null) {
				return false;
			}
		} else if (!seats.equals(other.seats)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	

	/**
	 * @return the reservation_id
	 */
	public int getReservation_id() {
		return reservation_id;
	}
	/**
	 * @param reservation_id the reservation_id to set
	 */
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	/**
	 * @return the confirmed_dt
	 */
	public java.util.Date getConfirmed_dt() {
		return confirmed_dt;
	}
	/**
	 * @param confirmed_dt the confirmed_dt to set
	 */
	public void setConfirmed_dt(java.util.Date confirmed_dt) {
		this.confirmed_dt = confirmed_dt;
	}
	


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "reservation_id", unique = true, nullable = false)
	int reservation_id;
	
	
	@Column(name = "confirmed")
	Boolean confirmed;
	
	@Column(name = "confirmed_dt")
	@Temporal(TemporalType.TIMESTAMP)
	java.util.Date  confirmed_dt;
	
	@Column(name = "version", nullable = false)
	@Version
	protected Integer version;
	

	@OneToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER,mappedBy="reservation")
	//@JoinColumn(name = "reservation_id", nullable = true)
	List<Seat> seats = new ArrayList<>();
	
}
