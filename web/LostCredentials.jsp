<%-- 
    Document   : LostCredentials
    Created on : Feb 20, 2018, 8:48:29 AM
    Author     : eleve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <style>
            form {
                padding: 20px 30%;
                text-align: right;
            }
            input {
                box-sizing: border-box;
                width: 100%;
                padding: 10px;
                margin-top: 20px;
            }
            a {font-size: small}
            .errorMsg {color: red}
        </style>
    </head>
    <body>
        <form action="<%=application.getContextPath()%>/login" method="post" enctype="text/plain">
            <h1>Hello <%=request.getAttribute("securityquestion")%>!</h1>
            <input type="text" placeholder="Security question answer" name="securityanswer" required><br />
            
            <input type="submit" value="Reset Password">
            <% if ((boolean) request.getAttribute("error")) { %>
                <p class="errorMsg">Wrong answer. Try again</p>
            <% } %>    
        </form>
    </body>
</html>
