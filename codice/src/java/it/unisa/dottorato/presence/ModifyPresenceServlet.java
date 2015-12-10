/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.dottorato.presence;
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

/**
 *
 * @author Rembor
 */
@WebServlet(name = "ModifyPresence", urlPatterns = {"/ModifyPresence"})
public class ModifyPresenceServlet extends HttpServlet{
    
     protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        try {

            
            boolean newsignature = Boolean.parseBoolean(request.getParameter("newSignature"));
            String fkPhdstudent = request.getParameter("fkPhdstudent");
            String fkLesson = request.getParameter("FkLesson");
            Presence aPresence =new Presence();
            aPresence.getFkLesson();
            aPresence.getFkPhdstudent();
            aPresence.isIsPresent();
         
            PresenceManager.getInstance().modifyPresence(newsignature,aPresence);
            result.put("result", true);
            out.write(result.toString());
        }catch (SQLException ex) {
                result.put("result", false);
                Logger.getLogger(ModifyPresenceServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
   
         

         catch (JSONException ex) {
            Logger.getLogger(ModifyPresenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
           finally {
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
         } catch (JSONException ex) {
             Logger.getLogger(ModifyPresenceServlet.class.getName()).log(Level.SEVERE, null, ex);
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
         } catch (JSONException ex) {
             Logger.getLogger(ModifyPresenceServlet.class.getName()).log(Level.SEVERE, null, ex);
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