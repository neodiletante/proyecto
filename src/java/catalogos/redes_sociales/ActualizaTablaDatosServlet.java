/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ulises
 */
public class ActualizaTablaDatosServlet extends HttpServlet {

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
    String idRed = request.getParameter("id_red");
    Connection con  = (Connection) session.getAttribute("conn");
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    List<RedSocialDatos> datosPorRed = rsDAO.buscaDatosPorRed(Integer.parseInt(idRed));
   
    try {
      out.println("<table id='tabla-datos'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Datos actuales</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      out.println("<th>No. lista referido</th>");
      out.println("<th>Dato Interés</th>");
      out.println("<th>Borrar</th>");
      out.println("</tr>");
      for(RedSocialDatos datos : datosPorRed){
        out.println("<tr>");
        out.println("<td class='resultado' id='td-no-lista-referido'>"+ datos.getNoListaReferido() + "</td>");
        out.println("<td class='resultado' id='td-dato-interes'>"+ datos.getDescDatoInteres() +"</td>");
        out.println("<td class='centrado'>");
        out.println("<input class='check_datos' type='checkbox' name='borrar'  value='"+ datos.getIdRelacion()+"-"+datos.getIdDato() +"'/>");
        out.println("</td>");
        out.println("</tr>");
      }
      out.println("</tbody>");
      out.println("</table>");
      
      
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
