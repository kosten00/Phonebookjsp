package ru.academits.service;

import ru.academits.PhoneBook;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactService {
    private ContactDao contactDao = PhoneBook.contactDao;

    private boolean isExistingContactInDatabase(int id) {
        List<Contact> contactList = contactDao.getAllContacts();

        return contactList.stream().anyMatch(contact -> contact.getId() == (id));
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.getAllContacts();
        for (Contact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public ContactValidation validateContactOnRemove(int contactId) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (!isExistingContactInDatabase(contactId)) {
            contactValidation.setValid(false);
            contactValidation.setGlobalError("Попытка удаления контакта, которого нет в базе данных!");
        }

        return contactValidation;
    }

    private ContactValidation validateContactsOnBatchRemove(int[] ids) {
        ContactValidation contactValidation = new ContactValidation();

        contactValidation.setValid(true);

        int[] notValidIds = new int[ids.length];
        int notValidIdsCounter = 0;

        for (int id : ids) {
            if (isExistingContactInDatabase(id)) {
                contactDao.remove(id);
            } else {
                notValidIds[notValidIdsCounter] = id;
                notValidIdsCounter++;
            }
        }

        if (notValidIdsCounter > 0) {
            contactValidation.setValid(false);
            contactValidation.setGlobalError("Контакты с ID = " + Arrays.toString(notValidIds) + " отсутствуют в базе данных!");
        }

        return contactValidation;
    }

    public ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (StringUtils.isEmpty(contact.getFirstName())) {
            contactValidation.setValid(false);
            contactValidation.setFirstNameError("Поле Имя должно быть заполнено.");
        }

        if (StringUtils.isEmpty(contact.getLastName())) {
            contactValidation.setValid(false);
            contactValidation.setLastNameError("Поле Фамилия должно быть заполнено.");
        }

        if (StringUtils.isEmpty(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setPhoneError("Поле Телефон должно быть заполнено.");
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setGlobalError("Номер телефона не должен дублировать другие номера в телефонной книге.");
        }

        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);
        if (contactValidation.isValid()) {
            contactDao.add(contact);
        }
        return contactValidation;
    }

    public ContactValidation removeSingleContact(String contactId) {
        int id = Integer.parseInt(contactId);

        ContactValidation contactValidation = validateContactOnRemove(id);
        if (contactValidation.isValid()) {
            contactDao.remove(id);
        }
        return contactValidation;
    }

    public ContactValidation batchRemove(String[] contactsIds) {
        int[] ids = new int[contactsIds.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = Integer.parseInt(contactsIds[i]);
        }

        return validateContactsOnBatchRemove(ids);
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public List<Contact> getFilteredContacts(String filterString) {
        filterString = filterString.toLowerCase();

        List<Contact> allContacts = contactDao.getAllContacts();
        List<Contact> filteredContacts = new ArrayList<>();

        for (Contact contact : allContacts) {
            if (contact.getPhone().contains(filterString)
                    || contact.getFirstName().toLowerCase().contains(filterString)
                    || contact.getLastName().toLowerCase().contains(filterString)) {
                filteredContacts.add(contact);
            }
        }

        return filteredContacts;
    }

    public void saveLastContact(Contact contact) {

        contactDao.saveLastContact(contact);
    }

    public Contact getLastContact() {
        return contactDao.getLastContact();
    }

    public void saveLastContactValidation(ContactValidation contactValidation) {
        contactDao.saveLastContactValidation(contactValidation);
    }

    public ContactValidation getLastContactValidation() {
        return contactDao.getLastContactValidation();
    }
}