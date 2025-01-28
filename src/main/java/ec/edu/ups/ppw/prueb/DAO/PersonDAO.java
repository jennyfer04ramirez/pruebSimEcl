package ec.edu.ups.ppw.prueb.DAO;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PersonDAO {

	@PersistenceContext
	EntityManager em;
	
	
}
