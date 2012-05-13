/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

//import catalogos.datos_interes.DatosInteresDAO;
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
    String modo = request.getParameter("modo");
    int idRedInt = (idRed!=null&&idRed.length()>0)?Integer.parseInt(idRed):0;
    Connection con  = (Connection) session.getAttribute("conn");
    RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
    List<RedSocialDatos> datosRed = rsDAO.buscaDatosPorRed(idRedInt);
    List<Integer> elementosConDatos= new ArrayList<Integer>();
    for(RedSocialDatos rsd : datosRed){
      elementosConDatos.add(rsd.getNoListaReferido());
    }
    List<Integer> elementosRed = rsDAO.buscaElementosRed(idRedInt);
    try{
      if ("combo".equals(modo)){
      
      out.println("<select id='sel-nos-lista'>");
      out.println("<option value='' selected='true'>Elementos de la red</option>");
      for(Integer elemento : elementosRed){
        out.println("<option value='" + elemento +"'> " + elemento + " </option>");
      }
      out.println("</select>");
      }else{
        int referido = rsDAO.buscaReferidoRed(idRedInt);
     
    String elementos = "";
    for(int i=0 ; i<elementosRed.size() ; i++){
      
      elementos += elementosRed.get(i);
      if(elementosConDatos.contains(elementosRed.get(i))){
        elementos += "d";
      }
      
      //if(i+1<elementosRed.size()){
        elementos += ",";
     // }
        System.out.println("elementos:"+elementos);
    }
    elementos += "-" + referido;
    out.print(elementos);
      }
    }finally{
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
