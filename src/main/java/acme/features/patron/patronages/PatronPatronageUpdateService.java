package acme.features.patron.patronages;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.SpamDetector;
import acme.entities.patronages.Patronage;
import acme.entities.patronages.PatronageStatus;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Patron;

@Service
public class PatronPatronageUpdateService implements AbstractUpdateService<Patron, Patronage> {

	@Autowired
	protected PatronPatronageRepository repository;
	
	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;
		
		final boolean result;
		int patronageId;
		Patronage patronage;
		Patron patron;
		
		patronageId = request.getModel().getInteger("id");
		patronage = this.repository.findPatronageById(patronageId);
		patron = patronage.getPatron();
		
		result = patronage.isDraftMode() && request.isPrincipal(patron);
		
		return result;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "status", "code", "legalStuff", "budget", "creationMoment", "startDate", "finishDate", "link");
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		request.unbind(entity, model, "status", "draftMode", "code", "legalStuff", "budget", "creationMoment", "startDate", "finishDate", "link");
		
		model.setAttribute("inventorFullName", entity.getInventor().getUserAccount().getIdentity().getFullName());
		model.setAttribute("inventorName", entity.getInventor().getUserAccount().getIdentity().getName());
		model.setAttribute("inventorSurname", entity.getInventor().getUserAccount().getIdentity().getSurname());
		model.setAttribute("inventorEmail", entity.getInventor().getUserAccount().getIdentity().getEmail());
		
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;
		
		Patronage result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findPatronageById(id); 
		
		return result;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("legalStuff")) {
			
			final List<String> legalStuffWords = Arrays.asList(entity.getLegalStuff().split(" "));
			final List<String> strongSpamTerms = Arrays.asList(this.repository.getStrongSpamTerms().split(","));
			final List<String> weakSpamTerms = Arrays.asList(this.repository.getWeakSpamTerms().split(","));
			final Double strongThreshold = this.repository.getStrongThreshold();
			final Double weakThreshold = this.repository.getWeakThreshold();
			
			
			errors.state(request, !SpamDetector.detectSpam(legalStuffWords, weakSpamTerms, weakThreshold), "legalStuff", "patron.patronage.form.error.spam");
			errors.state(request, !SpamDetector.detectSpam(legalStuffWords, strongSpamTerms, strongThreshold), "legalStuff", "patron.patronage.form.error.spam");
		}
		
		if(!errors.hasErrors("status")) {
			errors.state(request, entity.getStatus() == PatronageStatus.PROPOSED && entity.isDraftMode(), "status", "patron.patronage.form.error.status");
		}
		
		if (!errors.hasErrors("creationMoment") && !errors.hasErrors("startDate") && !errors.hasErrors("finishDate")) {
			Calendar calendar;
			final Date minimumStartDate;
			final Date minimumFinishDate;

			calendar = new GregorianCalendar();
			
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			minimumStartDate = calendar.getTime();
			
			calendar.setTime(entity.getStartDate());
			calendar.add(Calendar.MONTH, 1);
			minimumFinishDate = calendar.getTime();
			
			errors.state(request, entity.getStartDate().after(entity.getCreationMoment()), "startDate", "patron.patronage.form.error.date-creation");
			errors.state(request, entity.getFinishDate().after(entity.getStartDate()), "finishDate", "patron.patronage.form.error.date-start");
			errors.state(request, entity.getStartDate().after(minimumStartDate), "startDate", "patron.patronage.form.error.too-close");
			errors.state(request, entity.getFinishDate().after(minimumFinishDate), "finishDate", "patron.patronage.form.error.too-close");
		}
		
		if(!errors.hasErrors("budget")) {
			final List<String> acceptedCurrencies = Arrays.asList(this.repository.getAcceptedCurrencies().split(","));
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "patron.patronage.form.error.negative");
			errors.state(request, acceptedCurrencies.contains(entity.getBudget().getCurrency()), "budget", "patron.patronage.form.error.currency");
		}
		
		if (!errors.hasErrors("code")) {
			Patronage existing;

			existing = this.repository.findPatronageByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "patron.patronage.form.error.duplicated");
		}
		
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}

}
