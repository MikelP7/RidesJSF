package modelo.dataAccess;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import configuration.UtilDate;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import modelo.dominio.Ride;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.JPAUtil;
import modelo.dominio.Driver;



public class DataAccess  {

     public DataAccess(){}
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			List<String> result = em.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class).getResultList();
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
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<String> query = em.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
			query.setParameter(1, from);
			List<String> arrivingCities = query.getResultList(); 
			return arrivingCities;
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
	
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		EntityManager em = JPAUtil.getEntityManager();
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException();
			}
			em.getTransaction().begin();
			
			Driver driver = em.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				em.getTransaction().commit();
				throw new RideAlreadyExistException();
			}
			
			Ride ride = new Ride();
			
			ride.setFrom(from);
			ride.setTo(to);
			ride.setBetMinimum(nPlaces);
			ride.setDate(date);
			ride.setPrice(price);
			
			List<Integer> result = em.createQuery("SELECT MAX(r.rideNumber) FROM Ride r", Integer.class).getResultList();
			
			if(result.get(0) != null) {
				ride.setRideNumber(result.get(0)+1);
			}
			else {
				ride.setRideNumber(1);
			}
			
			ride.setDriver(driver);

			em.persist(ride);
			em.persist(driver); 
			em.getTransaction().commit();

			return ride;
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
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		EntityManager em = JPAUtil.getEntityManager();
		try {
			
			Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);

	        Date start = cal.getTime();
	        
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	        Date end = cal.getTime();

			
			List<Ride> res = new ArrayList<Ride>();	
			TypedQuery<Ride> query = em.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date>=?3 AND r.date<?4",Ride.class);   
			query.setParameter(1, from);
			query.setParameter(2, to);
			query.setParameter(3, start);
			query.setParameter(4, end);
			List<Ride> rides = query.getResultList();
		 	for (Ride ride:rides){
		 		res.add(ride);
			}
		 	return res;
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
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		EntityManager em = JPAUtil.getEntityManager();
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = em.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	}
	
	public Driver getDriverByEmail(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(Driver.class, email);
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
	
	public Driver createDriver(String email, String name, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			Driver existe = em.find(Driver.class, email);
			
			if(existe != null) {
				return null;
			}
			
			Driver d = new Driver();
			d.setEmail(email);
			d.setName(name);
			d.setPassword(password);
			d.setRides(new ArrayList<Ride>());
			
			em.getTransaction().begin();
			em.persist(d);
			em.getTransaction().commit();
			
			return d;
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
	
	public List<Ride> getRidesByEmail(String email){
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			Driver d = em.find(Driver.class, email);
			
			if(d == null) {
				return null;
			}
			
			return d.getRides();
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
	
	
	public List<Ride> GetRidesByDestination(String to){
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			List<Ride> res = new ArrayList<Ride>();	
			TypedQuery<Ride> query = em.createQuery("SELECT r FROM Ride r WHERE r.to=?1",Ride.class);   
			query.setParameter(1, to);
			List<Ride> rides = query.getResultList();
		 	
			for (Ride ride:rides){
		 		res.add(ride);
			}
		 	return res;
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
	
	public List<String> getAllDestinations() {
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			List<String> result = em.createQuery("SELECT DISTINCT r.to FROM Ride r ORDER BY r.to", String.class).getResultList();
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
}
	

