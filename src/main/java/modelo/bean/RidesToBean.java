package modelo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import modelo.businessLogic.*;

import modelo.dominio.Ride;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("ridesTo")
@SessionScoped
public class RidesToBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ArrivalCity;
	private List<String> ArrivalCities = new ArrayList<String>();
	private List<Ride> availableRides;

	BLFacade bl = new BLFacadeImplementation();
	
	public RidesToBean() {
		ArrivalCities.addAll(bl.getAllDestinations());
		ArrivalCity = ArrivalCities.get(0);
	}
	


	public String getArrivalCity() {
		return ArrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		ArrivalCity = arrivalCity;
	}


	public List<String> getArrivalCities() {
		return ArrivalCities;
	}

	public void setArrivalCities(List<String> arrivalCities) {
		ArrivalCities = arrivalCities;
	}
	
	public List<Ride> getAvailableRides() {
		return availableRides;
	}

	public void setAvailableRides(List<Ride> availableRides) {
		this.availableRides = availableRides;
	}
	
	public String moveToMenu() {
		return "me";
	}
	
	
	public void searchAvailableRides()	{
		availableRides = bl.GetRidesByDestination(ArrivalCity);
	}
}
