/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.cortes;

import catalogos.datos_interes.DatoInteres;
import catalogos.datos_interes.DatosInteresDAO;
import catalogos.grupos.GruposDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ulises
 */
public class BuscaCortesServlet extends HttpServlet {

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
    //response.setContentType("text/html;charset=UTF-8");
    //PrintWriter out = response.getWriter();
      HttpSession session = request.getSession();
      Connection  conn = (Connection) session.getAttribute("conn");
      GruposDAO gDAO = new GruposDAO(conn);
      List cortes = gDAO.consultaCortes();
     // session.removeAttribute("cortes");
      request.setAttribute("cortes", cortes);
         DatosInteresDAO dDAO = new DatosInteresDAO(conn);
     List<DatoInteres> datosInteres = dDAO.buscaDatosInteres();
     System.out.println("Datos de interés " + datosInteres.size());
     for (int i = 0 ; i < datosInteres.size() ; i++){
       System.out.println(datosInteres.get(i).getDescripcion());
     }
     request.setAttribute("datosInteres", datosInteres);
    
     // session.removeAttribute("referidos");
      String url = request.getParameter("url");
      String modo = request.getParameter("modo");
      url += "?modo="+modo;
      System.out.println(url);
      RequestDispatcher vista = request.getRequestDispatcher(url);
      vista.forward(request, response);
    
   // try {
    
   // } finally {      
   //   out.close();
   // }
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
