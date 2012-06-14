/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

import catalogos.redes_sociales.RedesSocialesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;

/**
 *
 * @author maria
 */
@WebServlet(name = "BorraRedesYRelaciones", urlPatterns = {"/borraRedesYRelaciones"})
public class BorraRedesYRelacionesServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    Connection con = (Connection) session.getAttribute("conn");
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    
    String idRedes = request.getParameter("id_redes");
     String[] aRedes = idRedes.split(",");
     List<Integer> redes = new ArrayList<Integer>();
     for(int i=0 ; i < aRedes.length ; i++){
       Integer red = Integer.valueOf(aRedes[i]);
       redes.add(red);
     }
     rsDAO.borraRedesYRelaciones(redes);
     
    try {
      
    } finally {      
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
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
   * Handles the HTTP
   * <code>POST</code> method.
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
