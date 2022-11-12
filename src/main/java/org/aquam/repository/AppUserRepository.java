package org.aquam.repository;

import org.aquam.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Query(value=" SELECT au FROM AppUser au WHERE au.enabled = :enabled and au.registrationDate < :registrationDate")
    List<AppUser> findUnconfirmedAccounts(@Param("enabled") Boolean enabled, @Param("registrationDate") LocalDateTime expirationThreshold);

    @Transactional
    @Modifying
    @Query(value=" DELETE FROM AppUser appUser WHERE appUser.enabled = :enabled and appUser.registrationDate < :registrationDate")
    void deleteUnconfirmedAccounts(@Param("enabled") Boolean enabled, @Param("registrationDate") LocalDateTime expirationThreshold);

}
