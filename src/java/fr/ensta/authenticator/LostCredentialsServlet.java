package fr.ensta.authenticator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.unboundid.ldap.sdk.LDAPException;
import fr.ensta.authenticator.Ldap;
import fr.ensta.authenticator.LoginServlet;
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
public class LostCredentialsServlet extends HttpServlet {
    
    static Pattern stepPattern = Pattern.compile("^step=(.+)$");
    static Pattern uidPattern = Pattern.compile("^uid=(.+)$");

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
        RequestDispatcher dispatcher;
        ServletContext context = getServletContext();

        dispatcher = context.getNamedDispatcher("lostcredentialsPage1");
        dispatcher.forward(request, response);
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
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher;

        List<String> params;
        params = request.getReader().lines().collect(Collectors.toList());
        String step = "";
        String uid = "";

        for (String temp : params) {
            Matcher matcher = LostCredentialsServlet.stepPattern.matcher(temp);
            if(matcher.matches()) {
                step = matcher.group(1);
                continue;
            }
            matcher = LostCredentialsServlet.uidPattern.matcher(temp);
            if(matcher.matches()) {
                uid = matcher.group(1);
            }
        }
        
        request.setAttribute("uid", uid);
        
        if (step.equals("1")) {
            dispatcher = context.getNamedDispatcher("lostcredentialsPage2");
            dispatcher.forward(request, response);
        }
      
        /*
        // Check credentials in LDAP
        try {
            Ldap ldap = new Ldap();
            ldap.authenticate(credentials.get("uname"), credentials.get("password"));

            HttpSession session = request.getSession();
            session.setAttribute("ldap", ldap);
        } catch (LDAPException e) {
            request.setAttribute("error", true);
            this.doGet(request, response);
            return;
        }
            
            response.sendRedirect(context.getContextPath() + "/account");*/
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Lost credentials reset";
    }

}
