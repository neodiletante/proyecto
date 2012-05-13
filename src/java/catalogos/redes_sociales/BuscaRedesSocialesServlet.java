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
 * @author maria
 */
public class BuscaRedesSocialesServlet extends HttpServlet {

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
    
    System.out.println("En el servlet busca redes sociales");
    HttpSession session = request.getSession();
    session.removeAttribute("listaRedes");
    Connection con = (Connection) session.getAttribute("conn");
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    RedSocialReg rsr = new RedSocialReg();
    String idGrupoString = request.getParameter("grupo");
    System.out.println("id grupo " + idGrupoString);
    int idGrupo = Integer.parseInt(idGrupoString);
    String noListaRefiereString = request.getParameter("no_lista_refiere");
    int noListaRefiere = Integer.parseInt(noListaRefiereString);
    rsr.setIdGrupo(idGrupo);
    rsr.setNoListaRefiere(noListaRefiere);
    System.out.println("id grupo " + rsr.getIdGrupo());
    System.out.println("no lista refiere " + rsr.getNoListaRefiere());
    //List listaRedes = rsDAO.buscaDatosRedes(rsr);
    List<RedSocialDatos> listaRedes = rsDAO.buscaDatosRedes(rsr);
    String modo = request.getParameter("modo");
    System.out.println("Modo buscaRedes "+ modo);
    
    if ( "combo".equals(modo) ){
      out.println("<select id='sel-red'>");
      out.println("<option value='' selected='true'>Red</option>");
      for(RedSocialDatos rsd : listaRedes){
        out.println("<option value='" +rsd.getIdRed() + "'>" + rsd.getIdRed() + "</option>");    
      }
      out.println("</select>");
     
    }
    
    if( "lista".equalsIgnoreCase(modo) ){
      out.println("<span id='lista-alumnos'>");
      out.println("<hr>");
      out.println("<br />");
      out.println("<table id='tabla-redes'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Redes actuales</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      out.println("<th>Id Red</th>");
      out.println("<th>No. personas</th>");
      out.println("<th>Integrantes</th>");
      out.println("<th>Modificar</th>");
      out.println("<th>Borrar</th>");
      out.println("<th>No. lista referido</th>");
      out.println("</tr>");
      if(listaRedes!=null && listaRedes.size()>0){
          for(RedSocialDatos rsd : listaRedes){
            List<String> red=rsDAO.buscaElementosRedColor( rsd.getIdRed(), idGrupo );
            out.println("<tr>");
            out.println("<td class='resultado' id='id-red'>" + rsd.getIdRed() + "</td>");
            out.println("<td class='resultado' id='no-personas'>" + red.size() + "</td>");
            out.println("<td><b>");
            if(red!=null)
               for( int i=0; i<red.size(); i++ ){
                 out.print("<span style=\"color:"+red.get(i).split("_")[1]+";font-size:1.5em;\">"+red.get(i).split("_")[0]+" "+"</span>");
               }
            else  out.println("</b></td>");
            out.println("</td>");
            out.println("<td class='centrado'>");
            out.println("<input class='radio_red' type='radio' name='modificar' id='modificar_red' value='"+ rsd.getIdRed() + "'/>");
            out.println("</td>");
            if(!rsd.isTieneDatos()){
                out.println("<td class='centrado'>");
                out.println("<input class='check_red' type='checkbox' name='borrar' id='borrar_red' value='"+ rsd.getIdRed() + "'/>");
                out.println("</td>");
            }
            else{
                out.println("<td>&nbsp;</td>");
            }
            out.println(" <td class='resultado' id='no-lista-referido'>" + rsd.getNoListaReferido() + "</td>");
            out.println("</tr>");
          }//for
      }//if contiene redes
      else{
        out.println("<tr>");
        out.println("<td> No se encontraron redes </td>");
        out.println("</tr>");
      }
      out.println("</span>");
    }
    
    if ( "select".equals(modo) ){
      out.println("<option value=\"Nueva red\">Nueva red</option>");
      for(RedSocialDatos rsd : listaRedes){
        out.println("<option value=\""+rsd.getIdRed()+"\">"+rsd.getIdRed()+"</option>");
      }
      out.flush();
    }
    
    out.close();
  }
  

  
  public void pintaLista(){
  
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
