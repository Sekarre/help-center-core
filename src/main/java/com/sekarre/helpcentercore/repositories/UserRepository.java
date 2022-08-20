package com.sekarre.helpcentercore.repositories;

import com.sekarre.helpcentercore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "select distinct * " +
            " from users u" +
            "         join role_user ru on u.id = ru.user_id" +
            "         join role r on ru.role_id = r.id" +
            " where r.name = ?1 ", nativeQuery = true)
    Set<User> findAllByRoleName(String roleName);

    @Query(value = "select distinct *" +
            " from users u" +
            "         join role_user ru on u.id = ru.user_id" +
            "         join role r on ru.role_id = r.id" +
            "         left join issue_user iu on u.id = iu.user_id" +
            "         left join  issue i on i.id = iu.issue_id" +
            " where r.name = ?1 and (i.id != ?2 or i.id IS NULL);", nativeQuery = true)
    Set<User> findAllByRoleNameAndIssueIdNotEqual(String roleName, Long issueId);

    @Query(value = "select distinct *" +
            " from users u" +
            "         join role_user ru on u.id = ru.user_id" +
            "         join role r on ru.role_id = r.id" +
            " where r.specialization = 'GAMES'" +
            "  and r.name = 'SUPPORT'" +
            "  and u.id = (select u.id" +
            "              from  users u" +
            "                       left join issue_user iu on u.id = iu.user_id" +
            "                       left join issue i on i.id = iu.issue_id" +
            "                       join role_user ru2 on u.id = ru2.user_id" +
            "                       join role r2 on ru2.role_id = r2.id" +
            "              where r2.specialization = r.specialization" + "" +
            "              and r2.name = r.name" +
            "              group by u.id" +
            "              order by count(*) limit 1)", nativeQuery = true)
    Optional<User> findUsersWithLeastIssuesAndMatchingSpecialization();
}
