package modelo.bean;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import modelo.businessLogic.BLFacade;
import modelo.businessLogic.BLFacadeImplementation;
import modelo.dominio.Ride;

@Named("myrides")
@SessionScoped
public class MyRidesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Ride> rides;
	
	@Inject
    private LoginBean lb;

	BLFacade bl = new BLFacadeImplementation();
	
	
	public List<Ride> getRides() {
		rides = bl.getRidesByEmail(lb.getEmail());
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
	public String moveToMenu() {
		return "me";
	}
	
}