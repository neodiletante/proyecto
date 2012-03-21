/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import catalogos.datos_interes.DatoInteres;
import catalogos.datos_interes.DatosInteresDAO;
//import catalogos.datos_interes.DatosInteresDAO;
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
  
     DatosInteresDAO dDAO = new DatosInteresDAO(con);
     List<DatoInteres> datosInteres = dDAO.buscaDatosInteres();
     System.out.println("Datos de interés " + datosInteres.size());
     for (int i = 0 ; i < datosInteres.size() ; i++){
       System.out.println(datosInteres.get(i).getDescripcion());
     }
     session.setAttribute("datosInteres", datosInteres);

    String elementos = "";
    for(int i=0 ; i<elementosRed.size() ; i++){
      elementos += elementosRed.get(i);
      if(i+1<elementosRed.size()){
        elementos += ",";
      }
    }
    System.out.println(elementos);
    session.setAttribute("nosLista", elementosRed);
    
    List<RedSocialDatos> datosPorRed = rsDAO.buscaDatosPorRed(Integer.parseInt(idRed));
  session.setAttribute("datosPorRed", datosPorRed);      
    
    
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
