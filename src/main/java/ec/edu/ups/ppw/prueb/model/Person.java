package ec.edu.ups.ppw.prueb.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class Person {

	@Id
	private String cedula;
	private String name;
	
    @OneToMany(fetch = FetchType.LAZY)
    private List<Consum> consumos;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Debs> deudas;
	
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Consum> getConsumos() {
		return consumos;
	}
	public void setConsumos(List<Consum> consumos) {
		this.consumos = consumos;
	}
	public List<Debs> getDeudas() {
		return deudas;
	}
	public void setDeudas(List<Debs> deudas) {
		this.deudas = deudas;
	}
		
	
}
