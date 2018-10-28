package service.impl;

import model.Identification;

import java.math.BigDecimal;

class InternalIdentification extends Identification{

    private static final int CRITICAL_TIME_TO_WAIT = 5;

    private final int timeToFail;
    private final boolean alreadyFailed;
    private final boolean inCriticalZone;

    private final boolean overPerformingCompany;
    private final BigDecimal companyPerformanceRatio;

    InternalIdentification(Identification identification, InternalCompany company) {
        this.setId(identification.getId());
        this.setName(identification.getName());
        this.setTime(identification.getTime());
        this.setWaitingTime(identification.getWaitingTime());
        this.setCompanyId(identification.getCompanyId());

        this.timeToFail = company.getSlaTime() - getWaitingTime();
        this.alreadyFailed = timeToFail < 0;
        this.inCriticalZone = !alreadyFailed && timeToFail < CRITICAL_TIME_TO_WAIT;

        this.overPerformingCompany = company.isOverPerforming();
        this.companyPerformanceRatio = company.getPerformanceRatio();
    }

    int getTimeToFail() {
        return timeToFail;
    }

    boolean isAlreadyFailed() {
        return alreadyFailed;
    }

    boolean isInCriticalZone() {
        return inCriticalZone;
    }

    boolean isOverPerformingCompany() {
        return overPerformingCompany;
    }

    BigDecimal getCompanyPerformanceRatio() {
        return companyPerformanceRatio;
    }
}
