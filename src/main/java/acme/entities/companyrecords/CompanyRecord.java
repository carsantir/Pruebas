
package acme.entities.companyrecords;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "stars")
})

public class CompanyRecord extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				companyName;

	@NotBlank
	private String				sector;

	@NotBlank
	private String				ceo;

	@NotBlank
	private String				description;

	@NotBlank
	@URL
	private String				website;

	@NotBlank
	@Pattern(regexp = "^([+][1-9][0-9]{0,2}[ ]){0,1}([(](0|[1-9][0-9]{0,3})[)][ ]){0,1}[0-9]{6,10}$")
	private String				number;

	@NotBlank
	@Email
	private String				email;

	@Min(0)
	@Max(5)
	private Integer				stars;


	@Transient
	public Boolean getIncorporated() {
		Boolean res = false;

		if (this.companyName.contains("Inc.")) {
			res = true;
		}

		return res;
	}
}
