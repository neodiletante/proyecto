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
  
  public List<TipoDato> buscaTiposDeDatos(){
    
    TipoDato tipoDato = null;
    List<TipoDato> tiposDato = new ArrayList<TipoDato>();
     String query = "SELECT * FROM tc_tipo_dato_interes";
   
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(query);
      while(rs.next()) {
        tipoDato = new TipoDato();
        tipoDato.setTipo(rs.getInt("tipo"));
        tipoDato.setDescripcion(rs.getString("descripcion"));
        tiposDato.add(tipoDato);
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return tiposDato;
    }
  }
  
  public List<TipoDato> buscaTiposDeDatos(boolean noUsado){
    
    TipoDato tipoDato = null;
    List<TipoDato> tiposDato = new ArrayList<TipoDato>();
     String query = "SELECT * FROM tc_tipo_dato_interes";
    if (noUsado){
      query+= " WHERE tipo NOT IN (SELECT tipo FROM tc_datos_interes)";
    }else{
      query+= " WHERE tipo IN (SELECT tipo FROM tc_datos_interes)";
    }
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(query);
      while(rs.next()) {
        tipoDato = new TipoDato();
        tipoDato.setTipo(rs.getInt("tipo"));
        tipoDato.setDescripcion(rs.getString("descripcion"));
        tiposDato.add(tipoDato);
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return tiposDato;
    }
  }
  
  public void borraTipoDato(int tipo){
    PreparedStatement psBorrar = null;
    String qBorrar = "DELETE FROM tc_tipo_dato_interes WHERE tipo = ?";
        try {
          psBorrar = con.prepareStatement(qBorrar);
          psBorrar.setInt(1, tipo);
          psBorrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public int insertaTipoDato(TipoDato tipoDato){
      int status = -1;  
      PreparedStatement psInsertar = null;
      String qInsertar = "INSERT INTO tc_tipo_dato_interes VALUES(?,?)";        
      try {
        psInsertar = con.prepareStatement(qInsertar);
        psInsertar.setInt(1, 0);
        psInsertar.setString(2, tipoDato.getDescripcion());
        psInsertar.executeUpdate();
        status=1;

      } catch (SQLException ex) {
          Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
        return status;
      }
    }
  
  public List<DatoInteres> buscaDatosInteres(){
    System.out.println("En el método busca datos interés");
    DatoInteres datoInteres = null;
    PreparedStatement psBusca = null;
    ResultSet res = null;
    List<DatoInteres> datosInteres = new ArrayList<DatoInteres>();
    String query = "SELECT id_dato, descripcion FROM tc_datos_interes";
    try {
      psBusca = con.prepareStatement(query);
      res = psBusca.executeQuery();
      while(res.next()) {
        datoInteres = new DatoInteres();
        datoInteres.setIdDato(res.getInt("id_dato"));
        datoInteres.setDescripcion(res.getString("descripcion"));
        datosInteres.add(datoInteres);
        System.out.println("Estoy agregando otro dato de interés  ");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return datosInteres;
    }
  }
  
  public List<DatoInteres> buscaDatosInteres(int idDato){
   System.out.println("En el método busca datos interés");
    DatoInteres datoInteres = null;
    PreparedStatement psBusca = null;
    ResultSet res = null;
    List<DatoInteres> datosInteres = new ArrayList<DatoInteres>();
    String query = "SELECT id_dato,descripcion FROM tc_datos_interes WHERE id_dato = ?";
    try {
      psBusca = con.prepareStatement(query);
      psBusca.setInt(1, idDato);
      res = psBusca.executeQuery();
      
      while(res.next()) {
        datoInteres = new DatoInteres();
        datoInteres.setIdDato(res.getInt("id_dato"));
        datoInteres.setDescripcion(res.getString("descripcion"));
        datosInteres.add(datoInteres);
        System.out.println("Estoy agregando otro dato de interés  ");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatosInteresDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return datosInteres;
    }
  }
  
  public void borraDatoInteres(int idDato){
    PreparedStatement psBorrar = null;
    String qBorrar = "DELETE FROM tc_datos_interes WHERE id_dato = ?";
        try {
          psBorrar = con.prepareStatement(qBorrar);
          psBorrar.setInt(1, idDato);
          psBorrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public int insertaDatoInteres(DatoInteres datoInteres){
      int status = -1;  
      PreparedStatement psInsertar = null;
      String qInsertar = "INSERT INTO tc_datos_interes VALUES(?,?,?)";        
      try {
        psInsertar = con.prepareStatement(qInsertar);
        psInsertar.setInt(1, 0);
        psInsertar.setString(2, datoInteres.getDescripcion());
        psInsertar.setInt(3, datoInteres.getTipo());
        psInsertar.executeUpdate();
        status=1;

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
        "UPDATE tc_datos_interes SET descripcion = ?, tipo = ? WHERE id_dato = ?";
      try {
        psModificar = con.prepareStatement(qModificar);
        psModificar.setString(1, datoInteres.getDescripcion());
        psModificar.setInt(2, datoInteres.getTipo());
        psModificar.setInt(3, datoInteres.getIdDato());
        psModificar.executeUpdate();
        status=1;
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
         datoInteres.setTipo(rs.getInt("tipo"));
       }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return datoInteres;
    }
  }
  
}