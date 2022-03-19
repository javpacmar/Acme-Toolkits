package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

		// Serialisation identifier -----------------------------------------------
	
	
		private static final long 		serialVersionUID = 1L;
		
		
		// Attributes ------------------------------------------------------------------
		
		@NotNull
		protected String 				strongSpamWords;
		
		@NotNull
		protected String 				weakSpamWords;
		
		@NotBlank
		protected String 				acceptedCurrencies;
		
		@NotBlank
		protected String 				defaultCurrency;
		
		@Range(min=0,max=100)
		protected double 				weakThreshold;
		
		@Range(min=0,max=100)
		protected double 				strongThreshold;
}