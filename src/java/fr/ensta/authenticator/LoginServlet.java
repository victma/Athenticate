/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensta.authenticator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author eleve
 */
public class LoginServlet extends HttpServlet {
    static Pattern unamePattern = Pattern.compile("^uname=(.+)$");
    static Pattern pwdPattern = Pattern.compile("^password=(.+)$");
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getAttribute("error") == null) {
                request.setAttribute("error", false);
            }
            
            RequestDispatcher dispatcher;
            ServletContext context = getServletContext();
                        
            dispatcher = context.getNamedDispatcher("loginForm");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            log("Exception dans AccountServlet.doGet()", e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            List<String> params;
            params = request.getReader().lines().collect(Collectors.toList());
            Map<String, String> credentials = new HashMap();
            
            for (String temp : params) {
                Matcher matcher = LoginServlet.unamePattern.matcher(temp);
                if(matcher.matches()) {
                    credentials.put("uname", matcher.group(1));
                    continue;
                } else {
                    matcher = LoginServlet.pwdPattern.matcher(temp);
                    if(matcher.matches()) {
                        credentials.put("password", matcher.group(1));
                    }
                }
            }
            
            if (credentials.size() != 2) {
                request.setAttribute("error", true);
                this.doGet(request, response);
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Not implemented</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>POST request</h1><p>");
            out.println(credentials);
            out.println("</p></body>");
            out.println("</html>");
        } catch (Exception e) {
            log("Exception dans AccountServlet.doPost()", e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manage login operations";
    }

}
