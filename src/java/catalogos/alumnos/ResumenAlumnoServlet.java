/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

import catalogos.redes_sociales.RedSocialDatos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maria
 */
public class ResumenAlumnoServlet extends HttpServlet {

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
    System.out.println("Servlet Resumen Alumno");
    HttpSession session = request.getSession();
    Connection con = (Connection) session.getAttribute("conn");
    String noExpString = request.getParameter("no_exp");
   
    int noExpediente = "".equals(noExpString)?0:Integer.parseInt(noExpString);
     System.out.println("El num de exp " + noExpediente);
    //String corteStr = request.getParameter("corte");
    //int corte = "".equals(corteStr)?0:Integer.parseInt(corteStr);
     AlumnosDAO aDAO = new AlumnosDAO(con);
     Alumno  alumno =  aDAO.buscarAlumno(noExpediente);
     //alumno.setNombre("Pepe");
     request.setAttribute("alumno", alumno);
    //RequestDispatcher rd = request.getRequestDispatcher("Catalogos/Alumnos/resumen_alumno.jsp");
    //rd.forward(request, response);
    
    
    try {
      /*
       * TODO output your page here. You may use following sample code.
       */
      out.println("<h1>Datos del Alumno</h1>");
      out.println("<label class='etiqueta-resumen'>No. Expediente: </label>");
      out.println("<label class='resultado-resumen'>"+alumno.getNoExpediente()+"</label><br />" );
      out.println("<label class='etiqueta-resumen'>Nombre: </label>");
      out.println("<label class='resultado-resumen'>"+alumno.getNombre()+"</label><br />" );
      out.println("<label class='etiqueta-resumen'>Sexo: </label>");
      out.println("<label class='resultado-resumen'>"+alumno.getSexo()+"</label><br />" );
      out.println("<h1>Datos por corte</h1>");
      List<AlumnoEnRedes> datos = aDAO.buscaDatosResumen(alumno.getNoExpediente());
      for(AlumnoEnRedes datosAlumno : datos){
        Map<String,Integer> cuentas = aDAO.buscaCuentaRedes(datosAlumno.getIdGRupo(),datosAlumno.getNoLista()   );
        out.println("<label class='etiqueta-resumen'>Corte: </label>");
      out.println("<label class='resultado-resumen'>"+datosAlumno.getCorte()+"</label>" );
        out.println("<table id='tabla-datos-por-corte'>");
          out.println("<thead>");
          out.println("<th colspan='7'>Datos por corte</th>");
          out.println("</thead>");
          out.println("<tbody>");
          out.println("<tr>");
          out.println("<th>Grupo</th>");
          out.println("<th>No. lista</th>");
          out.println("<th>Gpo. est.</th>");
          out.println("<th>Redes reporta</th>");
          out.println("<th>Redes referido</th>");
          out.println("<th>Redes participa</th>");
          out.println("<th>Datos de interés</th>");
          out.println("</tr>");
          out.println("<tr>");
   
          out.println("<td class='resultado'>" +datosAlumno.getGrupo()+ "</td>");
            out.println("<td class='resultado'>" +datosAlumno.getNoLista()+ "</td>");
            
            out.println("<td class='resultado'>" +datosAlumno.getGpoEstadistico()+ "</td>");
            out.println("<td class='resultado'>" +cuentas.get("reporta")+ "</td>");
            out.println("<td class='resultado'>" +cuentas.get("referido")+ "</td>");
            out.println("<td class='resultado'>" +cuentas.get("participa")+ "</td>");
            List<RedSocialDatos> datosInteres = aDAO.buscaDatosInteresResumen(alumno.getNoExpediente(), datosAlumno.getIdGRupo());
            String tieneDatosInteres = datosInteres.size()>0 ? "Si" : "No";
            out.println("<td class='resultado'>" +tieneDatosInteres+ "</td>");
            out.println("</tr>");
        //out.println("<label class='etiqueta-resumen'>Corte: </label>");
        //out.println("<label class='resultado-resumen'>"+datosAlumno.getCorte()+"</label><br />" );
        //out.println("<label class='etiqueta-resumen'>No. Lista: </label>");
        //out.println("<label class='resultado-resumen'>"+datosAlumno.getNoLista()+"</label><br />" );
        //out.println("<label class='etiqueta-resumen'>Gpo. est.: </label>");
        //out.println("<label class='resultado-resumen'>"+datosAlumno.getGpoEstadistico()+"</label><br />" );
        //out.println("<label class='etiqueta-resumen'>Redes reporta: </label>");
        //out.println("<label class='resultado-resumen'>"+cuentas.get("reporta")+"</label><br />" );
        //out.println("<label class='etiqueta-resumen'>Redes refiere: </label>");
        //out.println("<label class='resultado-resumen'>"+cuentas.get("refiere") +"</label><br />" );
        //out.println("<label class='etiqueta-resumen'>Redes participa: </label>");
        //out.println("<label class='resultado-resumen'>"+cuentas.get("participa")+"</label><br />" );
        //out.println("<label class='etiqueta-resumen'>Datos de interés: </label>");
        //List<RedSocialDatos> datosInteres = aDAO.buscaDatosInteresResumen(alumno.getNoExpediente());
        //String tieneDatosInteres = datosInteres.size()>0 ? "Si" : "No"; 
        //out.println("<label class='resultado-resumen'>"+ tieneDatosInteres+"</label><br />" );
        if(datosInteres.size()>0){
          out.println("<table id='tabla-datos-interes-resumen'>");
          out.println("<thead>");
          out.println("<th colspan='6'>Datos de interés</th>");
          out.println("</thead>");
          out.println("<tbody>");
          out.println("<tr>");
          out.println("<th>Dato</th>");
          out.println("<th>Referido</th>");
          out.println("<th>Refiere</th>");
          out.println("<th>Red</th>");
          out.println("</tr>");
          for(RedSocialDatos rsd : datosInteres){
            out.println("<tr>");
            out.println("<td class='resultado' id='td_no_exp'>" +rsd.getDescDatoInteres()+ "</td>");
            out.println("<td class='resultado'>" +rsd.getNoListaReferido()+ "</td>");
            out.println("<td class='resultado'>" +rsd.getNoListaRefiere()+ "</td>");
            out.println("<td class='resultado'>" +rsd.getIdRed()+ "</td>");
            out.println("</tr>");
          }
          out.println("</tbody>");
          out.println("</table>");
        }
        
      }
  //    out.println("</span>");
      
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
