package models.storage;

public class StorageCompanyNotFoundException extends StorageGenericException {

  private static final long serialVersionUID = 12347L;

  public StorageCompanyNotFoundException(String message) {
    super("No such company id in storage: " + message);
  }

}
