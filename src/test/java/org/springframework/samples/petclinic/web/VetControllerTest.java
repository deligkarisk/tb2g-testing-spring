package org.springframework.samples.petclinic.web;

import org.aspectj.asm.IModelFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;

    @Mock
    Map<String, Object> model;

    Collection<Vet> vetCollection;


    @BeforeEach
    void setUp() {
        Vets vets = new Vets();
        Vet vet = new Vet();
        vet.setId(4);
        vetCollection = new ArrayList<>();
        vetCollection.add(vet);
    }

    @Test
    void showVetList() {
        // given

        // when
        String view = vetController.showVetList(model);

        // then
        assertEquals("vets/vetList", view);
        then(clinicService).should(times(1)).findVets();
        then(model).should().put(anyString(), any());
        then(model).shouldHaveNoMoreInteractions();

    }

    @Test
    void showResourcesVetList() {
        // given
        given(clinicService.findVets()).willReturn(vetCollection);

        // when
        Vets returnedVets = vetController.showResourcesVetList();

        // then
        assertEquals(1, returnedVets.getVetList().size());
        assertEquals(Integer.valueOf(3), returnedVets.getVetList().get(0).getId());
    }
}