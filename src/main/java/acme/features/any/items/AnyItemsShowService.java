package acme.features.any.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkits.Item;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;


@Service
public class AnyItemsShowService implements AbstractShowService<Any, Item> {
	
	@Autowired
	protected AnyItemsRepository repository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository systemConfigurationRepository;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;
	
	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		
		final Item item;
		int id;
		
		id = request.getModel().getInteger("id");
		item = this.repository.findOneItemById(id);

		return item.isPublished();
	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;

		Item result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneItemById(id);

		return result;
	}
	
	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		model.setAttribute("itemtId", entity.getId());

		request.unbind(entity, model, "name", "code", "technology","description","price", "link", "inventor.userAccount.username");
		model.setAttribute("exchangePrice", this.exchangeService
        	.computeMoneyExchange(entity.getPrice(), this.systemConfigurationRepository.findSystemConfiguration().getSystemCurrency()).getTarget());
	}

}
