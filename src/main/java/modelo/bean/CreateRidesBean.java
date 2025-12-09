package modelo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;

import modelo.businessLogic.*;
import modelo.dataAccess.DataAccess;
import modelo.dominio.Ride;
import modelo.exceptions.RideAlreadyExistException;
import modelo.exceptions.RideMustBeLaterThanTodayException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;

@Named("createRides")
@SessionScoped
public class CreateRidesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String departCity;
	private String ArrivalCity;
	private Integer nPlaces;
	private Float price;
	private Date fecha;

	BLFacade bl = new BLFacadeImplementation();
	
	public CreateRidesBean() {		
		fecha = new Date();
		departCity = "";
		ArrivalCity = "";
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
	
	public Integer getnPlaces() {
		return nPlaces;
	}

	public void setnPlaces(Integer nPlaces) {
		this.nPlaces = nPlaces;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String moveToMenu() {
		return "me";
	}
	
	public void handlePrice() {	    
		
	}
	
	public void createRide() throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
		if(departCity.equals("") || ArrivalCity.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Depart city and Arrival city cannot be empty"));
			return;
		}	
		
		try {
				bl.createRide(departCity, ArrivalCity, fecha, nPlaces, price, "test1@gmail.com");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ride successfully created!"));
		} 
		catch (exceptions.RideAlreadyExistException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Ride already exist"));
		} 
		catch (exceptions.RideMustBeLaterThanTodayException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Ride must be later than today"));
		}
	}
}
