<%-- 
    Document   : accountInfo
    Created on : Jan 30, 2018, 12:24:23 PM
    Author     : victma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Info</title>
        
        <style>
            header {
                width: 100%;
                text-align: right;
            }
            table, th, td { border: 1px solid black; }
            th, td { padding: 10px; }
        </style>
    </head>
    <body>
        <header><a href="<%=application.getContextPath()%>/logout">Logout</a></header>

        <h1>Hello <%=request.getAttribute("firstname")%>!</h1>
               
        <table>
            <tr>
                <th>User ID</th>
                <td><%=request.getAttribute("uname")%></td>
            </tr>
            <tr>
                <th>First Name</th>
                <td><%=request.getAttribute("firstname")%></td>
            </tr>
            <tr>
                <th>Last Name</th>
                <td><%=request.getAttribute("lastname")%></td>
            </tr>
            <tr>
                <th>Email address</th>
                <td><%=request.getAttribute("email")%></td>
            </tr>
        </table>
        <p>
            <a href="<%=application.getContextPath()%>/account/edit">Edit Profile</a>    
        </p>
        <%=request.getAttribute("path")%>
    </body>
</html>
