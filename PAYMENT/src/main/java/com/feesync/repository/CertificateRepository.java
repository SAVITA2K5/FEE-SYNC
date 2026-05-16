package com.feesync.repository;

import com.feesync.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Certificate entity
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByCertificateId(String certificateId);

    Optional<Certificate> findByRollNumber(String rollNumber);
}
