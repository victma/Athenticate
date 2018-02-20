<%-- 
    Document   : AccountEditValidation
    Created on : Feb 20, 2018, 9:54:41 AM
    Author     : eleve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Update</title>
    </head>
    <body>
        <h1>Account Update</h1>
        <% if ((boolean) request.getAttribute("ok")) { %>
            <p>Modifications saved.</p>
        <% } else { %>
            <p>There was an error :( Please try again later.</p>
        <% } %>
        
        <p><a href="<%=application.getContextPath()%>/account">Go back to my profile</a> 
    </body>
</html>
