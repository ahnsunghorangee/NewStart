package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // 애플리케이션 로딩 시점에 1개만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // "hello": persistence.xml의 persistence-unit name

        // DB 트렌잭션 단위(DB connection 얻고 Query 날리기) 할 때는 매번 만들어서 해야됨, em: 쉽게 connection 하나 받은 것으로 생각
        EntityManager em = emf.createEntityManager();
        // 스레드 간의 공유 절대 안됨.

        EntityTransaction tx = em.getTransaction(); // JPA에서 DB 변경 작업은 모두 트렌잭션 안에서 해야한다.
        tx.begin();

        try{
            // 비영속
            /*
            Member member = new Member();
            member.setId(101L);
            member.setName("Hello101");

            //영속
            System.out.println("=== Before ===");
            em.persist(member);
            // em.detach(member); // 준영속
            // em.remove(member); // 삭제
            System.out.println("=== After ===");

            Member findMember = em.find(Member.class, 101L);
            // 조회때 select 쿼리가 나가지 않는 이유는 1차 캐시에서 불러오기 때문
            System.out.println("findMember.id = "+ findMember.getId());
            System.out.println("findMember.name = "+ findMember.getName());
            */

            /*
            Member findMember = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            // select 쿼리가 한 번만 나간다. findMember 쿼리 나가고 1차캐시에 저장 후 findMember2는 1차 캐시에서 불러온다.
            */

            // 영속 엔티티의 동일성 보장
            /*
            Member findMember = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("result = " + (findMember == findMember2)); // true

             */

            // 엔티티 등록할 때, 트랜잭션을 지원하는 쓰기 지연
            /*
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("==========================");

             */

            // 엔티티 수정, 변경 감지(Dirty Checking)
            /*
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ"); // 값만 바꾸면 update 쿼리 필요없이 JPA가 변경을 감지하고 update 쿼리를 날린다. 언제? tx.commit() 시점에

            System.out.println("==========================");

             */

            // 플러시
            /*
            Member member = new Member(200L,"member200");
            em.persist(member); // 영속성 컨텍스트에 담기고

            em.flush(); // 쿼리가 DB에 바로 반영(1차 캐시 지워지지 않고 쓰기 지연 SQL 저장소만 반영)
            System.out.println("==========================");

             */

            // 준영속 상태
            /*
            Member member = em.find(Member.class, 150L); // 영속 상태
            member.setName("AAAAA");

            em.detach(member); // 준영속 상태, update 쿼리가 안 나간다.

             */

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close(); // WAS가 내려갈 때 닫아줘야, connection pooling이 리소스가 릴리즈된다.
    }
}
