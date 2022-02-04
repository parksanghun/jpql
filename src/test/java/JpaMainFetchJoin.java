import jpql.Member;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpaMainFetchJoin {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamA");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member1");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();


//            String query = "select m from Member m join fetch m.team";
//            String query = "select m from Member m join fetch m.team";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            for (Member m : resultList) {
//                System.out.println(m);
//            }

//            String query = "select t from Team t join fetch t.members";
            String query = "select distinct t from Team t join fetch t.members";

            List<Team> result = em.createQuery(query, Team.class).getResultList();

            for (Team t : result) {
                System.out.println(t);
                for (Member m : t.getMembers()) {
                    System.out.println(m);
                }
            }

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
