/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ulises
 */
public class Alumno {
    private int noExpediente;
    private String nombre;
    private String sexo;
    
    public Alumno(){}
    
    public Alumno(int noExpediente, String nombre, String sexo){
        this.setNoExpediente(noExpediente);
        this.setNombre(nombre);
        this.setSexo(sexo);
    }
    
    public Alumno(String noExpediente, String nombre, String sexo){
        this.setNoExpediente(Integer.parseInt(noExpediente));
        this.setNombre(nombre);
        this.setSexo(sexo);
    }
    
    public int getNoExpediente() {
        return noExpediente;
    }

    public void setNoExpediente(int noExpediente) {
        this.noExpediente = noExpediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public List<Alumno> mapRst(ResultSet rst){
       List retVar = new ArrayList();
       try {
            Alumno al = null;
            if(rst.first()){
              do{
                al= new Alumno();
                al.setNoExpediente(rst.getInt("no_expediente"));
                al.setNombre(rst.getString("nombre"));
                al.setSexo(rst.getString("sexo"));
                retVar.add(al);
                al=null;
              }while(rst.next());  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVar;
    }
   
}