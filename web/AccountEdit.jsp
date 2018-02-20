<%-- 
    Document   : AccountEdit
    Created on : Feb 13, 2018, 11:17:32 AM
    Author     : eleve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Profile</title>
        
        <style>
            form {
                padding: 20px;
            }
            .info {
                padding: 5px;
                display: flex;
                align-items: center;
            }
            label {margin-right: 10px;}
            input[type="text"], input[type="email"] {
                flex-grow: 1;
                padding: 5px;
            }
            input[type="submit"] {
                box-sizing: border-box;
                width: 100%;
                padding: 10px;
                margin-top: 20px;
            }
        </style>
    </head>
     <body>
        <h1>Edit Profile</h1>
        <form action="<%=application.getContextPath()%>/account/edit" method="post" enctype="text/plain">
            <div class="info">
                <label>First Name:</label> 
                <input type="text" name="firstname" placeholder="<%=request.getAttribute("firstname")%>"><br />
            </div>
            <div class="info">
                <label>Last Name:</label> 
                <input type="text" name="lastname" placeholder="<%=request.getAttribute("lastname")%>"><br />
            </div>
            <div class="info">
                <label>Email Address:</label> 
                <input type="email" name="email" placeholder="<%=request.getAttribute("email")%>"><br />
            </div>
            <div class="info">
                <label>Secret Question:</label> 
                <input type="text" name="question" placeholder="<%=request.getAttribute("question")%>"><br />
            </div>
            
            <input type="submit" value="Update">
        </form>
    </body>
</html>
