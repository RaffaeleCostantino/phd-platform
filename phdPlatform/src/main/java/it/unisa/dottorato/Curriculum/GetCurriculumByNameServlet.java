package it.unisa.dottorato.Curriculum;

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
import org.json.JSONException;
import org.json.JSONObject;


/** Servlet incaricata ad effettuare la richiesta di ricerca di un curriculum 
 * dato il nome
 *
 * @author Tommaso Minichiello
 */
@WebServlet(name = "GetCurriculumByName", urlPatterns = {"/GetCurriculumByName"})
public class GetCurriculumByNameServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods
     * Ricostruisce un oggetto curriculum effettuando una ricerca dato il nome
     *
     * @param request oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere il nome del curriculum 
     * <code>curriculumName</code> per effettuare la ricerca
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String CurriculumName = request.getParameter("CurriculumName");
            JSONObject result = new JSONObject();
            try {
                Curriculum curriculum = CurriculumManager.getInstance().getCurriculumByName(CurriculumName);
                result.put("CurriculumDescription", curriculum.getDescription());
                result.put("CurriculumName", curriculum.getName());
                out.write(result.toString());
            } catch (ClassNotFoundException | SQLException | JSONException ex) {
                Logger.getLogger(GetCurriculumByNameServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(GetCurriculumByNameServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(GetCurriculumByNameServlet.class.getName()).log(Level.SEVERE, null, ex);
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
