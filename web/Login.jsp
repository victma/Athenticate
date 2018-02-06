<%-- 
    Document   : Login
    Created on : Feb 6, 2018, 11:49:49 AM
    Author     : victma
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
        <form action="login" method="post" enctype="text/plain">
            <input type="text" placeholder="Username" name="uname" required><br />
            <input type="password" placeholder="Password" name="password" required><br />
            <a href="#">Forgot password?</a><br />
            
            <input type="submit" value="Login">
            <% if ((boolean) request.getAttribute("error")) { %>
                <p class="errorMsg">Wrong credentials. Try again</p>
            <% } %>    
        </form>
    </body>
</html>
