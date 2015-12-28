package com.neustar.reservations.data.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity(name="seat")
public class Seat  implements Serializable{
	
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}

	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	/**
	 * @return the reservation
	 */
	public Reservation getReservation() {
		return reservation;
	}
	/**
	 * @param reservation the reservation to set
	 */
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((reservation == null) ? 0 : reservation.hashCode());
		result = prime * result + rowNum;
		result = prime * result + seatNum;
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
		if (!(obj instanceof Seat)) {
			return false;
		}
		Seat other = (Seat) obj;
		if (id != other.id) {
			return false;
		}
		if (reservation == null) {
			if (other.reservation != null) {
				return false;
			}
		} else if (!reservation.equals(other.reservation)) {
			return false;
		}
		if (rowNum != other.rowNum) {
			return false;
		}
		if (seatNum != other.seatNum) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Seat [reservation=" + reservation + ", rowNum=" + rowNum + ", seatNum=" + seatNum + ", id=" + id
				+ ", version=" + version + "]";
	}
	

	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name = "reservation_id", nullable = true)
	Reservation reservation;
	
	@Column(name = "rowNum", nullable = false)
	int rowNum;
	
	@Column(name = "seatNum", nullable = false)
	int seatNum;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	
	@Column(name = "version",  nullable = false)
	@Version
	private Integer version;
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
		
}
