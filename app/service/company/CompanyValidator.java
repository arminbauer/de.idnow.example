package service.company;

import api.Company;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple validator for company api model
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyValidator {
    public List<String> validate(Company company) {
        List<String> errors = new ArrayList<>();

        if (company.getId() == null) {
            errors.add("Company id is required!");
        }
        if (StringUtils.isEmpty(company.getName())) {
            errors.add("Company name is required!");
        }
        if (company.getSlaTime() == null) {
            errors.add("SLA time is required!");
        }
        if (company.getSlaPercentage() == null) {
            errors.add("SLA percentage is required!");
        }
        if (company.getCurrentSlaPercentage() == null) {
            errors.add("Current SLA percentage is required!");
        }
        return errors;
    }
}
