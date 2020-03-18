package service;

import java.util.List;

import dto.IdentificationDto;

public interface IdentificationService {

	void save(IdentificationDto identification);

	List<IdentificationDto> loadOrdered();

}
