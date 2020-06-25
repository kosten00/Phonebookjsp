<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="ru.academits.model.Contact" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="">
<head>
    <%
        List<Contact> contactList = (List<Contact>) request.getAttribute("contactList");
        Contact currentContact = (Contact) request.getAttribute("currentContact");
    %>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/phonebook.css"/>
    <title>Phone book</title>
</head>
<body>
<div class="delete-dialog"></div>
<div class="alert" title="Нет выбранных контактов"></div>
<div class="content">
    <div class="filter-container">
        <form method="POST" action="filter">
            <label class="filter-label mb-0 mr-2">
                Введите текст:
                <input type="text" class="form-control input-sm" name="filteredString" id="filter-input"/>
            </label>
            <button class="btn btn-primary" type="submit">Отфильтровать</button>
            <button class="btn btn-primary" id="cancel-button" name="cancelFilter">Сбросить фильтр</button>
        </form>
    </div>
    <form method="POST" action="remove">
        <table class="table table-bordered contact-table">
            <thead>
            <tr>
                <th>
                    <label class="select-all-label">
                        <input type="checkbox" onClick="selectAll()" id="select-all" title="Выбрать"/>
                    </label>
                </th>
                <th>№</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Телефон</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
            <% int number = 0;
                for (Contact contact : contactList) {
                    number++;
            %>
            <tr>
                <td>
                    <label class="select-me-label">
                        <input type="checkbox" class="select-me" name="selectedContactsIds"
                               value="<%=contact.getId()%>"/>
                    </label>
                </td>
                <td>
                    <% out.println(number); %>
                </td>
                <td>
                    <% out.println(contact.getLastName()); %>
                </td>
                <td>
                    <% out.println(contact.getFirstName()); %>
                </td>
                <td>
                    <% out.println(contact.getPhone()); %>
                </td>
                <td>
                    <button class='btn btn-primary' type='submit' name="contactId" value="<%=contact.getId()%>">
                        Удалить
                    </button>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
        <button type="submit" class="btn btn-primary">Удалить
            выбранные
        </button>
    </form>
    <br>
    <label class="server-error-message-container">
        <span class="error-message">
             <c:if test="${not empty contactValidation.globalError}">
                 <c:out value="${contactValidation.globalError}">
                 </c:out>
             </c:if>
        </span>
    </label>
    <form action="add" method="POST">
        <div>
            <label class="form-label">
                <span class="form-field">Фамилия:</span>
                <input type="text" class="ml-1 form-control input-sm form-input" name="lastName"
                       value='<%=currentContact.getLastName() == null ? "" : currentContact.getLastName() %>'/>
                <span class="error-message">
                     <c:if test="${not empty contactValidation.lastNameError}">
                         <c:out value="${contactValidation.lastNameError}">
                         </c:out>
                     </c:if>
                </span>
            </label>
        </div>
        <div>
            <label class="form-label">
                <span class="form-field">Имя:</span>
                <input type="text" class="ml-1 form-control input-sm form-input" name="firstName"
                       value='<%=currentContact.getFirstName() == null ? "" : currentContact.getFirstName() %>'/>
                <span class="error-message">
                    <c:if test="${not empty contactValidation.firstNameError}">
                        <c:out value="${contactValidation.firstNameError}">
                        </c:out>
                    </c:if>
                </span>
            </label>
        </div>
        <div>
            <label class="form-label">
                <span class="form-field">Телефон:</span>
                <input type="number" class="ml-1 form-control input-sm form-input" name="phone"
                       value='<%=currentContact.getPhone() == null ? "" : currentContact.getPhone() %>'/>
                <span class="error-message">
                     <c:if test="${not empty contactValidation.phoneError}">
                         <c:out value="${contactValidation.phoneError}">
                         </c:out>
                     </c:if>
                </span>
            </label>
        </div>
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
</div>
<script>
    var allCheckBoxes = document.getElementsByClassName("select-me");
    var selectAllCheckbox = document.getElementById("select-all");

    function selectAll() {
        if (selectAllCheckbox.checked === true) {
            for (var i = 0; i < allCheckBoxes.length; i++) {
                allCheckBoxes[i].checked = true;
            }
        } else {
            for (var j = 0; j < allCheckBoxes.length; j++) {
                allCheckBoxes[i].checked = false;
            }
        }
    }
</script>
</body>
</html>