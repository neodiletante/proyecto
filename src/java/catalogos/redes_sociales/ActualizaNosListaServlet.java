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
     // Connection conn = (Connection) session.getAttribute("conn");
      ListasDAO listaDAO = new ListasDAO(session);
      String grupo = request.getParameter("grupo");
      String formato = request.getParameter("formato");
     System.out.println("grupo " + grupo);
      List lista = listaDAO.getDatos(Integer.parseInt(grupo));
     if(lista == null){
       System.out.println("Lista null");
     }else{
       System.out.println("tamaño lista " + lista.size());
     }
      // System.out.println(lista.size());
     
    try {

      out.println("<span id='lista-alumnos'>");
      
      int noLista;
      
     System.out.println("formato + " + formato);
     out.println("<hr>");
      out.println("<br />");
 //     if ("matriz".equals(formato)){
        
      int tamanioLista = lista.size();
      System.out.println("Tamaño lista " + tamanioLista);
     
     int columnas = (int) Math.sqrt(tamanioLista) +1;
     System.out.println("Columnas " + columnas);
      
      for(int i=0, cuenta=1 ; i<tamanioLista ; i++, cuenta++){
        
        noLista = ((ListasDAO)lista.get(i)).getNo_lista();
        out.println("<label class='h4'>" + noLista + "</label>");
        out.println("<input class='check-red-social' type='checkbox' name='agrega_alumno'  value='"+ noLista  +"'/>");
        out.println("<input class='radio-referido' type='radio' name='referido'  value='" + noLista + "'/>");
        out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
        if(cuenta==columnas){
          out.println("<br />");
          cuenta=0;
        }
      }
     
  
      
     // }else{
        
     // }
 out.println("</span>");
      out.println("<br />");
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
