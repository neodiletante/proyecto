/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maria
 */
public class ModificaRedesSocialesServlet extends HttpServlet {

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
      HttpSession session = request.getSession();
      Connection con = (Connection) session.getAttribute("conn");
      RedesSocialesDAO rsDAO = new RedesSocialesDAO(con);
      String idGrupo = request.getParameter("id_grupo");
      String noListaRefiere = request.getParameter("no_lista_refiere");
      String noListaReferido = request.getParameter("no_lista_referido");
      String elementosRed = request.getParameter("red");
      String idRed = request.getParameter("id_red");
      int idRedInt = Integer.parseInt(idRed);
      List<Integer> redSocial = new ArrayList<Integer>();
      StringTokenizer tokens = new StringTokenizer(elementosRed,",");
      while(tokens.hasMoreTokens()){
        String elemento = tokens.nextToken();
        System.out.println("elemento " + elemento);
        int elementoRed = Integer.parseInt(elemento);
        redSocial.add(elementoRed);
      }
      
      RedSocialReg rsr = new RedSocialReg(idGrupo,noListaRefiere, noListaReferido);
      rsDAO.modificaRedSocialReg(rsr);
      rsDAO.modificaElementosRed(idRedInt, redSocial);
      
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
