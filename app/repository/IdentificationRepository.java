package repository;

import model.Identification;
import play.db.jpa.JPA;

import java.util.List;

public class IdentificationRepository {

	public List<Identification> findAllSorted() {
		return JPA.em().createQuery(
				"select i " +
				"from Identification i " +
				"order by i.company.slaTime asc, i.waitingTime desc, i.company.currentSlaPercentage asc",
				Identification.class).getResultList();
	}
}
