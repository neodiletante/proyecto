package catalogos.listas;
import catalogos.alumnos.Alumno;
import catalogos.alumnos.AlumnosDAO;
import catalogos.grupos.Grupo;
import catalogos.grupos.GruposDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.servlet.http.HttpSession;
/**
 *
 * @author daniel
 */
public class listasUtil{
private Connection con = null;
private static final Logger log= Logger.getLogger(ListasDAO.class.getName());
private GruposDAO gDao = new GruposDAO(con);

public listasUtil(HttpSession sesion){
   con=(Connection)sesion.getAttribute("conn");
}

public int[] getCortes(){
        int[] retVar=null;
        PreparedStatement pst=null;
        ResultSet rst=null;
        StringBuilder query= new StringBuilder("select corte from cortes");
        try{
            pst = con.prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rst=pst.executeQuery(query.toString());
            if( rst.first() ){
                rst.last();
                retVar = new int[rst.getRow()];
                if(rst.first()){
                    int i=0;
                    do{
                        retVar[i++]=rst.getInt("corte");
                    }while(rst.next());
                }    
            }
        }
        catch(Exception ex){
            log.log(Level.WARNING, "Error:", ex);
        }
        finally{
          try {
            if(pst!=null)pst.close();
            if(rst!=null)rst.close();
          } catch (SQLException ex) {
             Logger.getLogger(listasUtil.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        return retVar;
    }

    public List<Grupo> getgrupos(int corte){
        return gDao.consultaGrupos(corte);
    }
    
    public List<Alumno> getAlumnos(int corte){
      List<Alumno> alumnos= null  ;
      StringBuilder query= 
         new StringBuilder(" select * ");
        query.append(" from ");
        query.append(" tc_alumno where ");
        query.append(" no_expediente not in ( "); 
        query.append(" select ");
        query.append(" no_expediente ");
        query.append(" from ");
        query.append("    tc_listas l, ");
        query.append("    tc_grupos g ");
        query.append("  where ");
        query.append("  l.id_grupo = g.id_grupo ");
        query.append("  and corte= ").append(corte);
      Statement stm=null;
      ResultSet rst=null;
      try{
          stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          rst= stm.executeQuery(query.toString());
          Alumno alumno =  new Alumno();
          alumnos = alumno.mapRst(rst);
      }
      catch(Exception ex){
        log.log(Level.WARNING,"Error: ", ex);
      }
      finally{
        try {
            if(rst!=null)rst.close();
            if(stm!=null)stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(listasUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      return alumnos;
    }
}