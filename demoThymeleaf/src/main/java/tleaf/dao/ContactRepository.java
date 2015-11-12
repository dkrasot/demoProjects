package tleaf.dao;

import tleaf.domain.Contact;

import java.util.List;

public interface ContactRepository {

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Integer id);

}

//TODO LATER make JPA repo based on JpaRepo / CrudRepo
