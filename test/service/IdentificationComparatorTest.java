package service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domain.Company;
import domain.Identification;
import service.IdentificationComparator;

public class IdentificationComparatorTest {
	private IdentificationComparator comparator = new IdentificationComparator();

	@Test
	public void testExample1() {
		final Company company = generateCompany(1, 60, 0.9f, 0.95f);

		final Identification id1 = generateId(1, 30, company);
		final Identification id2 = generateId(2, 45, company);

		assertEquals(-1, comparator.compare(id1, id2));
	}

	@Test
	public void testExample2() {
		final Company company1 = generateCompany(1, 60, 0.9f, 0.95f);
		final Company company2 = generateCompany(2, 60, 0.9f, 0.90f);

		final Identification id1 = generateId(1, 30, company1);
		final Identification id2 = generateId(2, 30, company2);

		assertEquals(-1, comparator.compare(id1, id2));
	}

	@Test
	public void testExample3() {
		final Company company1 = generateCompany(1, 60, 0.9f, 0.95f);
		final Company company2 = generateCompany(2, 120, 0.8f, 0.95f);

		final Identification id1 = generateId(1, 30, company1);
		final Identification id2 = generateId(2, 30, company2);

		assertEquals(1, comparator.compare(id1, id2));
	}

	@Test
	public void testExample4() {
		final Company company1 = generateCompany(1, 60, 0.9f, 0.95f);
		final Company company2 = generateCompany(2, 120, 0.8f, 0.8f);

		final Identification id1 = generateId(1, 45, company1);
		final Identification id2 = generateId(2, 30, company2);

		assertEquals(-1, comparator.compare(id1, id2));
	}

	@Test
	public void testExample5() {
		final Company company1 = generateCompany(1, 60, 0.9f, 0.95f);
		final Company company2 = generateCompany(2, 120, 0.8f, 0.85f);

		final Identification id1 = generateId(1, 45, company1);
		final Identification id2 = generateId(2, 30, company2);

		assertEquals(1, comparator.compare(id1, id2));
	}

	@Test
	public void testExample6() {
		final Company company1 = generateCompany(1, 60, 0.9f, 0.95f);
		final Company company2 = generateCompany(2, 120, 0.8f, 0.85f);

		final Identification id1 = generateId(1, 65, company1);
		final Identification id2 = generateId(2, 30, company2);

		assertEquals(-1, comparator.compare(id1, id2));
	}

	@Test
	public void testExample7() {
		final Company company1 = generateCompany(1, 60, 0.9f, 0.95f);
		final Company company2 = generateCompany(2, 60, 0.8f, 0.75f);
		
		final Identification id1 = generateId(1, 30, company1);
		final Identification id2 = generateId(2, 30, company2);
		
		assertEquals(-1, comparator.compare(id1, id2));
	}

	private Identification generateId(long id, int waitingTime, Company company) {
		final Identification identification = new Identification();
		identification.id = id;
		identification.company = company;
		identification.waitingTime = waitingTime;

		return identification;
	}

	private Company generateCompany(long id, int time, float sla, float currentSla) {
		final Company company = new Company();
		company.id = id;
		company.slaTime = time;
		company.slaPercentage = sla;
		company.currentSlaPercentage = currentSla;

		return company;
	}

}
