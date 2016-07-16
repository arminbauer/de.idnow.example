package service.ifaces;

import com.google.inject.ImplementedBy;
import service.CompanyService;
import service.dto.CompanyDTO;

/**
 * Created by sreenath on 15.07.16.
 */
@ImplementedBy(CompanyService.class)
public interface ICompanyService extends IBaseService<CompanyDTO> {
}
