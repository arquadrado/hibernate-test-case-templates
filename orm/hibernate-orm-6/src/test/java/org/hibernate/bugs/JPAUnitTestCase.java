package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.TypedQuery;
import org.hibernate.bugs.entities.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		final String queryStmt = "FROM Profile$Active p LEFT JOIN FETCH p.commonDisplayNameAttribute dna WHERE p.customer.id = :customerId order by LOWER(COALESCE(dna.value, p.name)) asc";

		final TypedQuery<Profile> query = entityManager.createQuery(queryStmt, Profile.class);
		query.setParameter("customerId", "1234567890");
		query.setFirstResult(0);
		query.setMaxResults(20);
//
//        final EntityGraph<?> fullGraph = em.getEntityGraph("CBaseDataProfile.full");
//        query.setHint("jakarta.persistence.fetchgraph", fullGraph); // jakarta???

		final List<Profile> result = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
