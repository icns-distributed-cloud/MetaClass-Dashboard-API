package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.MailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailLogRepository extends JpaRepository<MailLog, Long> {
}
