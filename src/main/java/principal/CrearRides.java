package principal;
import jakarta.persistence.EntityManager;
import modelo.JPAUtil;
import modelo.dominio.Driver;
import modelo.dominio.Ride;

import java.util.*;
public class CrearRides {
	
	
	public CrearRides() {}
	
	private void createAndStoreRideWithDriver(Integer rideNumber, String from, String to, Driver d) {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			Ride r = new Ride();
			r.setFrom(from);
			r.setTo(to);
			r.setPrice(Float.parseFloat("0"));
			r.setRideNumber(rideNumber);
			r.setDriver(d);
			r.setBetMinimum(2);
			r.setDate(new Date());
			
			em.persist(r);
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
	
	private List<Ride> listaRides() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			List<Ride> result = em.createQuery("from Ride", Ride.class).getResultList();
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
	
	private Driver getFirstDriver() {
		
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			List<Driver> result = em.createQuery("from Driver", Driver.class).getResultList();
			em.getTransaction().commit();
			return result.get(0);
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
		
		CrearRides cr = new CrearRides();
		System.out.println("Creación de Rides:");
		
		cr.createAndStoreRideWithDriver(10, "Donostia", "Bilbao", cr.getFirstDriver());
		cr.createAndStoreRideWithDriver(11, "Gasteiz", "Bilbao", cr.getFirstDriver());
		cr.createAndStoreRideWithDriver(12, "Bilbao", "Donostia", cr.getFirstDriver());
		
		System.out.println("Listado de Rides:");
		List<Ride> rides = cr.listaRides();
		
		for (Ride r : rides) {
			System.out.println(r.toString());
		}
	}
}
