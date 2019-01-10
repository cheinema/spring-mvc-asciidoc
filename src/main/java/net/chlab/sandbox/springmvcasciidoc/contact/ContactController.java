package net.chlab.sandbox.springmvcasciidoc.contact;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        Assert.notNull(contactRepository, "contactRepository must not be null");
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }
}
