package controllers;


/*
 * This is an immutable version. In real life it is possible that existing company information is updated. 
 * This scenario is not handled in this version. Any update to company information will be silently discarded! 
 * This can be handled in general by either making the class mutable or updating the identification refences that
 * point to immutable objects. In both cases additional synchronisation is needed for both read and write 
 * operations!
 * 
 * It is also assumed that Id and SLA_Time are positive integers always less than Integer.MAX_VALUE.
 */
final class Company {
	
	private final String name;
	private final int id;
	private final int slaTime;
	private final double slaPercentage;
	private final double currentSlaPercentage;
	
	Company(String name, int id, int slaTime, double slaPercentage,
			double currentSlaPercentage) {		
		this.name = name;
		this.id = id;
		this.slaTime = slaTime;
		this.slaPercentage = slaPercentage;
		this.currentSlaPercentage = currentSlaPercentage;
	}
	
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public int getSlaTime() {
		return slaTime;
	}
	public double getSlaPercentage() {
		return slaPercentage;
	}
	public double getCurrentSlaPercentage() {
		return currentSlaPercentage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(currentSlaPercentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(slaPercentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + slaTime;
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
		Company other = (Company) obj;
		if (Double.doubleToLongBits(currentSlaPercentage) != Double
				.doubleToLongBits(other.currentSlaPercentage))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(slaPercentage) != Double
				.doubleToLongBits(other.slaPercentage))
			return false;
		if (slaTime != other.slaTime)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" Company: ").append("name=").append(name).append(" ,id=").append(id);
		sb.append(" ,slaTime=").append(slaTime);
		sb.append(" ,slaPerct=").append(slaPercentage);
		sb.append(" ,slaCurrPerct=").append(currentSlaPercentage);
		return sb.toString();
	}
	
	
}