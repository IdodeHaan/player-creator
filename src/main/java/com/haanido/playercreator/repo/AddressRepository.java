package com.haanido.playercreator.repo;

import com.haanido.playercreator.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
