package com.sogeti.playerrestapi.repo;

import com.sogeti.playerrestapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
