package ru.academits.dao;

import ru.academits.model.Contact;
import ru.academits.service.ContactValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactDao {
    private List<Contact> contactList = new ArrayList<>();
    private static AtomicInteger idSequence = new AtomicInteger();
    private Contact lastContact = new Contact();
    private ContactValidation lastContactValidation = new ContactValidation();

    public ContactDao() {
        Contact contact = new Contact();
        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");
        contactList.add(contact);

        Contact contact1 = new Contact();
        contact1.setId(getNewId());
        contact1.setFirstName("Петр");
        contact1.setLastName("Петрович");
        contact1.setPhone("912312212");
        contactList.add(contact1);

        Contact contact2 = new Contact();
        contact2.setId(getNewId());
        contact2.setFirstName("Сидр");
        contact2.setLastName("Сидорович");
        contact2.setPhone("88005553535");
        contactList.add(contact2);
    }

    public int getNewId() {
        return idSequence.getAndIncrement();
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public void add(Contact contact) {
        contactList.add(contact);
    }

    public void remove(int contactId) {
        contactList.removeIf(contact -> contact.getId() == contactId);
    }

    public void saveLastContact(Contact contact) {
        this.lastContact = contact;
    }

    public Contact getLastContact() {
        return lastContact;
    }

    public void saveLastContactValidation(ContactValidation contactValidation) {
        this.lastContactValidation = contactValidation;
    }

    public ContactValidation getLastContactValidation() {
        return lastContactValidation;
    }
}
