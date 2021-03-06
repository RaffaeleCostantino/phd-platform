package it.unisa.dottorato.phdProfile.missions;

import it.unisa.dottorato.account.PhdStudent;
import it.unisa.dottorato.exception.DateException;
import it.unisa.dottorato.exception.DescriptionException;
import it.unisa.dottorato.exception.IdException;
import it.unisa.dottorato.exception.MissionException;
import it.unisa.dottorato.exception.PlaceException;
import it.unisa.dottorato.exception.ReferenceAttributeException;
import it.unisa.dottorato.exception.ReferenceException;
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
import org.json.JSONException;
import org.json.JSONObject;

/**Servlet incaricata ad effettuare la richiesta di inserimento di una nuova missione
 *
 * @author gemmacatolino
 */
@WebServlet(name = "AddMissionServlet", urlPatterns = {"/AddMissionServlet"})
public class AddMissionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere la descrizione, la data di inizio e 
     * di fine, la referenza e il luogo di una missione per effettuare la
     * richiesta di aggiunta di una nuova missione
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        JSONObject result = new JSONObject();
        PrintWriter out = response.getWriter();
        
        try {
            try {
                response.setContentType("text/html;charset=UTF-8");

                
                String description = request.getParameter("description");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String reference = request.getParameter("reference");
                String place = request.getParameter("place");
                
                HttpSession session = request.getSession();
                PhdStudent loggedPerson = (PhdStudent) session.getAttribute("account");  

                Mission mission = new Mission();

                
                mission.setDescription(description);
                mission.setStartDate(java.sql.Date.valueOf(startDate));
                mission.setEndDate(java.sql.Date.valueOf(endDate));
                mission.setReference(reference);
                mission.setPlace(place);
                mission.setFkPhdstudent(loggedPerson.getfkAccount()); // da verificare

                MissionManager.getInstance().insert(mission);
                result.put("result", true);

                out.println("<script type=\"text/javascript\">");
                out.println("alert('La missione è stata inserita');");
                out.println("location='profileNuovo.jsp';");
                out.println("</script>");
            } catch (SQLException ex) {
                Logger.getLogger(AddMissionServlet.class.getName()).log(Level.SEVERE, null, ex);
                result.put("result", false);
            } catch (MissionException | DescriptionException | DateException | ReferenceException | PlaceException | ReferenceAttributeException | IdException ex) {
                Logger.getLogger(AddMissionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.write(result.toString());

        } catch (JSONException ex) {
            Logger.getLogger(AddMissionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
