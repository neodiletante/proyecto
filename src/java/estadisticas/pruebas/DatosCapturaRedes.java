/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estadisticas.pruebas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.*;

/**
 *
 * @author daniel
 */
public class DatosCapturaRedes {
    private int id_grupo=0;
    private Integer id_red=0;
    private ArrayList Integrantes=new ArrayList();
    private int frecuencia=0;
    private double proporcion=0;
    private Connection conn=null;
    static final Logger log = Logger.getLogger(DatosCapturaRedes.class.getName());
    
    public DatosCapturaRedes(int id_grupo, Connection conec){
        this.conn=conec;
        this.id_grupo=id_grupo;
    }

    private DatosCapturaRedes() {}
    
     /*Obtiene la lista de redes en un ArrayList de tipo DatosCapturaRedes*/
    public ArrayList<DatosCapturaRedes> getDatosCapturaRedes(int id_grupo){
        ArrayList<DatosCapturaRedes> ListaDatos = null;
        StringBuilder query= new StringBuilder("");
        query.append(" select id_grupo, crs.id_red, rs.no_lista ");
        query.append(" from ");
        query.append(" tr_redes_sociales rs, ");
        query.append(" tc_redes_sociales crs ");
        query.append(" where ");
        query.append(" rs.id_red=crs.id_red ");
        query.append(" and crs.id_grupo= ").append(id_grupo);
        query.append(" order by id_red, no_lista_referido ");
        Statement stm=null;
        ResultSet rst=null;
        try{
            stm=conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            rst=stm.executeQuery( query.toString() );
            if(rst.first()){
              ListaDatos=this.MapRst(rst);
            }
        }
        catch(Exception ex){
            log.log(Level.WARNING, "Error: ", ex);
        }
        finally{
            try{
                if(stm!=null)stm.close();
            }catch(Exception exc){}
        }
        /*vacia el resultset */
        return ListaDatos;
    }
    
    public ArrayList<DatosCapturaRedes> MapRst(ResultSet rst){
        DatosCapturaRedes item = null;
        ArrayList<DatosCapturaRedes> Lista=null;
       try{ 
         if(rst.first()){
             boolean seguir=true;
             Lista= new ArrayList<DatosCapturaRedes>();
             do{
                 if(item==null)
                    item=new DatosCapturaRedes();
                 item.id_grupo=rst.getInt("id_grupo");
                 item.id_red=new Integer(rst.getInt("id_red"));
                 while(seguir){
                   if(rst.getInt("id_red")==item.id_red)  {
                     if(item.Integrantes==null)
                        item.Integrantes=new ArrayList<Integer>();
                     else
                        item.Integrantes.add(rst.getInt("no_lista")); 
                   }    
                   else{
                     Lista.add(item);
                     item=null;
                     break;
                   }
                   seguir=rst.next();
                 } 
             }while(seguir);
         }
       }
       catch(Exception ex){
         log.log(Level.WARNING, "Error: ", ex);
       }
       finally{
        try{
           rst.close(); 
        }catch(Exception exc){}
       }
       return Lista;
    }
    
    /**/
    public String getIntegrantesString(){
        StringBuilder strIntegrantes=new StringBuilder("");
        ArrayList<String> Integ=getIntegrantes();
        for(int i=0; i<Integrantes.size(); i++){
            strIntegrantes.append(Integrantes.get(i)).append("_");
        }
        return strIntegrantes.length()>0?strIntegrantes.substring(0,strIntegrantes.length()-1):"";
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
     * @return the id_red
     */
    public Integer getId_red() {
        return id_red;
    }

    /**
     * @param id_red the id_red to set
     */
    public void setId_red(Integer id_red) {
        this.id_red = id_red;
    }

    /**
     * @return the Integrantes
     */
    public ArrayList getIntegrantes() {
        return Integrantes;
    }

    /**
     * @param Integrantes the Integrantes to set
     */
    public void setIntegrantes(ArrayList Integrantes) {
        this.Integrantes = Integrantes;
    }

    /**
     * @return the frecuencia
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia the frecuencia to set
     */
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * @return the proporcion
     */
    public double getProporcion() {
        return proporcion;
    }

    /**
     * @param proporcion the proporcion to set
     */
    public void setProporcion(double proporcion) {
        this.proporcion = proporcion;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
