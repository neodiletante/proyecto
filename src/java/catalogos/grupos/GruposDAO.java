/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.grupos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class GruposDAO {
    private Connection con=null;
    private Statement stmt=null;
    private ResultSet rs=null;
    private Grupo grupo = null;
    
    public GruposDAO(Connection con){
        this.con = con;
        
    }

        public List<Grupo> consultaGrupos(int corte){
            return consultaGrupos(corte, null);
        }
        
        public List consultaGrupos(int corte, String turno) {
        List<Grupo> grupos = new ArrayList<Grupo>();
        List<Integer> gruposConAlumnos = buscaGruposConAlumnos(corte);
        try{
           
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM tc_grupos where corte="+corte;
            if (turno!=null)
                query= query+" and turno=";
            query+=" order by turno";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                grupo = new Grupo();
                grupo.setIdGrupo(rs.getInt("id_grupo"));
                grupo.setGrado(rs.getInt("grado"));
                grupo.setGrupo(rs.getString("grupo"));
                grupo.setTurno(rs.getString("turno"));
                grupo.setCorte(rs.getInt("corte"));
                if(gruposConAlumnos.contains((Integer)grupo.getIdGrupo())){
                  grupo.setTieneAlumnos(true);
                }else{
                  grupo.setTieneAlumnos(false);
                }
                grupos.add(grupo);  
            }
            
   
        }catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println("Error SQL");
        } catch (Exception exc) {
           exc.printStackTrace();
        }
        
        return grupos;   
    }
        
        public void insertarGrupo(Grupo grupo){
            String qInsert = "INSERT INTO tc_grupos VALUES(0," 
              + grupo.getGrado() + ",'"
              + grupo.getGrupo().toUpperCase() + "','"
              + grupo.getTurno() + "',"
              + grupo.getCorte() + ")";
            System.out.println("En el DAO " + qInsert);
            
          try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.execute(qInsert);
          } catch (SQLException ex) {
              Logger.getLogger(GruposDAO.class.getName()).log(Level.SEVERE, null, ex);
              System.out.println("Falló insert");
          }
        }
        
        public void borrarGrupo(String idGrupo){
            try {
              String qBorrado = "DELETE FROM tc_grupos WHERE id_grupo = " + idGrupo;
              stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
              stmt.execute(qBorrado);
           } catch (SQLException ex) {
            Logger.getLogger(GruposDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        
        public void cambiarGrupo(String idGrupo, Grupo grupo){
            String qModificar = "UPDATE tc_grupos SET"
                + " grado=" + grupo.getGrado() 
                + ", grupo='" + grupo.getGrupo().toUpperCase()
                + "', turno='" + grupo.getTurno()
                + "', corte=" + grupo.getCorte()
                + " WHERE id_grupo = " + idGrupo;
            System.out.println(qModificar);
          try {
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.execute(qModificar);   
          } catch (SQLException ex) {
            Logger.getLogger(GruposDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        
        public List consultaCortes(){
            List cortes = new ArrayList();
            String query = "SELECT corte FROM cortes";
            try {
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(query);   
             while(rs.next()) {
               cortes.add(rs.getInt("corte"));
             }
             
          } catch (SQLException ex) {
            Logger.getLogger(GruposDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
            return cortes;
        }
    
                
        public List<Grupo> buscaGruposPorTurno(int corte, String turno){
           List<Grupo> grupos = new ArrayList<Grupo>();
        PreparedStatement psBusca = null;
        String query = "SELECT * FROM tc_grupos WHERE corte = ? AND turno = ?";
        try{
          psBusca = (PreparedStatement) con.prepareStatement(query);
            //stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          psBusca.setInt(1, corte);  
          psBusca.setString(2, turno);
           // System.out.println(query);
            rs = psBusca.executeQuery();
            
            while(rs.next()) {
                grupo = new Grupo();
                grupo.setIdGrupo(rs.getInt("id_grupo"));
                grupo.setGrado(rs.getInt("grado"));
                grupo.setGrupo(rs.getString("grupo"));
                grupo.setTurno(rs.getString("turno"));
                grupo.setCorte(rs.getInt("corte"));
                grupos.add(grupo);  
            }
            
   
        }catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println("Error SQL");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        return grupos;   
        }
        
        public List<Integer> buscaGruposConAlumnos(int corte){
          List<Integer> grupos = new ArrayList<Integer>();
           PreparedStatement psBusca = null;
          Integer idGrupo;
           String query = "SELECT g.id_grupo"
                + " FROM tc_grupos g INNER JOIN tc_listas l"
                + " ON g.id_grupo = l.id_grupo"
                + " WHERE g.corte = ? GROUP BY g.id_grupo";
        try{
          psBusca = (PreparedStatement) con.prepareStatement(query);
          psBusca.setInt(1, corte);  
          rs = psBusca.executeQuery();
          while(rs.next()) {
          //  grupo = new Grupo();
            idGrupo = rs.getInt("id_grupo");
            grupos.add(idGrupo);  
          }
            
   
        }catch (SQLException ex) {
           Logger.getLogger(GruposDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
          return grupos;
        }
      }
    
}