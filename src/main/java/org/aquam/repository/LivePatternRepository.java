package org.aquam.repository;

import org.aquam.model.LiveRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivePatternRepository extends JpaRepository<LiveRow, Long> {
}
