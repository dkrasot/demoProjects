package tleaf.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tleaf.domain.Contact;

import java.util.List;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addContact(Contact contact) {
        sessionFactory.getCurrentSession().save(contact);
    }

    @Override
    @SuppressWarnings("unchecked") //???
    public List<Contact> listContact() {
        return sessionFactory.getCurrentSession().createQuery("from Contact").list();
    }

    @Override
    public void removeContact(Integer id) {
        Contact contact = (Contact) sessionFactory.getCurrentSession().load(Contact.class, id);
        if (null != contact) {
            sessionFactory.getCurrentSession().delete(contact);
        }
    }
}
