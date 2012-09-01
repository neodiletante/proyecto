/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estadisticas.pruebas.servlet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class RedesAnalisisGrupos {
    private int idGrupo=0;
    private boolean uniformidad=false;
    private double media=0.0d;
    private double varianza=0.0d;
    private double proporcionMedia=0.0d;
    
    /*parsea una lista de un rst con informacion de grupos*/
    public ArrayList<RedesAnalisisGrupos> parseRstLst(ResultSet rst){
       ArrayList<RedesAnalisisGrupos> ListaRedes = null;
       RedesAnalisisGrupos red=null;
       try {
            if(rst.first()){
                ListaRedes = new ArrayList<RedesAnalisisGrupos>();
                do{
                   red= new RedesAnalisisGrupos();
                   red.setIdGrupo(rst.getInt("id_grupo"));
                   red.setMedia(rst.getDouble("media"));
                   red.setProporcionMedia(rst.getDouble("proporcion_media"));
                   red.setVarianza(rst.getDouble("varianza"));
                   red.setUniformidad(rst.getString("uniformidad").equalsIgnoreCase("S"));
                   ListaRedes.add(red);
                   red=null;
                }while(rst.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(RedesAnalisisGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListaRedes;
    }

    /*parsea una lista de un rst con informacion de grupos*/
    public RedesAnalisisGrupos parseRst(ResultSet rst){
       RedesAnalisisGrupos red=null;
       try {
            if(rst.first()){
               red= new RedesAnalisisGrupos();
               red.setIdGrupo(rst.getInt("id_grupo"));
               red.setMedia(rst.getDouble("media"));
               red.setProporcionMedia(rst.getDouble("proporcion_media"));
               red.setVarianza(rst.getDouble("varianza"));
               red.setUniformidad(rst.getString("uniformidad").equalsIgnoreCase("S"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RedesAnalisisGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return red;
    }    
    
    
    
    /**
     * @return the idGrupo
     */
    public int getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * @return the uniformidad
     */
    public boolean isUniformidad() {
        return uniformidad;
    }

    /**
     * @param uniformidad the uniformidad to set
     */
    public void setUniformidad(boolean uniformidad) {
        this.uniformidad = uniformidad;
    }

    /**
     * @return the media
     */
    public double getMedia() {
        return media;
    }

    /**
     * @param media the media to set
     */
    public void setMedia(double media) {
        this.media = media;
    }

    /**
     * @return the varianza
     */
    public double getVarianza() {
        return varianza;
    }

    /**
     * @param varianza the varianza to set
     */
    public void setVarianza(double varianza) {
        this.varianza = varianza;
    }

    /**
     * @return the proporcionMedia
     */
    public double getProporcionMedia() {
        return proporcionMedia;
    }

    /**
     * @param proporcionMedia the proporcionMedia to set
     */
    public void setProporcionMedia(double proporcionMedia) {
        this.proporcionMedia = proporcionMedia;
    }

}