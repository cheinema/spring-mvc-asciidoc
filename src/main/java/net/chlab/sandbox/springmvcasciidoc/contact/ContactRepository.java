package net.chlab.sandbox.springmvcasciidoc.contact;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ContactRepository {

    public List<Contact> findAll() {
        return Collections.emptyList();
    }
}
