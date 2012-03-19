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
  
  public int buscaIdUltimo(){
    String query = "SELECT MAX(id_red) AS id_ultimo FROM tc_redes_sociales";
    PreparedStatement psSelecciona;
    ResultSet rs;
    int idUltimo = 0;
    try {
      psSelecciona = con.prepareStatement(query);
      rs = psSelecciona.executeQuery();
      if(rs.next()){
        idUltimo = rs.getInt("id_ultimo");
      }
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return idUltimo;
    }
    
  }
  
  public void insertaElementosRed(List<String> elementos){
    System.out.println("En el DAO, inertando elementos red");
    String qInserta = "INSERT INTO tr_redes_sociales VALUES (?,?,null)";
    PreparedStatement psInserta;
    int idRed;
    idRed = buscaIdUltimo();
    for (String elemento : elementos){
      int elementoInt = Integer.parseInt(elemento);
      System.out.print("id red " + idRed);
      System.out.println(" -> elemento " + elemento);
      
      try{
        
        psInserta = con.prepareStatement(qInserta);
        psInserta.setInt(1,idRed);
        psInserta.setInt(2,elementoInt);
      //  psInserta.setInt(3,0);
        psInserta.execute();
      }catch(SQLException sqle){
       Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, sqle);
      }
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
            + " WHERE reg.id_grupo = ? AND reg.no_lista_refiere = ?"
            + " AND reg.id_red = dat.id_red"
            + " GROUP BY reg.id_red";
           
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
  
  public List buscaElementosRed(int idRed){
    List elementosRed = new ArrayList();
    String query = "SELECT no_lista FROM tr_redes_sociales WHERE id_red = ?";
    PreparedStatement psBusca;
    ResultSet rs;
    int elemento;
    try {
      psBusca = con.prepareStatement(query);
      psBusca.setInt(1, idRed);
      rs = psBusca.executeQuery();
      while(rs.next()){
        elemento = rs.getInt("no_lista");
        elementosRed.add(elemento);
      }
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return elementosRed;
  }
  public void actualizaDatosRed(int idRelacion, int idDato){
    String qInserta = "INSERT INTO tr_datos_interes VALUES(?,?)";
    PreparedStatement psInserta = null;
    try {
      psInserta = con.prepareStatement(qInserta);
      psInserta.setInt(1, idRelacion);
      psInserta.setInt(2, idDato);
       psInserta.execute();
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public int buscaIdRelacion(int idRed, int noLista){
    String query = "SELECT id_relacion FROM tr_redes_sociales WHERE id_red = ? AND no_lista = ?";
    PreparedStatement psBusca;
    ResultSet rs;
    int idRelacion = 0;
    try {
      psBusca = con.prepareStatement(query);
      psBusca.setInt(1, idRed);
      psBusca.setInt(2,noLista);
      rs = psBusca.executeQuery();
      while(rs.next()){
        idRelacion = rs.getInt("id_relacion");
      }
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return idRelacion;
    }
  }
  
  public List buscaDatosPorRed(int idRed){
    List<RedSocialDatos> datosPorRed = new ArrayList<RedSocialDatos>();
    String query = "SELECT rrs.no_lista, cdi.descripcion"
            + " FROM tr_redes_sociales rrs INNER JOIN tc_datos_interes cdi"
            + " INNER JOIN tr_datos_interes rdi"
            + " WHERE rdi.id_relacion = rrs.id_relacion"
            + " AND rdi.id_dato = cdi.id_dato AND rrs.id_red = ?";
    PreparedStatement psBusca;
    ResultSet rs;
    RedSocialDatos rsd;
    try {
      psBusca = con.prepareStatement(query);
      psBusca.setInt(1, idRed);
      rs = psBusca.executeQuery();
      while(rs.next()){
        rsd = new RedSocialDatos();
        rsd.setNoListaReferido(rs.getInt("no_lista"));
        rsd.setDescDatoInteres(rs.getString("descripcion"));
        datosPorRed.add(rsd);
      }
    } catch (SQLException ex) {
      Logger.getLogger(RedesSocialesDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      return datosPorRed;
    }
  }
} 
