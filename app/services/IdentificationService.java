package services;

import com.google.inject.ImplementedBy;
import models.IdentificationDto;
import services.impl.IdentificationServiceImpl;

import java.util.List;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
@ImplementedBy(IdentificationServiceImpl.class)
public interface IdentificationService {
    public void add(IdentificationDto identification);

    public List<IdentificationDto> getAll();
}
