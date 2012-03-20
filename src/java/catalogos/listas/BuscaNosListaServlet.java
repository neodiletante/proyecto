/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.listas;

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
 * @author ulises
 */
public class BuscaNosListaServlet extends HttpServlet {

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
     
      List lista = listaDAO.getDatos(Integer.parseInt(grupo));
     /*
      List lista = new ArrayList();
      lista.add(new ListasDAO(1));
      lista.add(new ListasDAO(2));
      lista.add(new ListasDAO(3));
      lista.add(new ListasDAO(4));
      lista.add(new ListasDAO(5));
    */
     // System.out.println(lista);
      
      if(lista!=null){
      session.setAttribute("lista", lista);
      System.out.println("Elementos lista " + lista.size());
      }else{
      session.removeAttribute("lista");
      System.out.println("Nop hay elementos en la lista");
      }
      
    try {
     out.write("CbNDO");
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
