/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

import catalogos.redes_sociales.RedSocialReg;
import catalogos.redes_sociales.RedesSocialesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
public class MostrarAlumnoEnRedesServlet extends HttpServlet {

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
    List<RedSocialReg> redes = new ArrayList<RedSocialReg>();
    Connection con = (Connection) session.getAttribute("conn");
    AlumnosDAO aDAO = new AlumnosDAO(con);
    String opcion = request.getParameter("opcion");
    String noExpString = request.getParameter("no_exp");
    String idGrupoStr = (request.getParameter("id_grupo")).trim();
    System.out.println("-----------------------" + idGrupoStr  + "--------------------");
    int idGrupo = "".equals(idGrupoStr)?0:Integer.parseInt(idGrupoStr);
    int noExpediente = "".equals(noExpString)?0:Integer.parseInt(noExpString);
     System.out.println("El num de exp " + noExpediente);
    String corteStr = request.getParameter("corte");
    int corte = "".equals(corteStr)?0:Integer.parseInt(corteStr);
    redes = aDAO.buscaAlumnoEnRedes(noExpediente, corte, opcion);
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
   
    try {

      out.println("<table id='tabla-alumno-en-redes'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Alumno en Redes</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      if(!"refiere".equals(opcion)){
        out.println("<th>No. lista reporta</th>");
      }
      out.println("<th>Id Red</th>");
      out.println("<th>Integrantes</th>");
      //out.println("<th><input type='button'  id='borrar-red' class='boton' value='Borrar' /></th>");
      out.println("<th><button class='ui-button' id='btn-borrar-red'>Borrar</button></th>");
      out.println("</tr>");
      
      for (RedSocialReg red : redes){
        
        out.println("<tr>"); 
        if(!"refiere".equals(opcion)){
          out.println("<td class='resultado'>" + red.getNoListaRefiere() + "</td>");
        }
        out.println("<td class='resultado'>" + red.getIdRed() + "</td>");
        out.println("<td>");
        List<String> integrantes = rsDAO.buscaElementosRedColor(red.getIdRed(), idGrupo);
        //List<AlumnoEnRedes> integrantes = aDAO.buscaIntegrantesRed(red.getIdRed());
        for(String a : integrantes){
         // out.print("<span style=\"color:"+a.getColor()+";font-size:1.5em;\">"+a.getNoLista()+" "+"</span>");
          out.println("<span style=\"color:"+a.split("_")[1]+";font-size:1.5em;\">"+a.split("_")[0]+" "+"</span>");
        }
        out.println("</td>");
        out.println("<td> <input type='checkbox' class='check_borra_red' name='sel_red_borrar'  value=" + red.getIdRed() +"/></td>");
        out.println("</tr>");
      }
      
      out.println("</tbody>");
      
      out.println("</table>");
      out.println("<button class='ui-button' id='btn-red'>Borrar</button>");
      
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
