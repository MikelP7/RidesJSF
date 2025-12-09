package modelo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;

import modelo.businessLogic.*;
import modelo.dataAccess.DataAccess;
import modelo.dominio.Driver;
import modelo.dominio.Ride;
import modelo.exceptions.RideAlreadyExistException;
import modelo.exceptions.RideMustBeLaterThanTodayException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;

@Named("register")
@SessionScoped
public class RegisterBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String name;
	private String password;
	private String rpassword;
	
	BLFacade bl = new BLFacadeImplementation();
	
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}

	public String comprobar() {
		
		if (email.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The passwords don't match"));
			return null;
		}
		
		if (!password.equals(rpassword)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The passwords don't match"));
			return null;
		}
		
		Driver comprobar = bl.getDriverByEmail(email);
		
		if (comprobar != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The email is already registered"));
			return null;
		}
		
		Driver d = bl.createDriver(email, name, password);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Driver successfully registered!"));
		return null;
	}
	
	public String moveToLogin() {
		return "lo";
	}
	
}
