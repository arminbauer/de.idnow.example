package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class Company extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String name;

	@Constraints.Min(0)
	public int slaTime;

	@Constraints.Min(0)
	public float slaPercentage;

	@Constraints.Min(0)
	public float currentSlaPercentage;

}