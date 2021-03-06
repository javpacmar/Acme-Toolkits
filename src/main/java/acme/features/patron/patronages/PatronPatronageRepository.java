package acme.features.patron.patronages;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronages.Patronage;
import acme.entities.patronages.PatronageReport;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;
import acme.roles.Patron;

@Repository
public interface PatronPatronageRepository extends AbstractRepository {
	
	@Query("SELECT p FROM Patronage p WHERE p.id = :id")
	Patronage findPatronageById(int id);
	
	@Query("SELECT p FROM Patronage p WHERE p.code = :code")
	Patronage findPatronageByCode(String code);
	
	@Query("SELECT p FROM Patronage p WHERE p.patron.id = :patronId")
	Collection<Patronage> findPatronages(Integer patronId);
	
	@Query("SELECT pr FROM PatronageReport pr WHERE pr.patronage.id = :id")
	Collection<PatronageReport> findPatronageReportsByPatronageId(int id);
	
	@Query("SELECT p FROM Patron p WHERE p.id = :id")
	Patron findOnePatronById(int id);
	
	@Query("SELECT i FROM Inventor i")
	Collection<Inventor> getAllInventors();
	
	@Query("SELECT i FROM Inventor i WHERE i.id = :id")
	Inventor findInventorById(int id);
	
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

}
