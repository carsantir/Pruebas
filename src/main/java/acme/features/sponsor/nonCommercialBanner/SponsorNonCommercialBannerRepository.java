
package acme.features.sponsor.nonCommercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.configurations.Configuration;
import acme.entities.roles.Sponsor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorNonCommercialBannerRepository extends AbstractRepository {

	@Query("select ncb from NonCommercialBanner ncb where ncb.id = ?1")
	NonCommercialBanner findNonCommercialBannerById(int id);

	@Query("select ncb from NonCommercialBanner ncb where ncb.sponsor.id = ?1")
	Collection<NonCommercialBanner> findNonCommercialBannersBySponsorId(int id);

	@Query("select s from Sponsor s where s.id=?1")
	Sponsor findSponsor(int id);

	@Query("select c from Configuration c")
	Collection<Configuration> findConfigurationParameters();

}
