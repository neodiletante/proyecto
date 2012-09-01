/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estadisticas.pruebas.servlet;

import estadisticas.pruebas.servlet.RedesAnalisisGrupos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class analisisGruposDAO {
    Connection conn=null;
    private static final Logger log = Logger.getLogger(analisisGruposDAO.class.getName());

    public analisisGruposDAO(Connection conect){
        this.conn=conect;
    }

    List<RedesAnalisisRedesReales> getAnalisisRedesGrupo(String id_grupo) {
        RedesAnalisisRedesReales parser = new RedesAnalisisRedesReales();
        ResultSet rst=null;
        Statement stm=null;
         List<RedesAnalisisRedesReales> ListaRedesReales=null;
        StringBuilder query = new StringBuilder("");
        query.append(" select * from tr_analisis_redes where id_grupo=").append(id_grupo);
        try{
            stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rst=stm.executeQuery(query.toString());
            ListaRedesReales=parser.parseRst(rst);
        }catch(Exception ex){
          Logger.getLogger(analisisGruposDAO.class.getName()).log(Level.WARNING,"Error: ",ex);
        }
        finally{
           try{
               if(rst!=null) rst.close();
               if(stm!=null) stm.close();
           }
           catch(Exception ex){}
        }
        return ListaRedesReales;
    }
    
    RedesAnalisisGrupos getAnalisisGrupo(String id_grupo) {
        RedesAnalisisGrupos parser = new RedesAnalisisGrupos();
        ResultSet rst=null;
        Statement stm=null;
         RedesAnalisisGrupos retVar=null;
        StringBuilder query = new StringBuilder("");
        query.append(" select * from tr_analisis_grupos where id_grupo=").append(id_grupo);
        try{
           stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
           rst=stm.executeQuery(query.toString());
           if(rst.first()){
            parser=parser.parseRst(rst);
           }
        }catch(Exception ex){
          Logger.getLogger(analisisGruposDAO.class.getName()).log(Level.WARNING,"Error: ",ex);
        }
        finally{
           try{
               if(rst!=null) rst.close();
               if(stm!=null) stm.close();
           }
           catch(Exception ex){}
        }
        return parser;
    }

    
}
