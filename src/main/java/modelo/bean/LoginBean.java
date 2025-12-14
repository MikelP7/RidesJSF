package modelo.bean;

import java.io.Serializable;


import modelo.businessLogic.*;
import modelo.dominio.Driver;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	
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
	
	public String comprobar() {
		
		if(email.equals("") || password.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The fields cannot be empty"));
			return null;
		}
		
		if(!email.contains("@") || !email.contains(".")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The email is invalid"));
			return null;
		}
		
		Driver d = bl.getDriverByEmail(email);
		
		if (d != null) {
			if(d.getPassword().equals(password)) {
				return "me";
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The password doesn't match"));
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: The email is not registered"));
		return null;
	}
	
	public String moveToRegister() {
		return "re";
	}
	
}
