package modelo.bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("menu")
@SessionScoped
public class MenuBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String moveToQueryRides() {
		return "qr";
	}
	
	public String moveToCreateRides() {
		return "cr";
	}
	
	public String moveToMyRides() {
		return "mr";
	}
	
	public String moveToRidesTo() {
		return "rt";
	}
}
