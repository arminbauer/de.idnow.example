import models.Company;
import models.Identification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

/**
 * Created by nick on 14.05.16.
 */
public class RandomObjects {

    public static Company randomCompany() {
        Company company = new Company();
        company.setId(RandomUtils.nextLong(0, Long.MAX_VALUE));
        company.setName(RandomStringUtils.randomAlphabetic(12));
        company.setSlaTimeSeconds(RandomUtils.nextInt(50, 200));
        company.setSlaPercentage(RandomUtils.nextFloat(0.80F, 0.95F));
        company.setCurrentSlaPercentage(RandomUtils.nextFloat(0.80F, 0.95F));
        return company;
    }

    public static Identification randomIdentification() {
        Identification identification = new Identification();
        identification.setId(RandomUtils.nextLong(100, Long.MAX_VALUE));
        identification.setName(RandomStringUtils.randomAlphanumeric(12));
        identification.setCompanyId(RandomUtils.nextLong(0, Long.MAX_VALUE));
        identification.setWaitTimeSeconds(RandomUtils.nextInt(0, 300));
        identification.setCreatedTs(new Date().getTime());
        return identification;
    }
}


