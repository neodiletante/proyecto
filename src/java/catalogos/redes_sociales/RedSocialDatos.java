/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

import java.io.Serializable;

/**
 *
 * @author maria
 */
public class RedSocialDatos implements Serializable {
  private int idRed;
  private int noListaRefiere;
  private int noListaReferido;
  private int idRelacion;
  private int noPersonas;
  private String descDatoInteres;
  private boolean tieneDatos;
  
  public RedSocialDatos(){}
  
  public RedSocialDatos(int idRed){
    this.idRed = idRed;
  }
  
  public int getIdRed() {
    return idRed;
  }

  public void setIdRed(int idRed) {
    this.idRed = idRed;
  }

  public int getIdRelacion() {
    return idRelacion;
  }

  public void setIdRelacion(int idRelacion) {
    this.idRelacion = idRelacion;
  }

  public int getNoListaReferido() {
    return noListaReferido;
  }

  public void setNoListaReferido(int noListaReferido) {
    this.noListaReferido = noListaReferido;
  }

  public int getNoListaRefiere() {
    return noListaRefiere;
  }

  public void setNoListaRefiere(int noListaRefiere) {
    this.noListaRefiere = noListaRefiere;
  }

  public int getNoPersonas() {
    return noPersonas;
  }

  public void setNoPersonas(int noPersonas) {
    this.noPersonas = noPersonas;
  }

  public String getDescDatoInteres() {
    return descDatoInteres;
  }

  public void setDescDatoInteres(String descDatoInteres) {
    this.descDatoInteres = descDatoInteres;
  }

  public boolean isTieneDatos() {
    return tieneDatos;
  }

  public void setTieneDatos(boolean tieneDatos) {
    this.tieneDatos = tieneDatos;
  }

  @Override
  public boolean equals(Object obj) {
    
    if ( this.getIdRed() ==  ((RedSocialDatos)obj).getIdRed()){
      return true;
    }
    else{
      return false;
    }
  }
  
  
  
}