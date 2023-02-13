package org.aquam.repository;

import org.aquam.model.SupportLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportLetterRepository extends JpaRepository<SupportLetter, Long> {
}
