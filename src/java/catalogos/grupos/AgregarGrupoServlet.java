/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.grupos;

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
public class AgregarGrupoServlet extends HttpServlet {

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
        PrintWriter out = null;
        String grado = request.getParameter("grado");
        String grupo = request.getParameter("grupo");
        String turno = request.getParameter("turno");
        String corte = request.getParameter("corte");
        /*
        System.out.println("grado " + grado);
        System.out.println("grupo " + grupo);
        System.out.println("turno " + turno);
        System.out.println("corte " + corte);
        
        */
        Grupo grupoInsert = new Grupo(grado,grupo,turno,corte);
     
        HttpSession session = request.getSession();
        
        Connection conect = (Connection) session.getAttribute("conn");
        GruposDAO gDAO = new GruposDAO(conect);
        gDAO.insertarGrupo(grupoInsert);
        List<Grupo> grupos = gDAO.consultaGrupos(Integer.parseInt(corte));
        MuestraTablaGrupos muestra = new MuestraTablaGrupos();
        out = muestra.getTabla(response, grupos);
        out.close();
      //  RequestDispatcher view = request.getRequestDispatcher("grupos");
       // view.forward(request, response);
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