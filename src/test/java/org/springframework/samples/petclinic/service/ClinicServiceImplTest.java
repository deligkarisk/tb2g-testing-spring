package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {


    @Mock
    PetRepository petRepository;

    @Mock
    VetRepository vetRepository;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    ClinicServiceImpl clinicService;


    @Test
    void findPetTypes() {

        // given
        PetType petType = new PetType();
        petType.setName("Dog");
        List<PetType> petTypeList = new ArrayList<>();
        petTypeList.add(petType);
        given(petRepository.findPetTypes()).willReturn(petTypeList);

        // when
        Collection<PetType> returnedPetTypes = clinicService.findPetTypes();

        // then
        assertEquals(1, returnedPetTypes.size());
        assertTrue(returnedPetTypes.contains(petType));
        then(petRepository).should().findPetTypes();
        then(petRepository).shouldHaveNoMoreInteractions();

    }
}