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
public class LostCredentials extends HttpServlet {
    
    static Pattern unamePattern = Pattern.compile("^uname=(.+)$");
    static Pattern pwdPattern = Pattern.compile("^password=(.+)$");

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LostCredentials</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LostCredentials at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        try {
            ServletContext context = getServletContext();
            
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
            
            if (credentials.size() != 2
                    || credentials.get("uname").isEmpty()
                    || credentials.get("password").isEmpty()) {
                request.setAttribute("error", true);
                this.doGet(request, response);
                return;
            }
            
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
            
            response.sendRedirect(context.getContextPath() + "/account");
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
        return "Short description";
    }// </editor-fold>

}
