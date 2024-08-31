<%-- 
    Document   : panel
    Created on : 27/08/2024, 11:07:46 p. m.
    Author     : elian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
        if(session.getAttribute("usuario") ==null); {
             response.sendRedirect("index.html");
             return;
        }
        %>
        
        <h1>Bienvenidos</h1>
    </body>
</html>
