package it.unisa.dottorato.phdProfile.collaborations;

import it.unisa.dottorato.account.PhdStudent;
import it.unisa.dottorato.exception.CollaborationException;
import it.unisa.dottorato.exception.DateException;
import it.unisa.dottorato.exception.DescriptionException;
import it.unisa.dottorato.exception.IdException;
import it.unisa.dottorato.exception.IstitutionException;
import it.unisa.dottorato.exception.ReferenceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
 
/** Servlet incaricata ad effettuare la richiesta di aggiunta di una nuova
 * collaborazione
 *
 * @author gemmacatolino
 */
@WebServlet(name = "AddCollaboration", urlPatterns = {"/AddCollaboration"})
public class AddCollaborationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere l'istituzione, la descrizione, la data
     * di inizio e di fine della collaborazione, per effettuare la richiesta di
     * aggiunta di una nuova collaborazione
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CollaborationException, DateException, ReferenceException, DescriptionException, IstitutionException, IdException {

        JSONObject result = new JSONObject();
        PrintWriter out = response.getWriter();

        try {
            response.setContentType("text/html;charset=UTF-8");
            
            String istitution = request.getParameter("istitution");
            String description = request.getParameter("description");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            
            HttpSession session = request.getSession();
            PhdStudent loggedPerson = (PhdStudent) session.getAttribute("account"); 
            
            Collaboration collaboration = new Collaboration();
            
            collaboration.setIstitution(istitution);
            collaboration.setDescription(description);
            collaboration.setStartDate(java.sql.Date.valueOf(startDate));
            collaboration.setEndDate(java.sql.Date.valueOf(endDate));
            collaboration.setFkPhdstudent(loggedPerson.getfkAccount()); //da verificare
            
            CollaborationManager.getInstance().insert(collaboration);
            
            out.println("<script type=\"text/javascript\">");
            out.println("alert('La collaborazione è stata inserita');");
            out.println("location='profileNuovo.jsp';");
            out.println("</script>");
            
        } catch (SQLException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
            
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
        try {
            processRequest(request, response);
        } catch (CollaborationException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DateException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReferenceException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DescriptionException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IstitutionException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (CollaborationException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DateException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReferenceException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DescriptionException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IstitutionException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdException ex) {
            Logger.getLogger(AddCollaborationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
