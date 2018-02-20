/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensta.authenticator;

import com.unboundid.ldap.sdk.LDAPException;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author eleve
 */
public class AccountServlet extends HttpServlet {
    private Map<String, Pattern> info;
    
    public AccountServlet() {
        super();
        this.info = new HashMap();
        this.info.put("givenname", Pattern.compile("^firstname=(.+)$"));
        this.info.put("sn", Pattern.compile("^lastname=(.+)$"));
        this.info.put("mail", Pattern.compile("^email=(.+)$"));
        this.info.put("securityquestion", Pattern.compile("^question=(.+)$"));
        this.info.put("securityanswer", Pattern.compile("^answer=(.+)$"));
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
            
            Ldap ldap = (Ldap) session.getAttribute("ldap");
            
            request.setAttribute("uname", ldap.getUid());
            request.setAttribute("email", ldap.getAttribute("mail"));
            request.setAttribute("firstname", ldap.getAttribute("givenname"));
            request.setAttribute("lastname", ldap.getAttribute("sn"));
            request.setAttribute("question", ldap.getAttribute("securityquestion"));
            
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
        
        Ldap ldap = (Ldap) session.getAttribute("ldap");
        
        List<String> params;
        params = request.getReader().lines().collect(Collectors.toList());
        
        request.setAttribute("ok", true);
        
        try {
            for (String temp : params) {
                for (String key : this.info.keySet()) {
                    Matcher matcher = this.info.get(key).matcher(temp);
                    if (matcher.matches()) {
                        if (!matcher.group(1).isEmpty()) {
                            ldap.update(key, matcher.group(1));
                        }
                        break;
                    }
                }
            }
        } catch (LDAPException e) {
            request.setAttribute("ok", false);
            System.out.print(e);
        }
        
        dispatcher = context.getNamedDispatcher("accountEditValidation");
        dispatcher.forward(request, response);
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
