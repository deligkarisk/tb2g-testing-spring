package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    ArgumentCaptor<Owner> ownerArgumentCaptor;


    @Test
    void testMethodOwnerPostValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("Address", "123 Duffet Street")
                        .param("city", "Athens")
                        .param("telephone", "000700400"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testMethodOwnerPostInvalid() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("city", "Athens"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));

    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("Address", "123 Duffet Street")
                        .param("city", "Athens")
                        .param("telephone", "000700400"))
                .andExpect(status().is3xxRedirection());
        then(clinicService).should().saveOwner(ownerArgumentCaptor.capture());
        assertEquals((Integer) 22,ownerArgumentCaptor.getValue().getId());
        then(clinicService).should().saveOwner(any(Owner.class));
    }

    @Test
    void processUpdateOwnerFormInvalid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("city", "Athens")
                        .param("telephone", "000700400"))
                .andExpect(status().isOk())
                .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
        then(clinicService).shouldHaveZeroInteractions();
    }

    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners")
                        .param("lastName", "Do not find me"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testFindByNameOneOwner() throws Exception {

        Owner owner = new Owner();
        owner.setId(24);
        Collection<Owner> owners = new HashSet<>();
        owners.add(owner);
        given(clinicService.findOwnerByLastName("One")).willReturn(owners);

        mockMvc.perform(get("/owners").param("lastName", "One"))
                .andExpect(view().name("redirect:/owners/24"))
                .andExpect(status().is3xxRedirection());
        Mockito.reset(clinicService);
    }


    @Test
    void testFindByNameManyOwners() throws Exception {
        Owner owner = new Owner();
        owner.setId(24);
        Owner owner2 = new Owner();
        owner.setId(55);
        Collection<Owner> owners = new HashSet<>();
        owners.add(owner);
        owners.add(owner2);
        given(clinicService.findOwnerByLastName("")).willReturn(owners);

        mockMvc.perform(get("/owners")
                        .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
        assertEquals("", stringArgumentCaptor.getValue());
        Mockito.reset(clinicService);


    }

    @Test
    void initCreationFormTest() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        Mockito.reset(clinicService);

    }


}