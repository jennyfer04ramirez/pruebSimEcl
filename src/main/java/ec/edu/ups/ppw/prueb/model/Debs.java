package ec.edu.ups.ppw.prueb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity

public class Debs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int hoursConsum;
	private double totalDeb;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHoursConsum() {
		return hoursConsum;
	}
	public void setHoursConsum(int hoursConsum) {
		this.hoursConsum = hoursConsum;
	}
	public double getTotalDeb() {
		return totalDeb;
	}
	public void setTotalDeb(double totalDeb) {
		this.totalDeb = totalDeb;
	}
	
	
}
