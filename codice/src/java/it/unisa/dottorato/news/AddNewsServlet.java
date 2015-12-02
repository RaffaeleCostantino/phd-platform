/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.dottorato.news;

import it.unisa.integrazione.model.Person;
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
/**
 *
 * @author Giuseppe Picciocchi 
 */
@WebServlet(name = "AddNewsServlet", urlPatterns = {"/AddNewsServlet"})
public class AddNewsServlet extends HttpServlet {

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
            throws ServletException, IOException, MissingDataEccezione {

        JSONObject result = new JSONObject();
        PrintWriter out = response.getWriter();

        try {
            response.setContentType("text/html;charset=UTF-8");
            
            //conserviamo gli attributi da settare nelle variabili
            //String idNews = request.getParameter("idNews"); non serve  il  manager se ne occupa di inserire l'id delle news
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            
            HttpSession session = request.getSession();
            Person loggedPerson = (Person) session.getAttribute("person");
            
            News news = new News();
            
            //inseriamo nell'oggetto corso i valori passati come parametri precedentemente
            news.getId();// il manager se ne occupa
            news.setTitle(title);
            news.setDescription(description);
            
            //inseriamo l'oggetto nella gestione calendario
            NewsManager.getInstance().insert(news);
            
            out.println("<script type=\"text/javascript\">");
            out.println("alert('La news è stata inserita');");
            out.println("location='collaborationActivity.jsp';"); // da modificare
            out.println("</script>");
            
        } catch (SQLException ex) {
            Logger.getLogger(AddNewsServlet.class.getName()).log(Level.SEVERE, null, ex);
            
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
        } catch (MissingDataEccezione ex) {
            Logger.getLogger(AddNewsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (MissingDataEccezione ex) {
            Logger.getLogger(AddNewsServlet.class.getName()).log(Level.SEVERE, null, ex);
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