/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.datos_interes;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
public class AgregaDatoInteresServlet extends HttpServlet {

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
    PrintWriter out = response.getWriter(); 
    System.out.println("en el servlet de agregar dato de interes");
    String descripcion = request.getParameter("descripcion");
    System.out.println(descripcion);
    DatoInteres datoInteresAgregado = new DatoInteres(descripcion);
    HttpSession session = request.getSession();
    Connection conect = (Connection) session.getAttribute("conn");
    DatosInteresDAO dDAO = new DatosInteresDAO(conect);
    int status = dDAO.insertaDatoInteres(datoInteresAgregado);
    if(status>0)
        out.println("Alumno agregado");
    else
        out.println("Hubo un problema agregando");
    //RequestDispatcher rd = request.getRequestDispatcher("Catalogos/Datos_interes/datos_interes.jsp");
    //rd.forward(request, response);
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