package modelo.bean;

import java.io.Serializable;
import java.util.Date;

import modelo.businessLogic.*;
import modelo.exceptions.RideAlreadyExistException;
import modelo.exceptions.RideMustBeLaterThanTodayException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
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
	
	@Inject
    private LoginBean lb;

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
				bl.createRide(departCity, ArrivalCity, fecha, nPlaces, price, lb.getEmail());
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
