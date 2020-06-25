package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;
import ru.academits.service.ContactValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    private ContactService contactService = PhoneBook.contactService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contactList", contactService.getAllContacts());
        req.setAttribute("contactValidation", contactService.getLastContactValidation());
        req.setAttribute("currentContact", contactService.getLastContact());
        req.getRequestDispatcher("phonebook.jsp").forward(req, resp);
    }
}
