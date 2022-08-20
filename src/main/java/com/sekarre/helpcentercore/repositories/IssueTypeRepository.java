package com.sekarre.helpcentercore.repositories;

import com.sekarre.helpcentercore.domain.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {
}
