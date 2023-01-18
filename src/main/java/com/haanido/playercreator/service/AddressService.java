package com.haanido.playercreator.service;

import com.haanido.playercreator.repo.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public void deleteAddress(int id) {
        addressRepository.deleteById(id);
    }
}
