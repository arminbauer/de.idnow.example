package controllers.api;

public interface IdNowAPI<T> {
    T startIdentification();
    T addCompany();
    T identifications();
}
