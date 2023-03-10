package com.haanido.playercreator.service;

import com.haanido.playercreator.repo.PhoneNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    public void deletePhoneNumber(int id) {
        phoneNumberRepository.deleteById(id);
    }

}
