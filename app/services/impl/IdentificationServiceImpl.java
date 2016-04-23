package services.impl;

import com.google.inject.Inject;
import repositories.CompanyRepository;
import repositories.IdentificationRepository;
import repositories.models.Company;
import repositories.models.Identification;
import services.IdentificationService;
import services.dto.IdentificationDTO;
import services.exceptions.InvalidCompanyException;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ebajrami on 4/23/16.
 */
public class IdentificationServiceImpl implements IdentificationService {

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private IdentificationRepository identificationRepository;

    @Override
    public void startIdentification(IdentificationDTO identificationDTO) throws InvalidCompanyException {
        checkNotNull(identificationDTO, "No identification provided");
        Company company = companyRepository.findById(identificationDTO.getCompanyId());
        if (company == null) {
            throw new InvalidCompanyException("Company with ID: " + company.getId() + " not found in the database");
        }

        checkArgument(identificationDTO.getId() > 0, "Invalid identification ID");
        checkArgument(identificationDTO.getTime() > 0, "Invalid identification time");
        checkArgument(identificationDTO.getName().length() > 0, "Invalid name of the user which sent request");
        checkArgument(identificationDTO.getWaitingTime() > 0, "Invalid waiting time");

        Identification identification = new Identification();
        identification.setId(identificationDTO.getId());
        identification.setName(identificationDTO.getName());
        identification.setTime(identificationDTO.getTime());
        identification.setWaitingTime(identificationDTO.getWaitingTime());
        identification.setCompany(company);
        identificationRepository.add(identification);
    }

    @Override
    public List<IdentificationDTO> getPendingIdentifications() {
        return identificationRepository.findAll().stream().sorted((ident1, ident2) -> {
            double slaFirst = ident1.getCompany().getSlaTime();
            double slaSecond = ident2.getCompany().getSlaTime();
            double waited1 = ident1.getWaitingTime();
            double waited2 = ident2.getWaitingTime();

            double percentOfTimePassed1 = waited1 / slaFirst;
            double percentOfTimePassed2 = waited2 / slaSecond;


            if (percentOfTimePassed1 == percentOfTimePassed2) {
                return (ident1.getCompany().getCurrentSlaPercentage() / ident1.getCompany().getSlaPercentage()) > (ident2.getCompany().getCurrentSlaPercentage() / ident2.getCompany().getSlaPercentage()) ?
                        1 : -1;
            }
            // first is more urgent
            if (percentOfTimePassed1 <= percentOfTimePassed2) {
                return 1;
            } else {
                return -1;
            }

        }).map(item -> {
            IdentificationDTO identificationDTO = new IdentificationDTO();
            identificationDTO.setCompanyId(item.getCompany().getId());
            identificationDTO.setId(item.getId());
            identificationDTO.setName(item.getName());
            identificationDTO.setTime(item.getTime());
            identificationDTO.setWaitingTime(item.getWaitingTime());
            return identificationDTO;
        }).collect(Collectors.toList());

    }
}
