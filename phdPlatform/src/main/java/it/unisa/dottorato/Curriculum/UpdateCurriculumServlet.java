package it.unisa.dottorato.Curriculum;

import it.unisa.dottorato.exception.DescriptionException;
import it.unisa.dottorato.exception.NameException;
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


/** Servlet incaricata ad effettuare la richiesta di aggiornamento di un curriculum
 *
 * @author Tommaso Minichiello
 */
@WebServlet(name = "UpdateCurriculum", urlPatterns = {"/UpdateCurriculum"})
public class UpdateCurriculumServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods
     * Aggiorna un oggetto curriculum
     *
     * @param request oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere il nome del vecchio curriculum 
     * <code>oldNameCurriculum</code>, il nome del nuovo curriculum <code>newNameCurriculum</code>,
     * la nuova descrizione <code>description</code> per effettuare l'aggiornamento
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NameException, DescriptionException, Exception{
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {

            JSONObject result = new JSONObject();

            String oldNameCurriculum = request.getParameter("oldNameCurriculum");
            String newNameCurriculum = request.getParameter("newNameCurriculum");
            String description = request.getParameter("description");

            Curriculum aPhdCurriculum = new Curriculum();
            aPhdCurriculum.setName(newNameCurriculum);
            aPhdCurriculum.setDescription(description);

            result.put("result", true);

            try {
                CurriculumManager.getInstance().update(oldNameCurriculum, aPhdCurriculum);

            } catch (ClassNotFoundException | SQLException ex) {
                result.put("result", false);
                Logger.getLogger(UpdateCurriculumServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.write(result.toString());

        } catch (JSONException ex) {
            Logger.getLogger(UpdateCurriculumServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (DescriptionException ex) {
            Logger.getLogger(UpdateCurriculumServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateCurriculumServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (DescriptionException ex) {
            Logger.getLogger(UpdateCurriculumServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateCurriculumServlet.class.getName()).log(Level.SEVERE, null, ex);
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
