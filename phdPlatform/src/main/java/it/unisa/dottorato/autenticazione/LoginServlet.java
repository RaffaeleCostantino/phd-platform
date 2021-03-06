package it.unisa.dottorato.autenticazione;

import it.unisa.dottorato.account.Professor;
import it.unisa.dottorato.account.PhdStudent;
import it.unisa.dottorato.account.Account;
import it.unisa.integrazione.database.exception.ConnectionException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**Servlet incaricata ad effettuare la richiesta di login alla piattaforma
 *
 * @author gemmacatolino
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere l'email e la password dell'account
     * per effettuare l'accesso
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws EmailException if an email error occurs
     * @throws PasswordException if a password error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EmailException, PasswordException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            String username = request.getParameter("email");
            String password = request.getParameter("password");
            LoginManager loginManager = LoginManager.getInstance();

            Account account = loginManager.login(username, password);

            if (account != null) {
               if (account instanceof Account) {
                   session.removeAttribute("loginError");
                   session.setAttribute("account",account);
                   response.sendRedirect("index.jsp");
                   return;
               }
               if (account instanceof PhdStudent) {
                    session.removeAttribute("loginError");
                    session.setAttribute("account", account);
                    response.sendRedirect("index.jsp");
                    return;
                }
                if (account instanceof Professor) {
                    session.removeAttribute("loginError");
                    session.setAttribute("account", account);
                    response.sendRedirect("index.jsp");   
                    return;
                }
            }
                 else {
                    session.setAttribute("loginError", "error");
                    response.sendRedirect("login.jsp");
                    return;
                }
        } catch (SQLException sqlException) {
            out.print("<h1>SQL Exception: </h1>" + sqlException.getMessage());
        } catch (ConnectionException connectionException) {
            out.print("<h1>Connection Exception</h1>");
        } finally {
            out.close();
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
        doPost(request, response);
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
            processRequest(request, response);
        } catch (EmailException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PasswordException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
