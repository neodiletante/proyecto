/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.listas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author daniel
 */
public class ListasDAO {
    private String nombre;
    private int id_grupo;
    private int no_lista;
    private String color;
    private String grupo_estadistico;
    private int no_exp;
    private String nombreGrupo;
    private boolean tieneRegistros;
    Connection con = null;

    private final Logger log= Logger.getLogger(this.getClass().getName());
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    public ListasDAO(HttpSession sesion){
        con=(Connection)sesion.getAttribute("conn");
    }
    
    private ListasDAO(){}
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the id_grupo
     */
    public int getId_grupo() {
        return id_grupo;
    }

    /**
     * @param id_grupo the id_grupo to set
     */
    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    /**
     * @return the no_lista
     */
    public int getNo_lista() {
        return no_lista;
    }

    /**
     * @param no_lista the no_lista to set
     */
    public void setNo_lista(int no_lista) {
        this.no_lista = no_lista;
    }
    
    public List map(ResultSet rst){
        ArrayList  retVar = null;
        ListasDAO dao =null;
        try{
          if(rst.first()){  
              rst.beforeFirst();
              retVar= new ArrayList();
              while(rst.next()){
                dao= new ListasDAO();
                dao.setNo_lista(rst.getInt("no_lista"));
                dao.setNombre(rst.getString("nombre"));
                dao.setId_grupo(rst.getInt("id_grupo"));
                dao.setColor(rst.getString("color"));
                dao.setGrupo_estadistico(rst.getString("grupo_estadistico"));
                dao.setNombreGrupo("nombre_grupo");
                dao.setNo_exp(Integer.parseInt(rst.getString("no_expediente")));
                dao.setTieneRegistros(rst.getInt("registros")>0);
                retVar.add(dao);
                dao=null;
              }  
          }
        }
        catch(Exception ex){
           log.log(Level.WARNING, "Error:", ex);
        }
        finally{
            if(rst!=null) try {
                rst.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(ListasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retVar;
    }
        
        public ListasDAO mapRqt(HttpServletRequest request){
            ListasDAO dao=new ListasDAO(request.getSession());
            if(request.getParameter("id_grupo")!=null)
              try{
                dao.setId_grupo(Integer.parseInt(request.getParameter("id_grupo")));
              }catch(Exception ex){}
            if( request.getParameter("no_lista")!=null )
                dao.setNo_lista(Integer.parseInt(request.getParameter("no_lista")));
            dao.setNombre(request.getParameter("nombre"));
            dao.setColor(request.getParameter("color"));
            dao.setGrupo_estadistico(request.getParameter("grupo_est"));
            if(request.getParameter("no_expediente")!=null)
                dao.setNo_exp(Integer.parseInt(request.getParameter("no_expediente")));
            return dao;
        }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the grupo_estadistico
     */
    public String getGrupo_estadistico() {
        return grupo_estadistico;
    }

    /**
     * @param grupo_estadistico the grupo_estadistico to set
     */
    public void setGrupo_estadistico(String grupo_estadistico) {
        this.grupo_estadistico = grupo_estadistico;
    }
    
    public List getDatos(int id_grupo){
        List retVar = null;
        ResultSet rst = null;
        PreparedStatement pst = null;
        StringBuilder query=new StringBuilder("select * from vw_listas where id_grupo=?");
        try{
            pst=con.prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setInt(1, id_grupo);
            rst=pst.executeQuery();
            retVar = this.map(rst);
        }
        catch(Exception ex){
            log.log(Level.WARNING, "Error:", ex);
        }
        return retVar;
    }

    /**
     * @return the no_exp
     */
    public int getNo_exp() {
        return no_exp;
    }

    /**
     * @param no_exp the no_exp to set
     */
    public void setNo_exp(int no_exp) {
        this.no_exp = no_exp;
    }

    /**
     * @return the nombreGrupo
     */
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    /**
     * @param nombreGrupo the nombreGrupo to set
     */
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    /**
     * @return the tieneRegistros
     */
    public boolean isTieneRegistros() {
        return tieneRegistros;
    }

    /**
     * @param tieneRegistros the tieneRegistros to set
     */
    public void setTieneRegistros(boolean tieneRegistros) {
        this.tieneRegistros = tieneRegistros;
    }
    
    public boolean getTieneRegistros(){return this.tieneRegistros;}
    
    }

