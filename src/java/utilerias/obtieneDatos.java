/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import catalogos.grupos.Grupo;
import catalogos.grupos.GruposDAO;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author daniel
 */
public class obtieneDatos {
    Connection conexion=null;
    public obtieneDatos(Connection con){
        conexion=con;
    }
    
    public List obtieneListaCortes(){ 
        GruposDAO gDAO = new GruposDAO(conexion);
        return gDAO.consultaCortes();
    }
    
    public List<Grupo> obtieneGruposCorte(int corte,String turno){
         GruposDAO gDAO = new GruposDAO(conexion);
         return gDAO.buscaGruposPorTurno(corte, turno);
    }
    
    public String generaReporte(HttpServletRequest rqt,String nombreReporte, String ruta, HashMap<String,Object> params){
      JasperPrint jp=null;
      String nombreArchivoGenerado=null;
        try {
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject (
                    new File(rqt.getSession().getServletContext().getRealPath("/Reportes/"+nombreReporte+".jasper")));
             String nombre = nombreReporte+ReportUtils.nombreArchivoAnalisisCorte(
             (Integer)params.get("corte") );
             jp=JasperFillManager.fillReport(jasperReport,params, conexion);
             JasperExportManager.exportReportToPdfFile(jp,
                     rqt.getSession().getServletContext().getRealPath(
                     ruta+nombre));
             nombreArchivoGenerado=rqt.getContextPath()+ruta+nombre;
        } catch (JRException ex) {
            Logger.getLogger(obtieneDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombreArchivoGenerado;
    }
    
     static class ReportUtils {
  public static final String DATE_FORMAT_NOW = "yyyy-MM-dd_HH-mm-ss";

  public static String now() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    return sdf.format(cal.getTime());

  }

      public static String nombreArchivoAnalisisCorte(int corte){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-MM");
        return "_"+corte+"_"+sdf.format(cal.getTime()) +".pdf";
      }
    }
}
