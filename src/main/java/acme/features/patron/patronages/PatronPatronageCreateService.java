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
import acme.framework.controllers.HttpMethod;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.roles.Patron;

@Service
public class PatronPatronageCreateService implements AbstractCreateService<Patron, Patronage> {

	@Autowired
	protected PatronPatronageRepository repository;
	
	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "status", "code", "legalStuff", "budget", "creationMoment", "startDate", "finishDate", "link");
		final int inventorId = Integer.parseInt((String) request.getModel().getAttribute("inventor"));
		final Inventor inventor = this.repository.findInventorById(inventorId);
		entity.setInventor(inventor);
		
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		request.unbind(entity, model, "status", "draftMode", "code", "legalStuff", "budget", "creationMoment", "startDate", "finishDate", "link", "inventor");
		
		model.setAttribute("inventors", this.repository.getAllInventors());
		
	}

	@Override
	public Patronage instantiate(final Request<Patronage> request) {
		assert request != null;
		
		final Patronage result;
		final Patron patron;
		
		patron = this.repository.findOnePatronById(request.getPrincipal().getActiveRoleId());
		
		result = new Patronage();
		result.setPatron(patron);
		result.setDraftMode(true);
		result.setStatus(PatronageStatus.PROPOSED);
		result.setCreationMoment(new Date());
		
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
			errors.state(request, entity.getStatus() == PatronageStatus.PROPOSED && entity.isDraftMode() , "status", "patron.patronage.form.error.status");
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
	public void create(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}
	
	@Override
	public void onSuccess(final Request<Patronage> request, final Response<Patronage> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
