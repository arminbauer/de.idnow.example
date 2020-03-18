package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class Identification extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String name;

	@Constraints.Required
	public Date time;

	@Constraints.Min(0)
	public int waitingTime;

	@ManyToOne
	public Company company;

}