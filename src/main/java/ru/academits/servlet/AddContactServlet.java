package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.ContactConverter;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;
import ru.academits.service.ContactValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AddContactServlet extends HttpServlet {
    private ContactService contactService = PhoneBook.contactService;
    private ContactConverter contactConverter = PhoneBook.contactConverter;
    private ContactDao contactDao = PhoneBook.contactDao;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contactParams = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Contact contact = contactConverter.convertFormStringParam(contactParams);
        contact.setId(contactDao.getNewId());

        ContactValidation contactValidation = contactService.addContact(contact);
        contactService.saveLastContactValidation(contactValidation);
        if (contactValidation.isValid()) {
            contactService.saveLastContact(new Contact());
        } else {
            contactService.saveLastContact(contact);
        }

        resp.sendRedirect("/phonebook");
    }
}
