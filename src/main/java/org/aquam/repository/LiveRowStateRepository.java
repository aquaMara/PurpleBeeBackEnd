package org.aquam.repository;

import org.aquam.model.LiveRowState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface LiveRowStateRepository extends JpaRepository<LiveRowState, Long> {

    @Transactional
    @Query(value=" SELECT liveRowState FROM LiveRowState liveRowState WHERE liveRowState.liveRowId = :lId and liveRowState.appUserId = :uId")
    Optional<LiveRowState> findLiveRowState(@Param("lId") Long liveRowId, @Param("uId") Long userId);

}
