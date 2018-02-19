/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensta.authenticator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eleve
 */
public class AccountServlet extends HttpServlet {    
    static Pattern firstnamePattern = Pattern.compile("^firstname=(.+)$");
    static Pattern lastnamePattern = Pattern.compile("^lastname=(.+)$");
    
    private Ldap ldap;
    
    public AccountServlet() {
        super();
        this.ldap = new Ldap();
    }
    
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
            RequestDispatcher dispatcher;
            ServletContext context = getServletContext();
            
            HttpSession session = request.getSession(false);
            
            if (session == null) {
                response.sendRedirect(context.getContextPath() + "/login");
                return;
            }
            
            String path = request.getPathInfo();
            request.setAttribute("path", path);
            
            if ("/edit".equals(path)) {
                dispatcher = context.getNamedDispatcher("accountEdit");
            } else if (path == null || "/".equals(path)) {
                dispatcher = context.getNamedDispatcher("accountInfo");
            } else {
                response.sendError(404);
                return;
            }
            
            request.setAttribute("uname", "victma");
            request.setAttribute("email", "d.duck@disney.com");
            request.setAttribute("firstname", "Donald");
            request.setAttribute("lastname", "Duck");
            
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
        RequestDispatcher dispatcher;
        ServletContext context = getServletContext();
            
        HttpSession session = request.getSession(false);
            
        if (session == null) {
            response.sendRedirect(context.getContextPath() + "/login");
            return;
        }
        
        if (credentials.get("uname").isEmpty())
        
          
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manage client account";
    }

}
