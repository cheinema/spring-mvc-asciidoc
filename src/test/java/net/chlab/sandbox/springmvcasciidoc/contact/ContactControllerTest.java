package net.chlab.sandbox.springmvcasciidoc.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
@ExtendWith(RestDocumentationExtension.class)
class ContactControllerTest {

    MockMvc mvc;

    @MockBean
    ContactRepository repository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

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
                        "'email':'john.doe@example.com'}]"))
                .andDo(document("contacts-list-sample", responseFields(
                        fieldWithPath("[].id").description("unique identifier of the contact"),
                        fieldWithPath("[].firstName").description("the first name of the contact"),
                        fieldWithPath("[].lastName").description("the last name of the contact"),
                        fieldWithPath("[].email").description("the email address of the contact")
                )));
    }
}
