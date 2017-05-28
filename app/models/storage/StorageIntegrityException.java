package models.storage;

public class StorageIntegrityException extends StorageGenericException {

  private static final long serialVersionUID = 12345L;

  public StorageIntegrityException() {
    super("Integrity error in storage");
  }

}

