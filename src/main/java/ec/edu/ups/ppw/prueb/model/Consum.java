package ec.edu.ups.ppw.prueb.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class Consum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date dateConsum; 
	
	private int hourConsum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateConsum() {
		return dateConsum;
	}

	public void setDateConsum(Date dateConsum) {
		this.dateConsum = dateConsum;
	}

	public int getHourConsum() {
		return hourConsum;
	}

	public void setHourConsum(int hourConsum) {
		this.hourConsum = hourConsum;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	
	
	
}
