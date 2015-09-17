package controllers;

public class InvalidJsonDataException extends Exception {
	public InvalidJsonDataException (){
		super ("The Json data was invalid.");
	}
}
