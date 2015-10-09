package services;

import java.util.List;

import models.IdentificationDTO;
import services.impl.IdentificationServiceImpl;

import com.google.inject.ImplementedBy;
@ImplementedBy(IdentificationServiceImpl.class)
public interface IdentificationService {
	public void add(IdentificationDTO identification);
	public List<IdentificationDTO> getAll();
}
