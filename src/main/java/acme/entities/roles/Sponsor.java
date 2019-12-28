
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends UserRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				organisationName;

	private String				creditCard;

	@Min(100)
	@Max(999)
	private Integer				cvv;

	private String				expirationDate;

}
