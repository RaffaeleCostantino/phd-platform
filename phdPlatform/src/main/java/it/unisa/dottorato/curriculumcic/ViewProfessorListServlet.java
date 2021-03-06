package it.unisa.dottorato.curriculumcic;

import it.unisa.dottorato.account.Professor;
import it.unisa.dottorato.exception.IdException;
import it.unisa.dottorato.exception.NameException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**Servlet incaricata ad effettuare la richiesta di visualizzazione dei 
 * professori di un curriculum-ciclo
 *
 * @author Tommaso Minichiello
 */
@WebServlet(name = "ViewProfessorListServlet", urlPatterns = {"/ViewProfessorListServlet"})
public class ViewProfessorListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request oggetto request per accedere ai parametri inviati attraverso
     * il metodo getParameter per ottenere il numero del ciclo fkCycle, il nome
     * del curriculum fkCurriculum e l'email del coordinatore, per poter ricercare
     * la coppia curriculum-ciclo e visualizzarne i professori
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws it.unisa.dottorato.curriculumcic.CurriculumcicException
     * @throws it.unisa.dottorato.exception.IdException
     * @throws it.unisa.dottorato.exception.NameException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CurriculumcicException, IdException, NameException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           int number = Integer.parseInt( request.getParameter("fkCycle"));
            String name = request.getParameter("fkCurriculum");
            //String coordinatore = request.getParameter("fkProfessor");
            
            Curriculumcic curr=new Curriculumcic();
            curr.setfkCycle(number);
            curr.setfkCurriculum(name);
            //curr.setfkProfessor(coordinatore);
             
            JSONObject result = new JSONObject();
            try {
                ArrayList<Professor> prof = CurriculumcicManager.getInstance().viewProfessorList(curr);
                JSONArray resultArray = new JSONArray(prof);
                result.put("prof", resultArray);
                out.write(result.toString());
            } catch (ClassNotFoundException | SQLException | JSONException ex) {
                Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (CurriculumcicException ex) {
            Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdException ex) {
            Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NameException ex) {
            Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (CurriculumcicException ex) {
            Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdException ex) {
            Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NameException ex) {
            Logger.getLogger(ViewProfessorListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
