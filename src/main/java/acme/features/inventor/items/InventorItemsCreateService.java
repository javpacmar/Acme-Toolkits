
package acme.features.inventor.items;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.SpamDetector;
import acme.entities.toolkits.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorItemsCreateService implements AbstractCreateService<Inventor, Item> {

	@Autowired
	protected InventorItemsRepository repository;


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "name", "code", "technology", "description", "price", "type", "link");
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "name", "code", "technology", "description", "price", "type", "link");
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		assert request != null;

		final Item result;
		final Inventor inventor;

		inventor = this.repository.findOneInventorById(request.getPrincipal().getActiveRoleId());
		result = new Item();
		result.setCode("");
		result.setDescription("");
		result.setInventor(inventor);
		result.setName("");
		result.setPublished(false);
		result.setTechnology("");

		return result;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("technology")) {
			
			final List<String> technologyWords = Arrays.asList(entity.getTechnology().split(" "));
			final List<String> strongSpamTerms = Arrays.asList(this.repository.getStrongSpamTerms().split(","));
			final List<String> weakSpamTerms = Arrays.asList(this.repository.getWeakSpamTerms().split(","));
			final Double strongThreshold = this.repository.getStrongThreshold();
			final Double weakThreshold = this.repository.getWeakThreshold();
			
			
			errors.state(request, !SpamDetector.detectSpam(technologyWords, weakSpamTerms, weakThreshold), "technology", "inventor.items.form.error.spam");
			errors.state(request, !SpamDetector.detectSpam(technologyWords, strongSpamTerms, strongThreshold), "technology", "inventor.items.form.error.spam");
		}
		
		if(!errors.hasErrors("description")) {
			
			final List<String> descriptionWords = Arrays.asList(entity.getDescription().split(" "));
			final List<String> strongSpamTerms = Arrays.asList(this.repository.getStrongSpamTerms().split(","));
			final List<String> weakSpamTerms = Arrays.asList(this.repository.getWeakSpamTerms().split(","));
			final Double strongThreshold = this.repository.getStrongThreshold();
			final Double weakThreshold = this.repository.getWeakThreshold();
			
			
			errors.state(request, !SpamDetector.detectSpam(descriptionWords, weakSpamTerms, weakThreshold), "description", "inventor.items.form.error.spam");
			errors.state(request, !SpamDetector.detectSpam(descriptionWords, strongSpamTerms, strongThreshold), "description", "inventor.items.form.error.spam");
		}

		if (!errors.hasErrors("price")) {
			final List<String> acceptedCurrencies = Arrays.asList(this.repository.getAcceptedCurrencies().split(","));
			errors.state(request, entity.getPrice().getAmount() > 0, "price", "inventor.item.form.error.negative");
			errors.state(request, acceptedCurrencies.contains(entity.getPrice().getCurrency()), "price", "inventor.item.form.error.currency");
		}

		if (!errors.hasErrors("code")) {
			Item existing;

			existing = this.repository.findOneComponentByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "inventor.item.form.error.code.duplicated");
		}

	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
