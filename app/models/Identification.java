package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Identification extends Model{
	@Id
	public Long id;

	@Constraints.Required
	public String name;

	public long time;
	public int waitingTime;
	public long companyId;

	public static Finder<Long, Identification> find = new Finder<>(Identification.class);
}
