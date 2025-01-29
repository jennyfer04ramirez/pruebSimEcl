package ec.edu.ups.ppw.prueb.DAO;

import java.util.List;

import ec.edu.ups.ppw.prueb.model.Consum;
import ec.edu.ups.ppw.prueb.model.Debs;
import ec.edu.ups.ppw.prueb.model.Person;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class PersonDAO {

	@PersistenceContext
	EntityManager em;
	
	public void addPerson(Person person) {
        em.persist(person);
    }
	
	public List<Person> getPerson() {
        String jpql = "SELECT p FROM Person p LEFT JOIN FETCH p.consumos LEFT JOIN FETCH p.deudas";
        TypedQuery<Person> query = em.createQuery(jpql, Person.class);
        return query.getResultList();
    }

    public Person getPersonByCedula(String cedula) {
        String jpql = "SELECT p FROM Person p LEFT JOIN FETCH p.consumos LEFT JOIN FETCH p.deudas WHERE p.cedula = :cedula";
        TypedQuery<Person> query = em.createQuery(jpql, Person.class);
        query.setParameter("cedula", cedula);
        return query.getSingleResult();
    }
	
	public void addConsum(Consum consum) {
        em.persist(consum);
    }
	
	public void addDebs(Debs debs) {
        em.persist(debs);
    }
	
	
}
