<%-- 
    Document   : LostCredentialsQuestion
    Created on : Feb 20, 2018, 11:06:02 AM
    Author     : eleve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
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
        <p><%=request.getAttribute("uid")%>'s question</p>
        <form action="<%=application.getContextPath()%>/lostcredentials" method="post" enctype="text/plain">
            <input type="hidden" name="step" value="2">
            <input type="hidden" name="uid" value="<%=request.getAttribute("uid")%>">
            <input type="text" name="answer" placeholder="Your answer" required>
            
            <input type="submit" value="Go to the security question"> 
        </form>
    </body>
</html>
