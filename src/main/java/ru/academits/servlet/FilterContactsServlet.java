package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterContactsServlet extends HttpServlet {
    private ContactService contactService = PhoneBook.contactService;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if (req.getParameter("cancelFilter") != null) {
            resp.sendRedirect("/phonebook");
        } else {
            String filterString = req.getParameter("filterString");
            req.setAttribute("contactList", contactService.getFilteredContacts(filterString));
            req.setAttribute("contactValidation", contactService.getLastContactValidation());
            req.setAttribute("currentContact", contactService.getLastContact());
            req.getRequestDispatcher("phonebook.jsp").forward(req, resp);
        }
    }
}
