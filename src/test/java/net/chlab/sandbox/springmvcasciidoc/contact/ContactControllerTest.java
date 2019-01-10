package net.chlab.sandbox.springmvcasciidoc.contact;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ContactRepository repository;

    @Test
    void noContacts() throws Exception {
        when(repository.findAll()).thenReturn(emptyList());

        this.mvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void contactWithAllAttributesFilled() throws Exception {
        final Contact contact = new Contact("DUMMY_ID");
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john.doe@example.com");

        when(repository.findAll()).thenReturn(singletonList(contact));

        this.mvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':'DUMMY_ID','firstName':'John','lastName':'Doe'," +
                        "'email':'john.doe@example.com'}]"));
    }
}
