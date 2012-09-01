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
public class RedesAnalisisRedesReales {
    private int idGrupo=0;
    private int idRedReal=0;
    private String integrantes=null;
    private int total=0;
    private int frecuencia=0;
    private double proporcion=0;
    
    /**/
    public ArrayList<RedesAnalisisRedesReales> parseRst(ResultSet rst){
       ArrayList<RedesAnalisisRedesReales> ListaRedes = null;
       RedesAnalisisRedesReales red=null;
       try {
            if(rst.first()){
                ListaRedes = new ArrayList<RedesAnalisisRedesReales>();
                do{
                   red= new RedesAnalisisRedesReales();
                   red.setIdGrupo(rst.getInt("id_grupo"));
                   red.setIdRedReal(rst.getInt("id_red_real"));
                   red.setIntegrantes(rst.getString("integrantes"));
                   red.setProporcion(rst.getDouble("proporcion"));
                   red.setFrecuencia(rst.getInt("frecuencia"));
                   ListaRedes.add(red);
                   red=null;
                }while(rst.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(RedesAnalisisRedesReales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListaRedes;
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
     * @return the idRedReal
     */
    public int getIdRedReal() {
        return idRedReal;
    }

    /**
     * @param idRedReal the idRedReal to set
     */
    public void setIdRedReal(int idRedReal) {
        this.idRedReal = idRedReal;
    }

    /**
     * @return the integrantes
     */
    public String getIntegrantes() {
        return integrantes;
    }

    /**
     * @param integrantes the integrantes to set
     */
    public void setIntegrantes(String integrantes) {
        this.integrantes = integrantes;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the frecuencia
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia the frecuencia to set
     */
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * @return the proporcion
     */
    public double getProporcion() {
        return proporcion;
    }

    /**
     * @param proporcion the proporcion to set
     */
    public void setProporcion(double proporcion) {
        this.proporcion = proporcion;
    }
    
}
