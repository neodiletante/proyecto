/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import catalogos.listas.ListasDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ActualizaNosListaServlet extends HttpServlet {

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
      ListasDAO listaDAO = new ListasDAO(session);
      String grupo = request.getParameter("grupo");
      System.out.println("grupo " + grupo);
      int noLista=0;

      List lista = listaDAO.getDatos(Integer.parseInt(grupo));
    
    try {
      out.println("<span id='lista-alumnos' width=\"100%\" align=\"left\">");
      out.println("<hr>");
      out.println("<br /><pre width=\"100%\">");
      int tamanioLista = lista!=null?lista.size():0;
      int columnas = (int) Math.sqrt(tamanioLista) +1;
      int ultimaFila=0;
      ListasDAO lDao=null;
      for(int i=0, cuenta=1 ; i<tamanioLista ; i++, cuenta++){
        lDao=((ListasDAO)lista.get(i));
        if(lDao!=null){
           out.print("<span align=\"left\" style=\"font-size:2em;color:"+lDao.getColor()+"\">" + 
                    (lDao.getNo_lista()<=9?"&nbsp;":"") + lDao.getNo_lista() );
           out.print("<input class='check-red-social' type='checkbox' name='agrega_alumno'  value='"+ lDao.getNo_lista()  +"'/>");
           out.print("<input class='radio-referido' type='radio' name='referido'  value='" + lDao.getNo_lista() + "'/>");
           out.print("&nbsp;&nbsp;&nbsp;&nbsp;</span>");
        }
        if(cuenta==columnas){
          out.println("&nbsp;");
          cuenta=0;
        }
        out.flush();
        lDao=null;
        ultimaFila=cuenta;
      }
      if(ultimaFila<columnas)
          for(int i=ultimaFila; i<=columnas; i++ ){
              out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
          }
          
     
      out.println("</pre></span>");
      out.println("<br />");
   }
    finally {      
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
