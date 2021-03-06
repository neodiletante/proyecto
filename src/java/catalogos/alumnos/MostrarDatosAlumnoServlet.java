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
    boolean hayDatos = false;
   
    int noExpediente = "".equals(noExpString)?0:Integer.parseInt(noExpString);
     System.out.println("El num de exp " + noExpediente);
    String corteStr = request.getParameter("corte");
    int corte = "".equals(corteStr)?0:Integer.parseInt(corteStr);
     AlumnosDAO aDAO = new AlumnosDAO(con);
   
     Alumno alumno = new AlumnoEnRedes();
     alumno =  aDAO.buscarAlumno(noExpediente);
   
   
    AlumnoEnRedes alumnoEnRedes = aDAO.buscaDatos(noExpediente, corte);
    if(alumnoEnRedes == null){
      alumnoEnRedes = new AlumnoEnRedes();
    }else{
      hayDatos = true;
    }
    alumnoEnRedes.setNoExpediente(alumno.getNoExpediente());
    alumnoEnRedes.setNombre(alumno.getNombre());
    alumnoEnRedes.setSexo(alumno.getSexo());
    //request.setAttribute("alumno", alumnoEnRedes);
    
    System.out.println(alumnoEnRedes.getNombre());
    //request.setAttribute("alumno", alumno);
    try {  
      out.println("<table id='info-alumno'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Datos del alumno</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      out.println("<th>No. expediente</th>");
      out.println("<th>Nombre</th>");
      out.println("<th>Sexo</th>");
      out.println("<th>Grupo</th>");
      out.println("<th>No. lista</th>");
      out.println("<th>Corte</th>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td class='resultado' id='td_no_exp'>" +alumnoEnRedes.getNoExpediente()+ "</td>");
      out.println("<td class='resultado'>" +alumnoEnRedes.getNombre()+ "</td>");
      out.println("<td class='resultado'>" +alumnoEnRedes.getSexo()+ "</td>");
      out.println("<td class='resultado'>" +(hayDatos==true?alumnoEnRedes.getGrupo():"")+ "</td>");
      out.println("<td class='resultado'>" +(hayDatos==true?alumnoEnRedes.getNoLista():"")+ "</td>");
       out.println("<td class='resultado' id='td_corte'>" +corte+ "</td>");
      out.println("</tr>");
      out.println("</tbody>");
      out.println("</table>");
      if (!hayDatos){
        System.out.println("No hay datos");
        out.println("<p>El alumno no pertenece a ningún grupo en este corte</p>");
      }
      out.println("~"+alumnoEnRedes.getIdGRupo());
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
