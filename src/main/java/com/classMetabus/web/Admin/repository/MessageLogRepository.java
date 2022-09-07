package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
}
