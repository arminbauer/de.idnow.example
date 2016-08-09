package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Company extends Model {
	@Id
	public Long id;

	@Constraints.Required
	public String name;

	@Constraints.Min(1)
	public int slaTime;

	@Constraints.Min(0)
	@Constraints.Max(1)
	public float slaPercentage;

	@Constraints.Min(0)
	@Constraints.Max(1)
	public float currentSlaPercentage;

	public static Finder<Long, Company> find = new Finder<>(Company.class);
}
