package principal;
import jakarta.persistence.EntityManager;
import modelo.JPAUtil;
import modelo.dominio.Driver;
import modelo.dominio.Ride;

import java.util.*;
public class CrearDrivers {
	
	public CrearDrivers() {}
	
	private void createAndStoreDriver(String email, String name, List<Ride> rides) {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			Driver d = new Driver();
			d.setEmail(email);
			d.setName(name);
			
			if (!rides.isEmpty()) {
				d.setRides(rides);
			}
			
			em.persist(d);
			em.getTransaction().commit();
		} 
		catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} 
		finally {
			em.close();
		}
	}
	
	private List<Driver> listaDrivers() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			List<Driver> result = em.createQuery("from Driver", Driver.class).getResultList();
			em.getTransaction().commit();
			return result;
		} 
		catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} 
		finally {
			em.close();
		}
	}
	
	public static void main(String[] args) {
		CrearDrivers cd = new CrearDrivers();
		System.out.println("Creación de Drivers:");
		//cd.createAndStoreDriver("test1@gmail.com", "test1", new ArrayList<Ride>());
		//cd.createAndStoreDriver("test2@gmail.com", "test2", new ArrayList<Ride>());
		//cd.createAndStoreDriver("test3@gmail.com", "test3", new ArrayList<Ride>());
		//cd.createAndStoreDriver("test4@gmail.com", "test4", new ArrayList<Ride>());
		
		System.out.println("Listado de Drivers:");
		List<Driver> drivers = cd.listaDrivers();
		
		for (Driver d : drivers) {
			System.out.println(d.toString());
		}
	}
}
