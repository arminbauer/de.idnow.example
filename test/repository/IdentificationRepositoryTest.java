package repository;

import static com.google.common.truth.Truth.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import model.Company;
import model.CompanyBuilder;
import model.Identification;
import model.IdentificationBuilder;
import org.junit.Test;
import play.db.jpa.JPA;

import java.util.List;

public class IdentificationRepositoryTest {

	private static final int PORT = 3333;

	final IdentificationRepository identificationRepository = new IdentificationRepository();

	@Test
	public void testFindOneIdent() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company = new CompanyBuilder().build();
				company.save();
				Identification identification = new IdentificationBuilder()
						.withCompany(company)
						.build();
				identification.save();

				assertThat(identificationRepository.findAllSorted().size()).isEqualTo(1);
			});
		});
	}

	@Test
	public void testFindZeroIdents() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> assertThat(identificationRepository.findAllSorted().size()).isEqualTo(0));
		});
	}

	@Test
	public void testSortByCurrentSlaPercentage() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company1 = new CompanyBuilder(1L).withCurrentSlaPercentage(0.95).build();
				company1.save();
				Company company2 = new CompanyBuilder(2L).withCurrentSlaPercentage(0.93).build();
				company2.save();
				Identification identification1 = new IdentificationBuilder(1L).withCompanyId(1L).withCompany(company1).build();
				identification1.save();
				Identification identification2 = new IdentificationBuilder(2L).withCompanyId(2L).withCompany(company2).build();
				identification2.save();

				List<Identification> identifications = identificationRepository.findAllSorted();
				assertThat(identification2).isEqualTo(identifications.stream().findFirst().get());
			});
		});
	}

	@Test
	public void testSortByWaitingTime() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company1 = new CompanyBuilder(1L).withCurrentSlaPercentage(0.95).build();
				company1.save();
				Company company2 = new CompanyBuilder(2L).withCurrentSlaPercentage(0.93).build();
				company2.save();
				Identification identification1 = new IdentificationBuilder(1L)
						.withCompanyId(1L)
						.withCompany(company1)
						.withWaitingTime(40)
						.build();
				identification1.save();
				Identification identification2 = new IdentificationBuilder(2L)
						.withCompanyId(2L)
						.withCompany(company2)
						.withWaitingTime(20)
						.build();
				identification2.save();

				List<Identification> identifications = identificationRepository.findAllSorted();
				assertThat(identification1).isEqualTo(identifications.stream().findFirst().get());
			});
		});
	}

	@Test
	public void testSortBySlaTime() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company1 = new CompanyBuilder(1L)
						.withSlaTime(90)
						.build();
				company1.save();
				Company company2 = new CompanyBuilder(2L)
						.withSlaTime(80)
						.build();
				company2.save();
				Identification identification1 = new IdentificationBuilder(1L)
						.withCompanyId(1L)
						.withCompany(company1)
						.withWaitingTime(40)
						.build();
				identification1.save();
				Identification identification2 = new IdentificationBuilder(2L)
						.withCompanyId(2L)
						.withCompany(company2)
						.withWaitingTime(20)
						.build();
				identification2.save();

				List<Identification> identifications = identificationRepository.findAllSorted();
				assertThat(identification2).isEqualTo(identifications.stream().findFirst().get());
			});
		});
	}

	@Test
	public void testSort() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company1 = new CompanyBuilder(1L)
						.withSlaTime(60)
						.withSlaPercentage(0.9)
						.withCurrentSlaPercentage(0.95)
						.build();
				company1.save();
				Company company2 = new CompanyBuilder(2L)
						.withSlaTime(70)
						.withSlaPercentage(0.9)
						.withCurrentSlaPercentage(.94)
						.build();
				company2.save();
				Company company3 = new CompanyBuilder(3L)
						.withSlaTime(70)
						.withSlaPercentage(0.9)
						.withCurrentSlaPercentage(.93)
						.build();
				company3.save();

				new IdentificationBuilder(1L)
						.withName("Peter Huber")
						.withCompanyId(1L)
						.withCompany(company1)
						.withWaitingTime(10)
						.build()
						.save();
				new IdentificationBuilder(2L)
						.withName("Mark Huber")
						.withCompanyId(1L)
						.withCompany(company1)
						.withWaitingTime(30)
						.build()
						.save();
				new IdentificationBuilder(3L)
						.withName("Andre Huber")
						.withCompanyId(2L)
						.withCompany(company2)
						.withWaitingTime(30)
						.build()
						.save();
				new IdentificationBuilder(4L)
						.withName("Florian Huber")
						.withCompanyId(3L)
						.withCompany(company3)
						.withWaitingTime(30)
						.build()
						.save();
				new IdentificationBuilder(5L)
						.withName("Daniel Huber")
						.withCompanyId(2L)
						.withCompany(company2)
						.withWaitingTime(10)
						.build()
						.save();

				List<Identification> identifications = identificationRepository.findAllSorted();
				assertThat(identifications.get(0).getId()).isEqualTo(2L);
				assertThat(identifications.get(1).getId()).isEqualTo(1L);
				assertThat(identifications.get(2).getId()).isEqualTo(4L);
				assertThat(identifications.get(3).getId()).isEqualTo(3L);
				assertThat(identifications.get(4).getId()).isEqualTo(5L);
			});
		});

	}
}