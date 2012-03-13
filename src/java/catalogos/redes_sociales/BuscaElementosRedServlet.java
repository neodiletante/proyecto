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
import com.google.gson.Gson;

/**
 *
 * @author ulises
 */
public class BuscaElementosRedServlet extends HttpServlet {

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
    System.out.println("En el servlert busca elementos red");
    HttpSession session = request.getSession();
    String idRed = request.getParameter("id_red");
    Connection con  = (Connection) session.getAttribute("conn");
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    List elementosRed = rsDAO.buscaElementosRed(Integer.parseInt(idRed));
    //Gson gson = new Gson();
    //String json = gson.toJson(elementosRed);
    //System.out.println(json);
    String elementos = "";
    for(int i=0 ; i<elementosRed.size() ; i++){
      elementos += elementosRed.get(i);
      if(i<elementosRed.size()){
        elementos += ",";
      }
    }
    
    
    try {
      out.write(elementos); 
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
