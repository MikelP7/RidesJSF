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
	
	public String moveToMenu() {
		return "me";
	}
	
}
