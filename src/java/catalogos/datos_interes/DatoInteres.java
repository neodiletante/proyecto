/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.datos_interes;

/**
 *
 * @author ulises
 */
public class DatoInteres {
  private int idDato=0;
  private String descripcion="";

   

  public DatoInteres(){}
  
  public DatoInteres(String idDato, String descripcion){
    this.idDato = idDato!=null?Integer.parseInt(idDato):0;
    this.descripcion = descripcion;
  }
  
  public DatoInteres(String descripcion){
    this.idDato = 0;
    this.descripcion = descripcion;
  }
  
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getIdDato() {
    return idDato;
  }

  public void setIdDato(int idDato) {
    this.idDato = idDato;
  }
  
}