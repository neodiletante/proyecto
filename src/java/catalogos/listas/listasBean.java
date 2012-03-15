/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.listas;

import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author daniel
 */
public class listasBean extends HttpServlet {
    private ListasDAO dao = null;
    private Connection con = null;
    private static final Logger log= Logger.getLogger(ListasDAO.class.getName());
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dao= new ListasDAO(request.getSession());
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        con=(Connection)request.getSession().getAttribute("conn");
        String[] noLista = request.getParameterValues("lstborra[]");
        String id_grupo = request.getParameter("id_grupo");
        try {
             String accion=request.getParameter("accion");
             accion=accion==null?" ":accion;
             
             String opcion=request.getParameter("opcion");
             opcion=opcion==null?" ":opcion;
             
             String mensaje=null;
             dao=dao.mapRqt(request);
             
             if(accion.equalsIgnoreCase("agregar")){
                 mensaje=agregarALista(dao);
             }
             
             if(accion.equalsIgnoreCase("borrar")){
                 if(noLista!=null  && noLista.length>0 &&borrarDeLista(noLista, id_grupo))
                     mensaje="Elemento borrado";
                 else
                     if(noLista==null || noLista.length==0)
                         mensaje="No hay alumnos seleccionados";
                     else    
                     mensaje="No se pudo borrar, revise que no forme parte de una red";
             }
             
             if(accion.equalsIgnoreCase("modificar")){
                 modificarEnLista(dao);
             }
             
             
             if( opcion.equalsIgnoreCase("getGrupos") ){
                 String corte = request.getParameter("corte");
                 String turno = request.getParameter("turno");
                 mensaje=getGrupos( corte, turno );
             }
             
             if( opcion.equalsIgnoreCase("getAlumnos") ){
                 String corte = request.getParameter("corte");
                 mensaje=getAlumnos( corte );
             }
             
             if(opcion.equalsIgnoreCase("getLista")){
                 String idGrupo = request.getParameter("id_grupo");
                 mensaje=getLista(Integer.parseInt(idGrupo));
             }
             
             if(opcion.equalsIgnoreCase("getListaGrupos")){
                 mensaje=getListaGrupo(dao.getId_grupo());
             }
             
             out.println(mensaje);
             
        }catch(Exception ex){
            log.log(Level.WARNING, "Error:", ex);
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
        return "Servlet que maneja las listas";
    }// </editor-fold>

    private String agregarALista(ListasDAO dao)  {
        StringBuilder query = new StringBuilder("insert into tc_listas(no_lista, id_grupo, no_expediente, color, gpo_estadistico)values(?,?,?,?,?)");
        PreparedStatement pst=null;
        boolean retVar=false;
        try{
            pst = con.prepareStatement(query.toString());
            pst.setInt(1, dao.getNo_lista());
            pst.setInt(2, dao.getId_grupo());
            pst.setInt(3, dao.getNo_exp());
            pst.setString(4, dao.getColor());
            pst.setString(5, dao.getGrupo_estadistico());
            retVar=pst.executeUpdate()>0;
        }
        catch(Exception ex){
            log.log(Level.WARNING,"Error: ",ex);
        }
        finally{
            if(pst!=null)try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retVar?"Agregado en lista":"No agregado en lista";
    }

    private boolean borrarDeLista(String[] noLista, String id_grupo) {
        boolean retVar=true;
        StringBuilder query = new StringBuilder("delete from tc_listas where id_grupo=? and no_lista=?");
        PreparedStatement pst=null;
        if(noLista!=null)
            try{
                pst = con.prepareStatement(query.toString());
                for(int i=0; i<noLista.length; i++){
                    pst.setString(1, id_grupo);
                    pst.setString(2, noLista[i]);
                    if(!(pst.executeUpdate()>0))
                        retVar=false;
                }
            }
            catch(Exception ex){
               retVar=false;
               log.log(Level.WARNING,"Error: ",ex);
            }
            finally{
                if(pst!=null)try {
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return retVar;
    }

    private boolean modificarEnLista(ListasDAO dao) {
        boolean retVar=false;
        StringBuilder query = new StringBuilder("update tb_listas set no_exp=?, color=?, gpo_estadistico=?  where id_grupo=? and no_lista=?");
        PreparedStatement pst=null;
        try{
            pst = con.prepareStatement(query.toString());
            pst.setInt(1, dao.getNo_exp());
            pst.setString(2, dao.getColor());
            pst.setString(3, dao.getGrupo_estadistico());
            pst.setInt(5, dao.getId_grupo());
            pst.setInt(6, dao.getNo_lista());
            retVar=pst.executeUpdate()>0;
        }
        catch(Exception ex){
            log.log(Level.WARNING,"Error: ",ex);
        }
        finally{
            if(pst!=null)try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retVar;
    }
    
    /* devuelve la lista de alumnos de la lista actual */
    private String getLista(int id_grupo){
        StringBuilder retVar = null;
        PreparedStatement pst=null;
        ResultSet rst=null;
        try{
            pst=con.prepareStatement("select * from vw_listas where id_grupo=? order by no_lista", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setInt(1, id_grupo);
            rst= pst.executeQuery();
            retVar= new StringBuilder("  ");
            retVar.append("      <thead> ");
            retVar.append("      <tr> ");
            retVar.append("          <th colspan=\"8\">Lista de alumnos del grupo</th> ");
            retVar.append("       </tr><tr> ");
            retVar.append("           <th width=\"5%\">Numero</th> ");
            retVar.append("           <th width=\"30%\">Nombre</th> ");
            retVar.append("           <th width=\"10%\">Expediente</th> ");
            retVar.append("           <th width=\"10%\">Grupo</th> ");
            retVar.append("           <th width=\"5%\">Color</th> ");
            retVar.append("           <th width=\"10%\">Gpo est</th> ");
            retVar.append("           <th width=\"10%\">Borrar</th> ");
            retVar.append("           <th width=\"10%\">cambiar</th></tr></thead>");
            if(rst.first()){
               do{
                   retVar.append(" <tr><td>");
                   retVar.append( rst.getString("no_lista") ).append( "</td><td>" );
                   retVar.append( rst.getString("nombre") ).append( "</td><td>" );
                   retVar.append( rst.getString("no_expediente") ).append( "</td><td>");
                   retVar.append( rst.getString("nombregrupo") ).append("</td>");
                   retVar.append(" <td style=\"background-color:" ).append( rst.getString("color") ).append("\">");
                   retVar.append( "<input type=\"hidden\" id=\"no_lista\" value=\"" ).append( rst.getString("no_lista") ).append( "></td><td>" );
                   retVar.append( "<input type=\"hidden\" id=\"\" value=\"" ).append( rst.getString("no_lista") ).append( "\"/></td><td>" );
                   retVar.append( rst.getString("gpo_estadistico") ).append( "</td><td> " );
                   retVar.append("<input type=\"checkbox\" value =\"").append(rst.getString("no_lista")).append("\"/></td><td>");
                   retVar.append("<input type=\"radio\" name=\"no_lista\" value =\"").append(rst.getString("no_lista")).append("\"/></td></tr>");
               }
               while(rst.next());
            }
            else{
              retVar.append(" <tr><td colspan=\"8\"> No se encontr&oacute; informaci&oacute;n </td></tr>");
            }
        }
        catch(Exception ex){
            log.log(Level.WARNING,"Error: ",ex);
        }
        finally{
         try { 
            if(rst!=null) rst.close();
            if(pst!=null) pst.close();
         } catch (SQLException ex) {
             Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
         }       
        return retVar!=null?retVar.toString():"<option></option>";
    }
  }
    
    /* grupos en el corte y turno seleccionados */
    private String getGrupos(String corte, String turno) {
       StringBuilder retVar=null;
       StringBuilder query=new StringBuilder(" select id_grupo, ");
       query.append("concat( cast(grado as char),'°',grupo,' ',turno) as grupo ");
       query.append("from tc_grupos where corte=").append(corte);  
       query.append(" and turno='").append(turno).append("' ");
       Statement stm=null;
       ResultSet rst=null;
       try{
            stm= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rst=stm.executeQuery(query.toString());
            if(rst.first()){
                retVar=new StringBuilder("");
                do{
                    retVar.append("<option value=\"").append(rst.getString("id_grupo"));
                    retVar.append("\"> ").append(rst.getString("grupo")).append("</option>\n");
                }while( rst.next() );
            }
        }catch(Exception ex){
            log.log(Level.WARNING, "Error", ex);
        }
        finally{
            try {
                if(stm!=null)stm.close();
                if(rst!=null)rst.close();
            } catch (SQLException ex) {
                Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retVar!=null?retVar.toString():"";        
    }

    /* alumnos disponibles para asignar a grupos en el corte */
    private String getAlumnos(String corte) {
        StringBuilder retVar=null;
        StringBuilder query=new StringBuilder("select * ");
        query.append(" from tc_alumno ");
        query.append(" where no_expediente not in( ");
        query.append(" select l.no_expediente ");
        query.append(" from  ");
        query.append(" tc_listas l, ");
        query.append(" tc_grupos g ");
        query.append(" where ");
        query.append(" l.id_grupo=g.id_grupo ");
        query.append(" and g.corte=").append(corte).append(" )");
        Statement stm=null;
        ResultSet rst=null;
        try{
            stm= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rst=stm.executeQuery(query.toString());
            if(rst.first()){
                retVar=new StringBuilder("");
                do{
                    retVar.append("<option value=\"").append(rst.getString("no_expediente"));
                    retVar.append("\"> ").append(rst.getString("no_expediente"));
                    retVar.append(" - ").append(rst.getString("nombre")).append("</option>\n");
                }while(rst.next());
            }
        }catch(Exception ex){
            log.log(Level.WARNING, "Error", ex);
        }
        finally{
            try {
                if(stm!=null)stm.close();
                if(rst!=null)rst.close();
            } catch (SQLException ex) {
                Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retVar.toString();
    }
    
    
    private String getListaGrupo(int grupo){
        StringBuilder retVar= new StringBuilder("");
        Statement stm=null;
        ResultSet rst=null;
        StringBuilder query = new StringBuilder("select * from vw_listas where id_grupo="+grupo);
        try{
           stm= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
           rst=stm.executeQuery(query.toString());
           List<Integer> lista = new ArrayList<Integer>();
           if(rst.first()){
             do{
               lista.add(rst.getInt("no_lista"));
             }while(rst.next());
             for(int i=1; i<51; i++){
               if(!lista.contains(i))
                 retVar.append("<option value=\"").append(i).append("\">").append(i).append("</option>");
             }
           }
           else{
             for(int i=1; i<51; i++)
               retVar.append("<option value=\"").append(i).append("\">").append(i).append("</option>");
           }
        }
        catch(Exception ex){
          log.log(Level.WARNING, "Error", ex);
        }
        finally{
            try {
                if(stm!=null)stm.close();
                if(rst!=null)rst.close();
            } catch (SQLException ex) {
                Logger.getLogger(listasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        return retVar.toString();
    }
}
