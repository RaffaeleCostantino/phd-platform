package it.unisa.dottorato.phdProfile.collaborations;

import it.unisa.dottorato.account.PhdStudent;
import it.unisa.dottorato.exception.ReferenceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**Servlet incaricata ad effettuare la richiesta di visualizzazioni di tutte le 
 * collaborazioni di un dottorando
 *
 * @author gemmacatolino
 */
@WebServlet(name = "GetAllCollaborationsServlet", urlPatterns = {"/GetAllCollaborationsServlet"})
public class GetAllCollaborationsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request oggetto request per accedere alla sessione chiamante attraverso
     * il metodo getSession per ottenere la sessione chiamante la servlet
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ReferenceException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JSONObject result = new JSONObject();
            try {
                
                HttpSession session = request.getSession();
                PhdStudent phdStudent = (PhdStudent) session.getAttribute("account"); // da verificare
                
                ArrayList<Collaboration> collaborations = (ArrayList<Collaboration>) CollaborationManager.getInstance().getAllCollaborationOf(phdStudent); // da verificare
                JSONArray resultArray = new JSONArray(collaborations);
                result.put("collaborations", resultArray);
                out.write(result.toString());
            } catch (SQLException | JSONException ex) {
                Logger.getLogger(GetAllCollaborationsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        } catch (ReferenceException ex) {
            Logger.getLogger(GetAllCollaborationsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ReferenceException ex) {
            Logger.getLogger(GetAllCollaborationsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
