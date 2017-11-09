import models.Company;
import models.CompanyStore;
import models.Identification;
import models.IdentificationStore;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Florian Schmidt on 08.11.2017.
 */
public class PriorityTest {
    @Test
    public void example1() throws Exception {
        CompanyStore.clear();
        IdentificationStore.clear();

        Company company1 = new Company();
        company1.setId(UUID.randomUUID());
        company1.setName("Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);
        CompanyStore.add(company1);

        Identification identification1 = new Identification();
        identification1.setId(UUID.randomUUID());
        identification1.setName("User1");
        identification1.setTime(LocalDateTime.now());
        identification1.setWaitingTime(30);
        identification1.setCompanyId(company1.getId());
        IdentificationStore.add(identification1);

        Identification identification2 = new Identification();
        identification2.setId(UUID.randomUUID());
        identification2.setName("User2");
        identification2.setTime(LocalDateTime.now());
        identification2.setWaitingTime(45);
        identification2.setCompanyId(company1.getId());
        IdentificationStore.add(identification2);

        Optional<Identification> optIdentification = IdentificationStore.getPrioritizedIdentifications()
                                                                        .stream().findFirst();

        if(optIdentification.isPresent()) {
            assertTrue(optIdentification.get().equals(identification2));
        } else {
            throw new Exception("No prioritized identification found.");
        }
    }

    @Test
    public void example2() throws Exception {
        CompanyStore.clear();
        IdentificationStore.clear();

        Company company1 = new Company();
        company1.setId(UUID.randomUUID());
        company1.setName("Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);
        CompanyStore.add(company1);

        Company company2 = new Company();
        company2.setId(UUID.randomUUID());
        company2.setName("Company2");
        company2.setSlaTime(60);
        company2.setSlaPercentage(0.9f);
        company2.setCurrentSlaPercentage(0.9f);
        CompanyStore.add(company2);
                
        Identification identification1 = new Identification();
        identification1.setId(UUID.randomUUID());
        identification1.setName("User1");
        identification1.setTime(LocalDateTime.now());
        identification1.setWaitingTime(30);
        identification1.setCompanyId(company1.getId());
        IdentificationStore.add(identification1);
        
        Identification identification2 = new Identification();
        identification2.setId(UUID.randomUUID());
        identification2.setName("USer2");
        identification2.setTime(LocalDateTime.now());
        identification2.setWaitingTime(30);
        identification2.setCompanyId(company2.getId());
        IdentificationStore.add(identification2);

        Optional<Identification> optIdentification = IdentificationStore.getPrioritizedIdentifications()
                                                                        .stream().findFirst();

        if(optIdentification.isPresent()) {
            assertTrue(optIdentification.get().equals(identification2));
        } else {
            throw new Exception("No prioritized identification found.");
        }
    }

    @Test
    public void example3() throws Exception {
        CompanyStore.clear();
        IdentificationStore.clear();

        Company company1 = new Company();
        company1.setId(UUID.randomUUID());
        company1.setName("Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);
        CompanyStore.add(company1);

        Company company2 = new Company();
        company2.setId(UUID.randomUUID());
        company2.setName("Company2");
        company2.setSlaTime(120);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.95f);
        CompanyStore.add(company2);

        Identification identification1 = new Identification();
        identification1.setId(UUID.randomUUID());
        identification1.setName("User1");
        identification1.setTime(LocalDateTime.now());
        identification1.setWaitingTime(30);
        identification1.setCompanyId(company1.getId());
        IdentificationStore.add(identification1);

        Identification identification2 = new Identification();
        identification2.setId(UUID.randomUUID());
        identification2.setName("User2");
        identification2.setTime(LocalDateTime.now());
        identification2.setWaitingTime(30);
        identification2.setCompanyId(company2.getId());
        IdentificationStore.add(identification2);

        Optional<Identification> optIdentification = IdentificationStore.getPrioritizedIdentifications()
                                                                        .stream().findFirst();

        if(optIdentification.isPresent()) {
            assertTrue(optIdentification.get().equals(identification1));
        } else {
            throw new Exception("No prioritized identification found.");
        }
    }

    @Test
    public void example4() throws Exception {
        CompanyStore.clear();
        IdentificationStore.clear();

        Company company1 = new Company();
        company1.setId(UUID.randomUUID());
        company1.setName("Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);
        CompanyStore.add(company1);

        Company company2 = new Company();
        company2.setId(UUID.randomUUID());
        company2.setName("Company2");
        company2.setSlaTime(120);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.8f);
        CompanyStore.add(company2);
                
        Identification identification1 = new Identification();
        identification1.setId(UUID.randomUUID());
        identification1.setName("User1");
        identification1.setTime(LocalDateTime.now());
        identification1.setWaitingTime(45);
        identification1.setCompanyId(company1.getId());
        IdentificationStore.add(identification1);
        
        Identification identification2 = new Identification();
        identification2.setId(UUID.randomUUID());
        identification2.setName("User2");
        identification2.setTime(LocalDateTime.now());
        identification2.setWaitingTime(30);
        identification2.setCompanyId(company2.getId());
        IdentificationStore.add(identification2);

        Optional<Identification> optIdentification = IdentificationStore.getPrioritizedIdentifications()
                                                                        .stream().findFirst();

        if(optIdentification.isPresent()) {
            assertTrue(optIdentification.get().equals(identification1));
        } else {
            throw new Exception("No prioritized identification found.");
        }
    }
}
