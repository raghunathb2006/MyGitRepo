package com.neustar.reservations.services.model;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reservation [Reservation_id=" + reservation_id + ", seats=" + seats + ", confirmed=" + confirmed + ", confirmed_dt="
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
	public void addSeat(Seat seat) {
		getSeats().add( seat);
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
	
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int id) {
		this.reservation_id = id;
	}
	public Date getConfirmed_dt() {
		return confirmed_dt;
	}
	public void setConfirmed_dt(Date confirmed_dt) {
		this.confirmed_dt = confirmed_dt;
	}


	int reservation_id;
	

	List<Seat> seats = new ArrayList<>();
	Boolean confirmed;
	Date  confirmed_dt;
	

	protected Integer version;

	
}
