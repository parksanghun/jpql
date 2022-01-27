import jpql.Address;
import jpql.Member;
import jpql.MemberDto;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpaMainProjection {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            Member findMember = result.get(0);
            findMember.setAge(20);

            List<Team> result2 = em.createQuery("select m.team from Member m", Team.class)
                    .getResultList();

            List<Team> result3 = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            List result4 = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] oResult = (Object[]) o;
            System.out.println(oResult[0]);
            System.out.println(oResult[1]);

            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            Object[] oResult2 = resultList2.get(0);
            System.out.println(oResult2[0]);
            System.out.println(oResult2[1]);

            List<MemberDto> resultList3 = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }


        emf.close();
    }
}
