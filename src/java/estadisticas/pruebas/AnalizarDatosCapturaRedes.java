/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estadisticas.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import cern.jet.stat.Probability;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/**
 *
 * @author daniel
 */
public class AnalizarDatosCapturaRedes {
    private int idGrupo=0;
    private Connection conn=null;
    private boolean uniformidad=false;
    private static final Logger log = Logger.getLogger(AnalizarDatosCapturaRedes.class.getName());
    
    public AnalizarDatosCapturaRedes( Connection connec, int id_grupo){
        idGrupo=id_grupo;
        conn=connec;
        procesarDatos();
    }
    
    public AnalizarDatosCapturaRedes(int id_Grupo){
        idGrupo=id_Grupo;
        obtieneConeccion();
        procesarDatos();
        liberaConeccion();
    }
    
    private void procesarDatos(){
        int totalFrecuencia=0;
        double media=0;
        double propPromedio=0;
        DatosCapturaRedes RedActual=null;
        double[] frecuencias = null;
        DescriptiveStatistics stat= new DescriptiveStatistics();
        /*obtener redes reales y frecuencia*/
        ArrayList<DatosCapturaRedes> datosFrecuencia= analizaDatosCaptura();
        if (datosFrecuencia!=null && datosFrecuencia.size()>0){
            frecuencias= new double[datosFrecuencia.size()];
            /*calcular frecuencia total*/
            ListIterator it = datosFrecuencia.listIterator();
            int i=0;
            while(it.hasNext()){
                RedActual=(DatosCapturaRedes)it.next();
                totalFrecuencia+=RedActual.getFrecuencia();
                frecuencias[i++]=RedActual.getFrecuencia();
                stat.addValue(RedActual.getFrecuencia());
                RedActual=null;
            }
            /*calcular media*/
            media=(double)totalFrecuencia/(double)datosFrecuencia.size();
            /*calcular proporciones*/
            while(it.hasPrevious()){
                RedActual=(DatosCapturaRedes)it.previous();
                RedActual.setProporcion((double)RedActual.getFrecuencia()/(double)totalFrecuencia);
                propPromedio+=(double)RedActual.getFrecuencia()/(double)datosFrecuencia.size();
                it.set(RedActual);
                RedActual=null;
            }        
            /*-----probar normalidad-----*/
            double frecuenciaEsperada= (double)totalFrecuencia/(double)datosFrecuencia.size();
            int gradosDeLibertad=datosFrecuencia.size()-1;
            /*calculo de x cuadrada 
             * x**2=suma de todas ((frecuenciaReportada-frecuenciaEsperada)**2)/frecuenciaEsperada */
            double xCuadradaCalculada=0;
            /*Para cada uno calcular x cuadrada i y sumar*/
            while(it.hasNext()){
                RedActual=(DatosCapturaRedes)it.next();
                xCuadradaCalculada+=Math.pow(RedActual.getFrecuencia()-frecuenciaEsperada,2)/frecuenciaEsperada;
                RedActual=null;
            }        
            /*calcular X2 si xCuadradaCalculada<xCuadradaTabla es uniforme*/
            double x2Tabla=Probability.chiSquare(gradosDeLibertad, 0.05);/*0.05 nivel de significancia*/
            /*Probar uniformidad*/
            uniformidad=xCuadradaCalculada<x2Tabla;
            /*borrar información anterior */
            this.borrarRegistrosAnalisisAnteriorGrupo(idGrupo);
            /*guardar en tablas*/
            StringBuilder queryGpo = new StringBuilder("");
            StringBuilder queryRedes = new StringBuilder("");
            queryRedes.append("insert into tr_analisis_redes(id_grupo, frecuencia, proporcion, integrantes)values(?,?,?,?)");
            queryGpo.append("insert into tr_analisis_grupos(id_grupo, uniformidad, media, proporcion_media, varianza)values(?,?,?,?,?)");
            PreparedStatement pstm=null;
            try{
                /*insertar analisis redes*/
                pstm=conn.prepareCall(queryRedes.toString());
                while(it.hasPrevious()){
                    RedActual=(DatosCapturaRedes)it.previous();   
                    pstm.setInt(1, RedActual.getId_grupo());
                    pstm.setInt(2, RedActual.getFrecuencia());
                    pstm.setDouble(3, RedActual.getProporcion());
                    pstm.setString(4, RedActual.getIntegrantesString());
                    if( !(pstm.executeUpdate()>0) ){
                        log.log(Level.WARNING,"Error insertando red aqui",pstm);
                    }
                    RedActual=null;
                }
                pstm.close();
               /*insertar en analisis grupo*/
                pstm=conn.prepareCall(queryGpo.toString());
                pstm.setInt(1,idGrupo);
                pstm.setString(2, uniformidad?"S":"N");
                pstm.setDouble(3, media);
                pstm.setDouble(4, propPromedio);
                pstm.setDouble(5, stat.getVariance());
                System.out.println("Stdv= "+stat.getStandardDeviation()+" Varian="+stat.getVariance());
                /*agregar*/
                pstm.executeUpdate();
            }catch(Exception ex){
                log.log(Level.WARNING,"Error: ",ex);
            }finally{
                try{
                   pstm.close(); 
                }catch(Exception ex){}
            }
        }else{
            log.log(Level.WARNING,"Error: Lista de redes vacía");
        }
    }
    
    public ArrayList<DatosCapturaRedes>  analizaDatosCaptura(){
        /*obtiene lista de redes */
        DatosCapturaRedes datos=new DatosCapturaRedes(idGrupo, conn);
        ArrayList<DatosCapturaRedes> LstRedes=datos.getDatosCapturaRedes(idGrupo);
        /* compara elemento por elemento cada lista*/ 
        ArrayList<Integer> redesRepetidas=new ArrayList<Integer>();
        ArrayList<DatosCapturaRedes> RedesReales = new ArrayList<DatosCapturaRedes>();
        DatosCapturaRedes aux1=null;
        DatosCapturaRedes aux2=null;
        if(LstRedes!=null){
            for(int i=0;i<LstRedes.size(); i++){
                aux1=LstRedes.get(i);
                if(!redesRepetidas.contains(aux1.getId_red())){
                    aux1.setFrecuencia(1);
                    for(int k=i+1; k<LstRedes.size(); k++){
                      aux2=LstRedes.get(k);
                      if(!redesRepetidas.contains(aux2.getId_red())){
                            boolean identico=aux1.getIntegrantesString().equalsIgnoreCase(aux2.getIntegrantesString());
                           /* for(int m=0;m<aux1.getIntegrantes().size(); m++){
                                if(!aux2.getIntegrantes().contains(aux1.getIntegrantes().get(m))){
                                    identico=false;
                                    break;
                                }    
                            }*/
                            if(identico){
                               redesRepetidas.add(aux2.getId_red());
                               aux1.setFrecuencia(aux1.getFrecuencia()+1);
                            }   
                       }
                      }
                      /*si no esta repetida agregar a la lista*/
                      RedesReales.add(aux1);
                      System.out.println(aux1.getId_red()+"autentica");
                }
                /**limpiar variable*/
                aux1=null;
            }
        }
    return RedesReales;
}

    private void obtieneConeccion() {
        conn=null;
        try {
           conn=conn = DriverManager.getConnection("jdbc:mysql://192.168.1.77:3306/saes?autoReconnect=true","saes","saes");
        } catch (Exception ex) {
           Logger.getLogger(AnalizarDatosCapturaRedes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void liberaConeccion(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnalizarDatosCapturaRedes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]){
        AnalizarDatosCapturaRedes analizador =new AnalizarDatosCapturaRedes(32);
    }

    private void borrarRegistrosAnalisisAnteriorGrupo(int idGrupo) {
        StringBuilder query=new StringBuilder("");
        Statement stm=null;
        query.append("delete from tr_analisis_redes where id_grupo=").append(idGrupo);
        try{
            stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stm.executeUpdate(query.toString());
            query.delete(0, query.length());
            query.append("delete from tr_analisis_grupos where id_grupo=").append(idGrupo);
            stm.executeUpdate(query.toString());
        }
        catch(Exception ex){
            log.log(Level.WARNING, "Error: ", ex);
        }finally{
            try {
                if(stm!=null){
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AnalizarDatosCapturaRedes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}