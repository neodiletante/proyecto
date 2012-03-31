/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.grupos;

import catalogos.grupos.Grupo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ulises
 */
public class MuestraTablaGrupos {
 
  
  public PrintWriter getTabla(HttpServletResponse response, List<Grupo> grupos){
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
      out.println("<table id='tabla-grupos-actuales'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Grupos actuales</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      out.println("<th>Grado</th>");
      out.println("<th>Grupo</th>");
      out.println("<th>Turno</th>");
      out.println("<th>Corte</th>");
      out.println("<th>Borrar</th>");
      out.println("<th>Modificar</th>");
      out.println("</tr>");
      
      for(Grupo grupo : grupos){
        out.println("<tr>");
        out.println("<td class='resultado' id='input_grado'>" + grupo.getGrado() + "</td>");
        out.println("<td class='resultado' id='input_grupo'>" + grupo.getGrupo() + "</td>");
        out.println("<td class='resultado' id='input_turno'>" + grupo.getTurno() + "</td>");
        out.println("<td class='resultado' id='input_corte'>" + grupo.getCorte() + "</td>");
        if(!grupo.isTieneAlumnos()){
           out.println("<td class='centrado'>");
           out.println("<input class='check_grupo' type='checkbox' name='corte' id='borrar' value='"+ grupo.getIdGrupo() + "'/>");
           out.println("</td>");
        }else{
           out.println("<td></td>");
        }
        out.println("<td class='centrado'>");
        out.println("<input class='radio_grupo' type='radio' name='modificar' id='modificar' value='"+ grupo.getIdGrupo() + "'/>");
        out.println("</td>");
        out.println("</tr>");
      }
      out.println("</tbody>");
      out.println("</table>");
    } catch (IOException ex) {
      Logger.getLogger(MuestraTablaGrupos.class.getName()).log(Level.SEVERE, null, ex);
    } finally{
      return out;
    }
  }
  
}
