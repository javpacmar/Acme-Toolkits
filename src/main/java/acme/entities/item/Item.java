package acme.entities.item;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Item extends AbstractEntity {

		// Serialisation identifier ------------------------------------------------------------------
	
		private static final long 				serialVersionUID = 1L;
		
		
		// Attributes ------------------------------------------------------------------
		
		@NotBlank
		@Length(min=1,max=100)
		protected String 						name;
		
		@NotBlank
		@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
		protected String 						code;
		
		@NotBlank
		@Length(min=1,max=100)
		protected String 						technology;
		
		@NotBlank
		@Length(min=1,max=255)
		protected String 						description;
		
		@NotNull
		protected Money 						retailPrice;
		
		protected ItemType 						itemType;
		
		@URL
		protected String 						link;
		
		// Relations ---------------------------------------------------------
		
		@Valid
		@NotNull
		@ManyToOne(optional = false)
		protected Inventor inventor;
}