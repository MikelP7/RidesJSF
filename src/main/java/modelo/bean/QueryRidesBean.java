package modelo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;

import modelo.businessLogic.*;
import modelo.dataAccess.DataAccess;
import modelo.dominio.Ride;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("queryRides")
@SessionScoped
public class QueryRidesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String departCity;
	private String ArrivalCity;
	private List<String> DepartCities = new ArrayList<String>();
	private List<String> ArrivalCities = new ArrayList<String>();
	private Date fecha;
	private List<Ride> availableRides;

	BLFacade bl = new BLFacadeImplementation();
	
	public QueryRidesBean() {
		DepartCities.addAll(bl.getDepartCities());
		departCity = DepartCities.get(0);
		
		ArrivalCities = bl.getArrivalCities(departCity);
		ArrivalCity = ArrivalCities.get(0);
		
		fecha = new Date();
	}
	
	public Date getFecha() {
		return this.fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return ArrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		ArrivalCity = arrivalCity;
	}

	public List<String> getDepartCities() {
		return DepartCities;
	}

	public void setDepartCities(List<String> departCities) {
		DepartCities = departCities;
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
	
	public void onDateSelect(SelectEvent event) {
		System.out.print(event.getObject());
	}
	
	public void handleDepartCitySelection() {
		if (departCity != null) {
			ArrivalCities = bl.getArrivalCities(departCity);
		} 
		else {
			ArrivalCities = new ArrayList<String>();
		}
	}
	
	public void searchAvailableRides()	{
		availableRides = bl.getRides(departCity, ArrivalCity, fecha);
		System.out.println("from: " + departCity);
		System.out.println("to: " + ArrivalCity);
		System.out.println("date: " + fecha);
		System.out.println(availableRides);
	}
}
