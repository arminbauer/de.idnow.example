package models.storage;

public class StorageElementAlreadyExistsException extends StorageGenericException {

  private static final long serialVersionUID = 12346L;

  public StorageElementAlreadyExistsException(String message) {
    super("Element already exists in storage: " + message);
  }

}
