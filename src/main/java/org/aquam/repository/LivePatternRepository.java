package org.aquam.repository;

import org.aquam.model.LivePattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivePatternRepository extends JpaRepository<LivePattern, Long> {
}
