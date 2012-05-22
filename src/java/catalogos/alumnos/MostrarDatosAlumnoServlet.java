/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maria
 */
@WebServlet(name = "MostrarDatosAlumno", urlPatterns = {"/mostrarDatosAlumno"})
public class MostrarDatosAlumnoServlet extends HttpServlet {

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
    String noExpString = request.getParameter("no_exp");
   
    int noExpediente = "".equals(noExpString)?0:Integer.parseInt(noExpString);
     System.out.println("El num de exp " + noExpediente);
    String corteStr = request.getParameter("corte");
    int corte = "".equals(corteStr)?0:Integer.parseInt(corteStr);
     AlumnosDAO aDAO = new AlumnosDAO(con);
   
     Alumno alumno = new AlumnoEnRedes();
     alumno =  aDAO.buscarAlumno(noExpediente);
   
   
    AlumnoEnRedes alumnoEnRedes = aDAO.buscaDatos(noExpediente, corte);
    if(alumnoEnRedes!=null){
    alumnoEnRedes.setNoExpediente(alumno.getNoExpediente());
    alumnoEnRedes.setNombre(alumno.getNombre());
    alumnoEnRedes.setSexo(alumno.getSexo());
    //request.setAttribute("alumno", alumnoEnRedes);
    }
    //request.setAttribute("alumno", alumno);
    try {   
      out.print("<div id='info-alumno'>");
      out.print("<p class='etiqueta'><label>"+alumnoEnRedes.getNoExpediente()+"</label>");
      out.print("<p class='etiqueta'><label>"+alumnoEnRedes.getNombre()+"</label>");
      out.print("<p class='etiqueta'><label>"+alumnoEnRedes.getSexo()+"</label>");
      out.print("<p class='etiqueta'><label>"+alumnoEnRedes.getGrupo()+"</label>");
      out.print("<p class='etiqueta'><label>"+alumnoEnRedes.getNoLista()+"</label>");
      out.print("</div>");
      
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
