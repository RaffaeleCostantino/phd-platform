
package it.unisa.dottorato.presence;


import it.unisa.dottorato.phdCourse.CalendarManager;
import it.unisa.dottorato.phdCourse.Course;
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

/**
 *
 * @author matteo
 */
@WebServlet(name = "GetCourseByProfessor", urlPatterns = {"/GetCourseByProfessor"})
public class GetCourseByProfessorServlet extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException, PhdStudentexception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<Course> course =null;
        try (PrintWriter out = response.getWriter()) {
            String Professor = request.getParameter("fkProfessor");
            JSONObject result = new JSONObject();
            
            try { 
                course= PresenceManager.getInstance().getCourseByProfessor(Professor);
                JSONArray resultArray = new JSONArray(course);
                result.put("CorsiProfessor", resultArray);
                out.write(result.toString());
            }  catch (JSONException ex) {
                Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PhdStudentexception ex) {
            Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PhdStudentexception ex) {
            Logger.getLogger(GetCourseByProfessorServlet.class.getName()).log(Level.SEVERE, null, ex);
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
