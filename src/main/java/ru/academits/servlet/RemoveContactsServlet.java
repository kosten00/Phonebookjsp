package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.service.ContactService;
import ru.academits.service.ContactValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveContactsServlet extends HttpServlet {
    private ContactService contactService = PhoneBook.contactService;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contactIdToRemove = req.getParameter("contactId");

        ContactValidation contactValidation;

        if (contactIdToRemove == null) {
            String[] contactsIdsToRemove = req.getParameterValues("selectedContactsIds");
            contactValidation = contactService.batchRemove(contactsIdsToRemove);
        } else {
            contactValidation = contactService.removeSingleContact(contactIdToRemove);
        }
        contactService.saveLastContactValidation(contactValidation);

        resp.sendRedirect("/phonebook");
    }
}
