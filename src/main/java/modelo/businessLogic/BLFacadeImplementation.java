package modelo.businessLogic;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import modelo.dataAccess.DataAccess;
import modelo.dominio.*;
import modelo.exceptions.RideAlreadyExistException;
import modelo.exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		dbManager = new DataAccess();
	}    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){		
		return dbManager.getDepartCities();		    	
    }
    
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getArrivalCities(String from){		
		 return dbManager.getArrivalCities(from);		
	}

	/**
	 * {@inheritDoc}
	 * @throws exceptions.RideAlreadyExistException 
	 * @throws exceptions.RideMustBeLaterThanTodayException 
	 */
   @WebMethod
   public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException, exceptions.RideAlreadyExistException, exceptions.RideMustBeLaterThanTodayException{
		return dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
   }
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		return dbManager.getRides(from, to, date);
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		return dbManager.getThisMonthDatesWithRides(from, to, date);
	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
		dbManager.initializeDB();
	}
    
    @WebMethod
    public Driver getDriverByEmail(String email) {
    	return dbManager.getDriverByEmail(email);
    }
    
    @WebMethod 
    public Driver createDriver(String email, String name, String password) {
    	return dbManager.createDriver(email, name, password);
    }
    
    @WebMethod
    public List<Ride> getRidesByEmail(String email){
    	return dbManager.getRidesByEmail(email);
    }
    
    @WebMethod 
    public List<Ride> GetRidesByDestination(String to){
    	return dbManager.GetRidesByDestination(to);
    }
    
    @WebMethod 
    public List<String> getAllDestinations(){
    	return dbManager.getAllDestinations();
    }
}

