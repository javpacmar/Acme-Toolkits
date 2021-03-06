package acme.features.inventor.toolkits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.datatypes.Money;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorToolkitRepository extends AbstractRepository{
		
	@Query("SELECT t FROM Toolkit t WHERE t.inventor.id = :id")
	Collection<Toolkit> findToolkitsByInventorId(int id);
	
	@Query("SELECT t FROM Toolkit t WHERE t.id = :id")
	Toolkit findToolkitById(int id);
	
	@Query("SELECT q.item.price FROM Quantity q WHERE q.toolkit.id = :id")
	Collection<Money> collectPrices(int id);
	
	@Query("select c.systemCurrency from SystemConfiguration c")
	String systemCurrency();

	@Query("SELECT i FROM Inventor i WHERE i.id = :id")
	Inventor findInventorById(int id);

	@Query("SELECT t FROM Toolkit t WHERE t.code = :code")
	Toolkit findToolkitByCode(String code);

	@Query("SELECT acceptedCurrencies FROM SystemConfiguration")
	String getAcceptedCurrencies();
	
	@Query("SELECT strongSpamTerms FROM SystemConfiguration")
	String getStrongSpamTerms();
	
	@Query("SELECT weakSpamTerms FROM SystemConfiguration")
	String getWeakSpamTerms();
	
	@Query("SELECT strongThreshold FROM SystemConfiguration")
	Double getStrongThreshold();
	
	@Query("SELECT weakThreshold FROM SystemConfiguration")
	Double getWeakThreshold();

	@Query("SELECT q FROM Quantity q WHERE q.toolkit.id = :id")
	Collection<Quantity> getQuantitiesByToolkit(int id);

}
