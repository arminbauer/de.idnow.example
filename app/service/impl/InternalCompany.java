package service.impl;

import model.Company;

import java.math.BigDecimal;
import java.math.RoundingMode;

class InternalCompany extends Company {

    private final BigDecimal performanceRatio;
    private final boolean overPerforming;

    InternalCompany(Company company) {
        this.setId(company.getId());
        this.setName(company.getName());
        this.setSlaTime(company.getSlaTime());
        this.setSlaPercentage(company.getSlaPercentage());
        this.setCurrentSlaPercentage(company.getCurrentSlaPercentage());

        this.performanceRatio = new BigDecimal(getCurrentSlaPercentage()).divide(new BigDecimal(getSlaPercentage()), 2, RoundingMode.HALF_DOWN);
        this.overPerforming = performanceRatio.compareTo(BigDecimal.ONE) > 0;
    }

    boolean isOverPerforming() {
        return overPerforming;
    }

    BigDecimal getPerformanceRatio() {
        return performanceRatio;
    }
}
