package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.AbsentClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<AbsentClassInfo,Integer> {
}
