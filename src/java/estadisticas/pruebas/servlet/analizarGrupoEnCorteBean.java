/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estadisticas.pruebas.servlet;

import catalogos.grupos.Grupo;
import estadisticas.pruebas.AnalizarDatosCapturaRedes;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import utilerias.obtieneDatos;

/**
 *
 * @author daniel
 */
public class analizarGrupoEnCorteBean extends HttpServlet {
    private Connection  conn =null;
    HttpServletRequest rqt = null;
    private String nombreArchivo;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
        String turno= request.getParameter("turno");
        conn = (Connection) session.getAttribute("conn");
        rqt = request;
        try {
            if(request.getParameter("opcion")!=null){
                int opcion=Integer.parseInt(request.getParameter("opcion"));
                switch(opcion){
                    case 1:{ /* obtener corte */
                      muestraOpcionCortes(out);  
                    }break;
                    case 2:{/* obtener grupos */
                      int corte=Integer.parseInt(request.getParameter("corte"));
                      muestraOpcionGrupos(out, corte, turno);
                    }break;
                    case 3:{/*obtener detalle de redes por grupo*/
                      String id_grupo=  request.getParameter("id_grupo");
                      obtenerAnalisisRedes(out, id_grupo);
                    }break;    
                    case 4:{
                      int corte=  Integer.parseInt(request.getParameter("corte"));
                      this.generaReporteAnalisisGposPorCorte(out, corte);
                    }break;
                    case 5:{
                      int corte=  Integer.parseInt(request.getParameter("corte"));
                      analizaUnCorte(corte);
                    }
                }
            }
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void muestraOpcionCortes(PrintWriter out) {
        obtieneDatos datos = new obtieneDatos(this.conn);
        StringBuilder salida=new StringBuilder("");
        List cortes = datos.obtieneListaCortes();
        for(int i=0; i<cortes.size(); i++){
            int corte=(Integer)cortes.get(i);
            salida.append("<option value=\"").append(corte).append("\">").append(corte).append("</corte>");
        }
         out.print(salida.toString());
    }

    private void obtenerAnalisisRedes(PrintWriter out, String id_grupo) {
        /*analizar el grupo*/
        AnalizarDatosCapturaRedes analizador =new AnalizarDatosCapturaRedes(conn, 32);
        /*presentar los datos*/
        analisisGruposDAO aGrupos= new analisisGruposDAO(conn);
        RedesAnalisisGrupos analisisGrupo= aGrupos.getAnalisisGrupo(id_grupo);
        List<RedesAnalisisRedesReales> redes = aGrupos.getAnalisisRedesGrupo(id_grupo);
        StringBuilder salida=new StringBuilder("");
        RedesAnalisisRedesReales red=null;
        /**
         * Encabezado con estadisticos del grupo
         */
        salida.append("<tr><th colspan=\"4\"><table class=\"tabla\"><tr>");
        salida.append("<th>Media: </th>").append("<td>");
        salida.append(analisisGrupo.getMedia()).append("</td>");
        salida.append("<th>Prop. Media: </th><td>");
        salida.append(analisisGrupo.getProporcionMedia()).append("</td>");
        salida.append("<th>Varianza: </th><td>");
        salida.append(analisisGrupo.getVarianza()).append("</td>");
        salida.append("<th>Uniformidad: </th><td>");
        salida.append(analisisGrupo.isUniformidad()?"SI":"NO").append("</td>");
        salida.append("</tr></table></td></tr>");
        salida.append(" <tr> ");
        salida.append("     <th> red </th> ");
        salida.append("     <th>Integrantes </th> ");
        salida.append("     <th>Frecuencia </th> ");
        salida.append("     <th>Proporcion </th> ");
        salida.append(" </tr> ");
        if(redes!=null){
            for(int i=0; i<redes.size(); i++){
                red= redes.get(i);
                salida.append("<tr>");
                salida.append("<td>").append(red.getIdRedReal()).append("</td>");
                salida.append("<td>").append(red.getIntegrantes().replaceAll("_", "-")).append("</td>");
                salida.append("<td>").append(red.getFrecuencia()).append("</td>");
                salida.append("<td>").append(red.getProporcion()).append("</td>");
                salida.append("</tr>");
                red=null;
            }
        }
        else{
            salida.append("<tr><td> No hay información de analisis del grupo ").append(id_grupo).append("</td></tr>");
        }
        out.println(salida.toString());
    }
    
    private String generaReporteGruposPorCorte(int corte){
        obtieneDatos Util = new obtieneDatos(this.conn);
        HashMap parameters = new HashMap();
        parameters.put("corte", corte);
        String archivo=Util.generaReporte(rqt, "GruposPorCorte","/Archivos/Analisis/", parameters);
        //this.generaReporteAnalisisGposPorCorte(this., corte);
        return archivo;
    }

    private void muestraOpcionGrupos(PrintWriter out, int corte, String turno) {
        obtieneDatos datos = new obtieneDatos(this.conn);
        StringBuilder salida=new StringBuilder("");
        List<Grupo> grupos=datos.obtieneGruposCorte(corte, turno);
        for(int i=0; i<grupos.size(); i++){
            salida.append("<option value=\"").append(((Grupo)grupos.get(i)).getIdGrupo()).append("\">");
            salida.append(((Grupo)grupos.get(i)).getGrado()).append(" ");
            salida.append(((Grupo)grupos.get(i)).getGrupo()).append("</option>\n");
        }
        out.println(salida);
    }

    private void generaReporteAnalisisGposPorCorte(PrintWriter out, int corte) {
       String archivo = this.generaReporteGruposPorCorte(corte);
            out.println("<center>");
            out.println("<a href=\""+archivo+"\">Archivo Generado");
            out.print("<button class=\"btn-pdf\"></button>");
            out.print("</a>");
            out.flush();
    }

    private void analizaUnCorte(int corte) {
        obtieneDatos datos = new obtieneDatos(this.conn);
        List<Grupo> gruposM = datos.obtieneGruposCorte(corte, "M");
        List<Grupo> gruposV = datos.obtieneGruposCorte(corte, "V");
        AnalizarDatosCapturaRedes analizador =null;
        for(int i=0; i<gruposM.size(); i++){
            analizador =new AnalizarDatosCapturaRedes(conn, gruposM.get(i).getIdGrupo());
            analizador=null;
        }
        for(int i=0; i<gruposV.size(); i++){
            analizador =new AnalizarDatosCapturaRedes(conn, gruposV.get(i).getIdGrupo());
            analizador=null;
        }
    }

}
