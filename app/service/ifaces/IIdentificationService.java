package service.ifaces;

import com.google.inject.ImplementedBy;
import service.IdentificationService;
import service.dto.IdentificationDTO;

/**
 * Created by sreenath on 15.07.16.
 */
@ImplementedBy(IdentificationService.class)
public interface IIdentificationService extends IBaseService<IdentificationDTO> {
}
