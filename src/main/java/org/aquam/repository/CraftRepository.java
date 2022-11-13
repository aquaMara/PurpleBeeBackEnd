package org.aquam.repository;

import org.aquam.model.Craft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CraftRepository extends JpaRepository<Craft, Long> {

    Optional<Craft> findByName(String name);
}
