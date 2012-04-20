/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

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
 * @author maria
 */
public class BuscaAlumnosServlet extends HttpServlet {

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
    System.out.println("En el servlet busca alumnos");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    Connection con = (Connection) session.getAttribute("conn");
    String busca = request.getParameter("nombre");
    AlumnosDAO aDAO = new AlumnosDAO(con);
    List<Alumno> alumnos = aDAO.consultaAlumnos((busca!=null)?busca:"");
    //List<Alumno> alumnos = aDAO.consultaAlumnos("homero");
    //List<Alumno> alumnos = aDAO.buscaPosicionAlumnos();
    System.out.println("Número de alumnos " + alumnos.size());
    int alumnosPorTab = 10;
    int tamanioLista = alumnos.size();
    int numTabs = tamanioLista%alumnosPorTab == 0 ? tamanioLista / alumnosPorTab : tamanioLista / alumnosPorTab + 1;
    int cuentaAlumnos = 0;
    try{
    out.println("<div id='tabs'>");
    out.println("<ul>");
    for(int i=0 ; i<numTabs ; i++){
      out.println("<li><a href='#tabs-" + i+1 + "'></a>"+ alumnos.get(i*alumnosPorTab).getNoExpediente() + " - " + ((i+1<numTabs) ? alumnos.get(alumnosPorTab*(i+1)-1).getNoExpediente() : alumnos.get(alumnos.size()-1).getNoExpediente())+ "</li>");
    }
    out.println("</ul>");
    for(int i=0 ; i<numTabs ; i++){
      
      out.println("<div id='tabs-" + i+1 + "'>");
      out.println("<table id='tabla-alumnos-actuales-" + i+1 + "' class='tabla-resultado'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Alumnos actuales</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      out.println("<th>No. Expediente</th>");
      out.println("<th>Nombre</th>");
      out.println("<th>Sexo</th>");
      out.println("<th>Borrar</th>");
      out.println("<th>Modificar</th>");
      out.println("</tr>");
      for(int j=0; j<alumnosPorTab ; j++){
        if(cuentaAlumnos < alumnos.size()){
        Alumno alumno = alumnos.get(cuentaAlumnos++);
        out.println("<tr>");
        out.println("<td class='resultado' id='input_noExpediente'>" + alumno.getNoExpediente() + "</td>");
        out.println("<td class='resultado' id='input_nombre'>" + alumno.getNombre() + "</td>");
        out.println("<td class='resultado' id='input_sexo'>" + alumno.getSexo() + "</td>");
        if(alumno.isTieneRegistros()){
        out.println("<td></td>");
        
        }else{
          out.println("<td class='centrado'>");
          out.println("<input class='check_alumno' type='checkbox' name='borrar'  value='" + alumno.getNoExpediente() + "'/>");
        }
        
        out.println("<td class='centrado'>");
        out.println("<input class='radio_alumno' type='radio' name='modificar' value='" + alumno.getNoExpediente() + "'/>");
        out.println("</td>");
        out.println("</tr>");
      }else{
        break;  
      }
      }
      out.println("</tbody>");
        out.println("</table>");
 
      out.println("</div>");
    }
   
    }finally{
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


