/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;


import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
/**
 *
 * @author ulises
 */
public class AlumnosDAO {
     private Connection con=null;
    private Statement stmt=null;
    private ResultSet rs=null;
    private Alumno alumno;
    
    AlumnosDAO(Connection con){
        this.con = con;
    }
    
    public List consultaAlumnos(String buscaNombre){
        PreparedStatement psBuscar = null;
        List<Alumno> alumnos = new ArrayList<Alumno>();
        String query = "SELECT no_expediente, Nombre, sexo FROM tc_alumno WHERE nombre LIKE ?";
        //String query = "SELECT no_expediente, Nombre, sexo FROM tc_alumno";
        List<Integer> alumnosConRegistros = buscaAlumnosConRegistros();
        try{
           
            //stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
           psBuscar = con.prepareStatement(query);
          psBuscar.setString(1, "%"+buscaNombre+"%");
           rs = psBuscar.executeQuery();
           // rs = stmt.executeQuery(query);
              while(rs.next()) {
                int noExpediente = rs.getInt("no_expediente");
                String nombre = rs.getString("Nombre");
                String sexo = rs.getString("sexo");
                alumno = new Alumno(noExpediente, nombre, sexo);
                if(alumnosConRegistros.contains(noExpediente)){
                  alumno.setTieneRegistros(true);
                }else{
                  alumno.setTieneRegistros(false);
                }
                  
                alumnos.add(alumno);
                
              }
              
        }catch(SQLException ex){
           Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return alumnos;
        }
        
    }
    
    public Alumno buscarAlumno(int noExpediente){
      alumno = null;
      PreparedStatement psBuscar = null;
      String query = "SELECT * FROM tc_alumno WHERE no_expediente = ?";
    try {
      psBuscar = con.prepareStatement(query);
      psBuscar.setInt(1, noExpediente);
      rs = psBuscar.executeQuery();
       while(rs.next()) {
                //int noExpediente = rs.getInt("no_expediente");
                String nombre = rs.getString("Nombre");
                String sexo = rs.getString("sexo");
                alumno = new Alumno(noExpediente, nombre, sexo);               
              }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return alumno;
        }
    }
    
    public int buscaAlumno(int noExpediente){
      //alumno = null;
      int cuenta = -1;
      PreparedStatement psBuscar = null;
      String query = "SELECT COUNT(*) AS cuenta FROM tc_alumno where no_expediente = ?";
    try {
      psBuscar = con.prepareStatement(query);
      psBuscar.setInt(1, noExpediente);
      rs = psBuscar.executeQuery();
       while(rs.next()) {
         cuenta = rs.getInt("cuenta");
                
       }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return cuenta;
        }

    }
    
    public void borrarAlumno(int noExpediente){
        PreparedStatement psBorrar = null;
         String qBorrar = "DELETE FROM tc_alumno WHERE no_expediente = ?";
        try {
          psBorrar = con.prepareStatement(qBorrar);
          psBorrar.setInt(1,noExpediente);
          psBorrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int insertarAlumno(Alumno alumno){
      int status = -1;  
      int cuenta = buscaAlumno(alumno.getNoExpediente());
      if (cuenta>0){
        return 0;
      }
      System.out.println("En el método de insertar Alumno");
      PreparedStatement psInsertar = null;
      String qInsertar = "INSERT INTO tc_alumno VALUES(?,?,?)";        
      try {
        psInsertar = con.prepareStatement(qInsertar);
        psInsertar.setInt(1, alumno.getNoExpediente());
        psInsertar.setString(2, alumno.getNombre());
        psInsertar.setString(3, alumno.getSexo());
        psInsertar.executeUpdate();
        status=1;

      } catch (SQLException ex) {
          Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
        return status;
      }
    }
    
    public int modificarAlumno(int noExpedienteAnt, Alumno alumno){
      int cuenta = buscaAlumno(alumno.getNoExpediente());
      if(cuenta > 0 && noExpedienteAnt != alumno.getNoExpediente()){
        return 0;
      }
      int status = -1;
      PreparedStatement psModificar = null;
      String qModificar = 
        "UPDATE tc_alumno SET no_expediente = ?, Nombre = ?, sexo = ? WHERE no_expediente = ?";
      try {
        psModificar = con.prepareStatement(qModificar);
        psModificar.setInt(1, alumno.getNoExpediente());
        psModificar.setString(2, alumno.getNombre());
        psModificar.setString(3, alumno.getSexo());
        psModificar.setInt(4, noExpedienteAnt);
        psModificar.executeUpdate();
        status=1;
      } catch (SQLException ex) {
        Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
        return status;
      }
    }
    
    public List<Integer> buscaAlumnosConRegistros(){
      PreparedStatement psBuscar = null;
      List<Integer> listaAlumnos = new ArrayList<Integer>();
      int noExpediente = 0;
      String query = "SELECT a.no_expediente"
              + " FROM tc_alumno a INNER JOIN tc_listas l"
              + " ON a.no_expediente = l.no_expediente";
    try {
      psBuscar = con.prepareStatement(query);
      rs = psBuscar.executeQuery();
      while(rs.next()) {
        noExpediente = rs.getInt("no_expediente");
        listaAlumnos.add(noExpediente);
       }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return listaAlumnos;
        }
    }
    
    public List<Alumno> buscaPosicionAlumnos(){
      PreparedStatement psBuscar = null;
      List<Alumno> listaAlumnos = new ArrayList<Alumno>();
      Alumno alumno;
      String query = "SELECT @num:=@num+1 AS posicion, a.no_expediente, a.nombre"
              + " FROM tc_alumno a, (SELECT @num:=0) r ORDER BY no_expediente";
    try {
      psBuscar = con.prepareStatement(query);
      rs = psBuscar.executeQuery();
      while(rs.next()) {
        alumno = new Alumno();
        alumno.setPosicion(rs.getInt("posicion"));
        alumno.setNoExpediente(rs.getInt("no_expediente"));
        alumno.setNombre(rs.getString("nombre"));
        listaAlumnos.add(alumno);
       }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return listaAlumnos;
        }
    }
    
     public AlumnoEnRedes buscaDatos(int no_expediente, int corte){
      PreparedStatement psBuscar = null;
      AlumnoEnRedes alumno = null;
      String query = "SELECT l.no_lista, CONCAT(g.grado,'° ',g.grupo,' ',g.turno) AS grupo"
              + " FROM tc_listas l INNER JOIN tc_grupos g ON g.id_grupo = l.id_grupo "
              + " WHERE g.corte = ? AND l.no_expediente = ?";
    try {
      psBuscar = con.prepareStatement(query);
      psBuscar.setInt(1, corte);
      psBuscar.setInt(2, no_expediente);
      rs = psBuscar.executeQuery();
      while(rs.next()) {
        alumno = new AlumnoEnRedes();
        alumno.setNoLista(rs.getInt("no_lista"));
        alumno.setGrupo(rs.getString("grupo"));
        System.out.println("En el DAO alumno, grupo "+ alumno.getGrupo());
      }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return alumno;
        }
    }
     
      public List<Integer> buscaRedesRefiere(int no_expediente, int corte){
      PreparedStatement psBuscar = null;
      List<Integer> redes = new ArrayList<Integer>();
      String query = 
              "SELECT crs.id_red FROM tc_redes_sociales crs INNER JOIN tc_listas l ON crs.no_lista_refiere = l.no_lista "
              + " INNER JOIN tc_grupos g ON g.id_grupo = l.id_grupo "
              + " WHERE g.corte = ? AND l.no_expediente = ?";
    try {
      psBuscar = con.prepareStatement(query);
      psBuscar.setInt(1, corte);
      psBuscar.setInt(2, no_expediente);
      rs = psBuscar.executeQuery();
      while(rs.next()) {
       redes.add(rs.getInt("id_red"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
          try {
            rs.close();
          } catch (SQLException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
          return redes;
        }
    }
     
     //SELECT crs.id_red FROM tc_redes_sociales crs INNER JOIN tc_listas l ON crs.no_lista_refiere = l.no_lista INNER JOIN tc_grupos g ON g.id_grupo = l.id_grupo WHERE g.corte = 1 AND l.no_expediente = 1
    
    
}