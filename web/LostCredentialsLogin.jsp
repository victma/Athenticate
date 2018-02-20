<%-- 
    Document   : LostCredentials
    Created on : Feb 20, 2018, 8:48:29 AM
    Author     : eleve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lost password</title>
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
        </style>
    </head>
    <body>
        <form action="<%=application.getContextPath()%>/lostcredentials" method="post" enctype="text/plain">
            <input type="hidden" name="step" value="1">
            <input type="text" name="uid" placeholder="User ID" required><br />
            
            <input type="submit" value="Go to the security question"> 
        </form>
    </body>
</html>
