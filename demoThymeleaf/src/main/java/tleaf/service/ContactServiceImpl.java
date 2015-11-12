package tleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tleaf.dao.ContactRepository;
import tleaf.domain.Contact;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    //@Autowired
    private ContactRepository contactRepository;
    //was autowired to private field; creating constructor..
    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    @Override
    public void addContact(Contact contact) {
        contactRepository.addContact(contact);
    }

    @Transactional
    @Override
    public List<Contact> listContact() {
        return contactRepository.listContact();
    }

    @Transactional
    @Override
    public void removeContact(Integer id) {
        contactRepository.removeContact(id);
    }
}
