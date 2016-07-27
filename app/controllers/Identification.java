package controllers;

final class Identification{
	
	private final int id;
	private final String name;
	private final int waitingTime;
	private final Company company;
	
	Identification(int id, String name, int waitingTime, Company company) {
		this.id = id;
		this.name = name;
		this.waitingTime = waitingTime;
		this.company = company;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getWaitingTime() {
		return waitingTime;
	}
	public Company getCompany() {
		return company;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + waitingTime;
		
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identification other = (Identification) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (waitingTime != other.waitingTime)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Identification: ").append("name=").append(name).append(" ,id=").append(id);
		sb.append(" ,waitingTime=").append(waitingTime);
		sb.append(company);
		return sb.toString();
	}
	
	
}