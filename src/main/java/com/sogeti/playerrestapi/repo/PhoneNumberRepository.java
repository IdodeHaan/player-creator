package com.sogeti.playerrestapi.repo;

import com.sogeti.playerrestapi.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
}
