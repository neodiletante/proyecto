/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ulises
 */
public class BorraRedesServlet extends HttpServlet {

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
      System.out.println("En el servlet de borra redes");
      HttpSession  session = request.getSession();
      Connection con = (Connection) session.getAttribute("conn");
      PrintWriter out = response.getWriter();
      try {
         con.setAutoCommit(true);
      } catch (SQLException ex) {
          Logger.getLogger(BorraRedesServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    String idRedes = request.getParameter("id_redes");
    String[] redes = idRedes.split(",");
    List<Integer> idsRedesBorrar = new ArrayList<Integer>();
    Integer red;
    for(int i = 0 ; i< redes.length ; i++){
      if (redes[i].trim().length()>0)  {
          red = Integer.valueOf(redes[i]);
          System.out.println("Borrando " + red);
          idsRedesBorrar.add(red);
      }
    }
    
    rsDAO.borraRedesSociales(idsRedesBorrar);
    out.println("terminado");
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
