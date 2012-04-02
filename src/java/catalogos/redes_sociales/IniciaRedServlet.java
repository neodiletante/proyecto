/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/**
 *
 * @author ulises
 */
public class IniciaRedServlet extends HttpServlet {

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
    System.out.println("En el servlet de inicia red");
   
   // Red obj =  gson.fromJson(json,Red.class);
   //  System.out.println("Esto es lo que sale " + obj);
	
    HttpSession session = request.getSession();
    Connection con = (Connection) session.getAttribute("conn");
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    String idGrupo = request.getParameter("id_grupo");
    String noListaRefiere = request.getParameter("no_lista_refiere");
    String noListaReferido = request.getParameter("no_lista_referido");
    String red = request.getParameter("red");
    System.out.println("Toda la red " + red);
    List<String> redSocial = new ArrayList<String>();
    List<Integer> redSocialInt = new ArrayList<Integer>();
    StringTokenizer tokens = new StringTokenizer(red,",");
    while(tokens.hasMoreTokens()){
      String elemento = tokens.nextToken();
      System.out.println("elemento " + elemento);
      redSocial.add(elemento);
      redSocialInt.add(Integer.parseInt(elemento));
    }
    
    System.out.println("No lista referido " + noListaReferido );
    RedSocialReg rsr = new RedSocialReg(idGrupo,noListaRefiere, noListaReferido);
    rsDAO.insertaRedSocial(rsr);
    rsDAO.insertaElementosRed(redSocialInt);
    session.removeAttribute("lista");
    
    try {
      out.write("echo");
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

class red{
  public String elemento;
  
 
}