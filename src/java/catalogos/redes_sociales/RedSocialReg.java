/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.redes_sociales;

/**
 *
 * @author maria
 */
public class RedSocialReg {
  private int idRed;
  private int idGrupo;
  private int noListaRefiere;
  private int noListaReferido;

  public RedSocialReg(){}
  
  public RedSocialReg(String idGrupo, String noListaRefiere, String noListaReferido){
    this.idGrupo = Integer.parseInt(idGrupo);
    this.noListaRefiere = Integer.parseInt(noListaRefiere);
    this.noListaReferido = Integer.parseInt(noListaReferido);
  }
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdRed() {
        return idRed;
    }

    public void setIdRed(int idRed) {
        this.idRed = idRed;
    }

    public int getNoListaRefiere() {
        return noListaRefiere;
    }

    public void setNoListaRefiere(int noListaRefiere) {
        this.noListaRefiere = noListaRefiere;
    }

  public int getNoListaReferido() {
    return noListaReferido;
  }

  public void setNoListaReferido(int noListaReferido) {
    this.noListaReferido = noListaReferido;
  }

  @Override
  public String toString() {
    return this.getIdGrupo() + " " + this.getNoListaRefiere() + " " + this.getNoListaReferido();
  }
  
  
}
