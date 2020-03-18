package dao;

import java.util.List;

import domain.Identification;

public interface IdentificationRepository {

	Identification save(Identification identification);

	List<Identification> load();

}
