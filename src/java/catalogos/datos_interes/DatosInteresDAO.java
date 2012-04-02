package catalogos.datos_interes;

import catalogos.alumnos.AlumnosDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ulises
 */
public class DatosInteresDAO {
 private Connection con=null;
    private Statement stmt=null;
    private ResultSet rs=null;

  public DatosInteresDAO(Connection con){
    this.con = con;
  }
  
  public List<DatoInteres> buscaDatosInteres(){
    DatoInteres datoInteres = null;
    List<DatoInteres> datosInteres = new ArrayList<DatoInteres>();
    String query = "SELECT * FROM tc_datos_interes";// WHERE tipo NOT IN (SELECT tipo FROM tc_datos_interes)";
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(query);
      while(rs.next()) {
        datoInteres = new DatoInteres();
        datoInteres.setIdDato(rs.getInt("id_dato"));
        datoInteres.setDescripcion(rs.getString("descripcion"));
        datosInteres.add(datoInteres);
      }
    }
    catch (SQLException ex) {
        Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally{
      return datosInteres;
    }
  }
  
  public List<DatoInteres> buscaDatosInteres(int idDato){
    DatoInteres datoInteres = null;
    List<DatoInteres> datosInteres = new ArrayList<DatoInteres>();
    String query = "SELECT * FROM tc_datos_interes where id_dato="+idDato;// WHERE tipo NOT IN (SELECT tipo FROM tc_datos_interes)";
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(query);
      while(rs.next()) {
        datoInteres = new DatoInteres();
        datoInteres.setIdDato(rs.getInt("id_dato"));
        datoInteres.setDescripcion(rs.getString("descripcion"));
        datosInteres.add(datoInteres);
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return datosInteres;
    }
  }
  
  public int borraDatoInteres(int idDato){
    PreparedStatement psBorrar = null;
    int retVar=0;
    String qBorrar = "DELETE FROM tc_datos_interes WHERE id_dato = ?";
        try {
          psBorrar = con.prepareStatement(qBorrar);
          psBorrar.setInt(1, idDato);
          retVar=psBorrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return retVar;
  }
  
  public int insertaDatoInteres(DatoInteres datoInteres){
      int status = -1;  
      PreparedStatement psInsertar = null;
      ResultSet rst =null;
      int indice=0;
      try{
        stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rst =stmt.executeQuery("select max(id_dato)+1 sig from tc_datos_interes");
        if(rst.first())
            indice=rst.getInt("sig");
        else
            indice=1;
      }
      catch(Exception ex){
          Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.WARNING,"Error" ,ex);
      }
      finally{
        try{  
          if( rst!=null ) rst.close();
          if( stmt!=null ) stmt.close();
        }catch(Exception ex)  {}
      }
      String qInsertar = "INSERT INTO tc_datos_interes (id_dato, descripcion) VALUES(? ,?)";        
      try {
        psInsertar = con.prepareStatement(qInsertar);
        psInsertar.setInt(1, indice);
        psInsertar.setString(2, datoInteres.getDescripcion());
        status=psInsertar.executeUpdate();

      } catch (SQLException ex) {
          Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
        return status;
      }
    }
  
  public int modificaDatoInteres(DatoInteres datoInteres){
      int status = -1;
      PreparedStatement psModificar = null;
      String qModificar = 
        "UPDATE tc_datos_interes SET descripcion = ? WHERE id_dato = ?";
      try {
        psModificar = con.prepareStatement(qModificar);
        psModificar.setString(1, datoInteres.getDescripcion());
        psModificar.setInt(2, datoInteres.getIdDato());
        status=psModificar.executeUpdate();
      } catch (SQLException ex) {
        System.out.println("Ocurrió un error SQL al modificar el dato");
        Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
        return status;
      }
    }
  
    public DatoInteres buscaDatoInteres(int idDato){
    PreparedStatement psBuscar = null;
    DatoInteres datoInteres = new DatoInteres(); 
      String query = "SELECT * FROM tc_datos_interes WHERE id_dato = ?";
    try {
      psBuscar = con.prepareStatement(query);
      psBuscar.setInt(1, idDato);
      rs = psBuscar.executeQuery();
       while(rs.next()) {
         datoInteres.setIdDato(idDato);
         datoInteres.setDescripcion(rs.getString("descripcion"));
       }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return datoInteres;
    }
  }

    public String getSiguienteId() {
      PreparedStatement psBuscar = null;
      String datoInteres = null; 
      String query = "SELECT (max(id_dato)+1) as id FROM tc_datos_interes ";
      try {
        psBuscar = con.prepareStatement(query);
        rs = psBuscar.executeQuery();
        if(rs.first())
          datoInteres=rs.getString("id");
      } 
      catch (SQLException ex) {
        Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      finally{
        return datoInteres;
      }
    }
  
}
