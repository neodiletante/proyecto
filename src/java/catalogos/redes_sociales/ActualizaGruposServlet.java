/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import catalogos.grupos.Grupo;
import catalogos.grupos.GruposDAO;
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
 * @author ulises
 */
public class ActualizaGruposServlet extends HttpServlet {

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
    String corte = (String) request.getParameter("corte");
    String turno = (String) request.getParameter("turno");
    System.out.println("corte y turno " + corte + " " + turno);   
    //HttpSession session = request.getSession();
    Connection conn = (Connection) session.getAttribute("conn");
    GruposDAO gDAO = new GruposDAO(conn);
    session.removeAttribute("grupos");
    session.removeAttribute("lista");
    session.removeAttribute("datosInteres");
    session.removeAttribute("datosPorRed");
    List<Grupo> grupos = gDAO.buscaGruposPorTurno(Integer.parseInt(corte), turno);
    session.setAttribute("grupos", grupos);
    try {
      /*
       * TODO output your page here. You may use following sample code.
       */
    out.println("<select id='select-grupos'>");
    out.println("<option value='' selected='true'>Grupo</option>");
    for(Grupo grupo : grupos){
      out.println("<option value='" + grupo.getIdGrupo() + "'>" + grupo.getGrado() + " ° " + grupo.getGrupo() + " " + grupo.getTurno() + "</option>");    
    }
    out.println("</select>");
   
   
    
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
