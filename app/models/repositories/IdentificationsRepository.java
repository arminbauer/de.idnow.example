package models.repositories;

import com.google.inject.ImplementedBy;
import models.dtos.IdentificationDto;

import java.util.List;

@ImplementedBy(IdentificationsRepositoryImpl.class)
public interface IdentificationsRepository {
	List<IdentificationDto> findAll();
	void add(IdentificationDto dto);
}
