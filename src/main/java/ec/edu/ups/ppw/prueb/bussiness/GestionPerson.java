package ec.edu.ups.ppw.prueb.bussiness;

import java.util.List;

import ec.edu.ups.ppw.prueb.DAO.PersonDAO;
import ec.edu.ups.ppw.prueb.model.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GestionPerson {

	@Inject
	private PersonDAO personDAO;
	
	public List<Person> getPerson() {
        return personDAO.getPerson();
    }

    public Person getPersonByCedula(String cedula) {
        return personDAO.getPersonByCedula(cedula);
    }
}
