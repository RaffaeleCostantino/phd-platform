package it.unisa.dottorato.curriculumcic;

import it.unisa.dottorato.account.Professor;
import it.unisa.dottorato.exception.IdException;
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


/**Servlet incaricata ad effettuare la richiesta di visualizzazione dei 
 * coordinatori di un curriculum-ciclo
 *
 * @author Tommaso Minichiello
 */
@WebServlet(name = "ViewCurriculumcicCoordinatorServlet", urlPatterns = {"/ViewCurriculumcicCoordinatorServlet"})
public class ViewCurriculumcicCoordinatorServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere il numero del ciclo fkCycle, il nome
     * del curriculum fkCurriculum e l'email del coordinatore, per ricercare la coppia
     * curriculum-ciclo e visualizzarne il coordinatore
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws it.unisa.dottorato.exception.IdException
     * @throws it.unisa.dottorato.exception.NameException
     * @throws it.unisa.dottorato.curriculumcic.CurriculumcicException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IdException, NameException, CurriculumcicException {

        try {
            response.setContentType("text/html;charset=UTF-8");
            
            PrintWriter out = response.getWriter();
            JSONObject result = new JSONObject();
            int number = Integer.parseInt( request.getParameter("fkCycle"));
            String name = request.getParameter("fkCurriculum");
            
            Curriculumcic curr=new Curriculumcic();
            curr.setfkCycle(number);
            curr.setfkCurriculum(name);
            
            try {
                Professor professor = CurriculumcicManager.getInstance().viewCurriculumcicCoordinator(curr);
                result.put("fkAccount", professor.getfkAccount());
                result.put("name", professor.getName());
                result.put("surname", professor.getSurname());
                if(professor.getName() == null) result.put("result", false);
                else result.put("result", true);
            } catch (ClassNotFoundException | SQLException ex) {
                result.put("result", false);
                Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            out.write(result.toString());
            
        } catch (JSONException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (IdException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NameException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CurriculumcicException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (IdException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NameException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CurriculumcicException ex) {
            Logger.getLogger(ViewCurriculumcicCoordinatorServlet.class.getName()).log(Level.SEVERE, null, ex);
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
