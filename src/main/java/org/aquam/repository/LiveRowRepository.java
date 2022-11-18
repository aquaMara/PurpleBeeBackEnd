package org.aquam.repository;

import org.aquam.model.LiveRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiveRowRepository extends JpaRepository<LiveRow, Long> {

    Optional<List<LiveRow>> findByPatternId(Long patternId);

}
