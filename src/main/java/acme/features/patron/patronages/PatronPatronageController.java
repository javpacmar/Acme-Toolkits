package acme.features.patron.patronages;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.patronages.Patronage;
import acme.framework.controllers.AbstractController;
import acme.roles.Patron;

@Controller
public class PatronPatronageController extends AbstractController<Patron, Patronage>{
	
	@Autowired
	protected PatronPatronageListService listService;
	
	@Autowired
	protected PatronPatronageShowService showService;
	
	@Autowired
	protected PatronPatronageUpdateService updateService;
	
	@Autowired
	protected PatronPatronageDeleteService deleteService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
	}

}
