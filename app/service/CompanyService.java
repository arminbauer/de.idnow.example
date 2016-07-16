package service;

import com.avaje.ebean.Ebean;
import models.Company;
import service.dto.CompanyDTO;
import service.ifaces.ICompanyService;
import utils.ErrorCodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for querying the Companies
 *
 * Created by sreenath on 15.07.16.
 */
public class CompanyService implements ICompanyService {

    @Override
    public CompanyDTO getById(String id) {
        Company c = Ebean.find(Company.class, id);
        if (c == null) {
            throw new RuntimeException(ErrorCodes.COMPANY_ITEM_NOT_FOUND);
        }
        return getDto(c);
    }

    @Override
    public List getAll() {
        List<Company> companies = Ebean.find(Company.class).findList();
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        for (Company c : companies) {
            companyDTOs.add(getDto(c));
        }
        return companyDTOs;
    }

    @Override
    public void create(CompanyDTO obj) {
        Ebean.save(getModel(obj));
    }

    @Override
    public CompanyDTO update(CompanyDTO obj) {
        return null;
    }

    @Override
    public CompanyDTO delete(CompanyDTO obj) {
        return null;
    }

    /**
     * Utility function for translating models to dtos
     *
     * @param obj
     * @return
     */
    private CompanyDTO getDto(Company obj) {
        CompanyDTO c = new CompanyDTO();
        c.setName(obj.getName());
        c.setId(obj.getId());
        c.setCurrentSlaPercent(obj.getCurrentSlaPercent());
        c.setSlaPercent(obj.getCurrentSlaPercent());
        c.setSlaTimeSeconds(obj.getSlaTimeSeconds());
        return c;
    }

    /**
     * Utility function for translating dtos to models
     *
     * @param dto
     * @return
     */
    private Company getModel(CompanyDTO dto) {
        Company c = new Company();
        c.setName(dto.getName());
        c.setCurrentSlaPercent(dto.getCurrentSlaPercent());
        c.setSlaPercent(dto.getSlaPercent());
        c.setSlaTimeSeconds(dto.getSlaTimeSeconds());
        c.setId(dto.getId());
        return c;
    }
}
