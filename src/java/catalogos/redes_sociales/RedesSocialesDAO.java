/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class RedesSocialesDAO {
  Connection con = null;
  
  public RedesSocialesDAO(Connection con){
    this.con = con;
  }
  
  public void insertaRedSocial(RedSocialReg reg){
    System.out.println("En  DAO, insertando red social");
    System.out.println(reg);
    String qInserta = "INSERT INTO tc_redes_sociales VALUES (0,?,?,?)";
    PreparedStatement psInserta = null;
    try {
      psInserta = con.prepareStatement(qInserta);
      psInserta.setInt(1, reg.getIdGrupo());
      psInserta.setInt(2, reg.getNoListaRefiere());
      psInserta.setInt(3, reg.getNoListaReferido());
      psInserta.execute();
    } catch (SQLException ex) {
      //Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
      ex.printStackTrace();
    }
  }
  
  public void insertaDatosRedSocial(RedSocialDatos rsd){
     String qInserta = "INSERT INTO tr_redes_sociales VALUES (?,?,?,?,?,?)";
    PreparedStatement psInserta = null;
    try {
      psInserta = con.prepareStatement(qInserta);
      psInserta.setInt(1, rsd.getIdRed());
 //     psInserta.setInt(2, rsd.getNoLista1());
 //     psInserta.setInt(3, rsd.getNoLista2());
      psInserta.setInt(4, rsd.getIdRelacion());
 //     psInserta.setInt(5, rsd.getDatoInteresRed());
 //     psInserta.setInt(6, rsd.getDatoInteresSoc());
      psInserta.execute();
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public List<RedSocialReg> buscaRedes(RedSocialReg reg){
    String query = "SELECT id_red, id_grupo, no_lista_refiere FROM tc_redes_sociales "
            + "WHERE id_grupo = ? AND no_lista_refiere = ?";
    PreparedStatement psBuscaRedes = null;
    ResultSet rs = null;
    List<RedSocialReg> listaRedSocial = new ArrayList<RedSocialReg>();//null;
    RedSocialReg redSocialReg;
    try {
      psBuscaRedes = con.prepareStatement(query);
      psBuscaRedes.setInt(1, reg.getIdGrupo());
      psBuscaRedes.setInt(2, reg.getNoListaRefiere());
      rs = psBuscaRedes.executeQuery();
      while(rs.next()){
      redSocialReg = new RedSocialReg();
      redSocialReg.setIdRed(rs.getInt("id_red"));
      redSocialReg.setIdGrupo(rs.getInt("id_grupo"));
      redSocialReg.setNoListaRefiere(rs.getInt("no_lista_refiere"));
      listaRedSocial.add(redSocialReg);
      }
      //listaRedSocial = mapRedSocialReg(rs);
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
       return listaRedSocial;
    }
  }
  
   public List<RedSocialDatos> buscaDatosRedes(RedSocialReg reg){
    String query = "SELECT reg.id_red, COUNT(*) AS no_personas, reg.no_lista_referido"
            + " FROM tc_redes_sociales reg, tr_redes_sociales dat"
            + " WHERE reg.id_grupo = ? AND reg.no_lista_refiere = ?";
           
    PreparedStatement psBuscaRedes = null;
    ResultSet rs = null;
    RedSocialDatos rsd;
    List<RedSocialDatos> listaDatosRed = new ArrayList<RedSocialDatos>();
    try {
      psBuscaRedes = con.prepareStatement(query);
      psBuscaRedes.setInt(1, reg.getIdGrupo());
      psBuscaRedes.setInt(2, reg.getNoListaRefiere());
      rs = psBuscaRedes.executeQuery();
      while(rs.next()){
        rsd = new RedSocialDatos();
        rsd.setIdRed(rs.getInt("id_red"));
        rsd.setNoPersonas(rs.getInt("no_personas"));
        rsd.setNoListaReferido(rs.getInt("no_lista_referido"));
        listaDatosRed.add(rsd);
      }
      
      //listaRedSocial = mapRedSocialReg(rs);
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
       return listaDatosRed;
    }
  }
  
  public List mapRedSocialReg(ResultSet rs) throws SQLException{
    RedSocialReg redSocialReg = new RedSocialReg();
    List<RedSocialReg> listaRedesSociales = new ArrayList<RedSocialReg>();
    while(rs.next()){
      redSocialReg = new RedSocialReg();
      redSocialReg.setIdRed(rs.getInt("id_red"));
      redSocialReg.setIdGrupo(rs.getInt("id_grupo"));
      redSocialReg.setNoListaRefiere(rs.getInt("no_lista_refiere"));
      listaRedesSociales.add(redSocialReg);
    }
    return listaRedesSociales;
  }
} 
