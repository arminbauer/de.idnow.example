package models.services;

import com.google.inject.ImplementedBy;
import models.dtos.IdentificationDto;

import java.util.List;

@ImplementedBy(IdentificationsOrderingServiceImpl.class)
public interface IdentificationsOrderingService {
	List<IdentificationDto> getOrderedIdentifications();
}
