package com.inflearn.ex0601.다대일;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			// 저장
			Team0 team = new Team0();
			team.setName("TeamA");
			em.persist(team);
			
			Member0 member = new Member0();
			member.setUsername("member1");
			member.changeTeam(team);
			em.persist(member);
			
			em.flush();
			em.clear();
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
