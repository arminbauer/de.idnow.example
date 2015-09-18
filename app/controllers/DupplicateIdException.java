package controllers;

public class DupplicateIdException extends Exception {
	public DupplicateIdException () {
		super("All ids need to be unique");
	}
}
