/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos.alumnos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maria
 */
public class BuscaAlumnosServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    System.out.println("En el servlet busca alumnos");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    Connection con = (Connection) session.getAttribute("conn");
    AlumnosDAO aDAO = new AlumnosDAO(con);
    List<Alumno> alumnos = aDAO.consultaAlumnos();
    //List<Alumno> alumnos = aDAO.buscaPosicionAlumnos();
    System.out.println("Número de alumnos " + alumnos.size());
    int alumnosPorTab = 10;
    int tamanioLista = alumnos.size();
    int numTabs = tamanioLista%alumnosPorTab == 0 ? tamanioLista / alumnosPorTab : tamanioLista / alumnosPorTab + 1;
    int cuentaAlumnos = 0;
    try{
    out.println("<div id='tabs'>");
    out.println("<ul>");
    for(int i=0 ; i<numTabs ; i++){
      out.println("<li><a href='#tabs-" + i+1 + "'></a>"+ (i*alumnosPorTab + 1) + " - " + ((i+1<numTabs) ? alumnosPorTab*(i+1) : alumnos.size())+ "</li>");
    }
    out.println("</ul>");
    for(int i=0 ; i<numTabs ; i++){
      
      out.println("<div id='tabs-" + i+1 + "'>");
      out.println("<table id='tabla-alumnos-actuales-" + i+1 + "'>");
      out.println("<thead>");
      out.println("<th colspan='6'>Alumnos actuales</th>");
      out.println("</thead>");
      out.println("<tbody>");
      out.println("<tr>");
      out.println("<th>No. Expediente</th>");
      out.println("<th>Nombre</th>");
      out.println("<th>Sexo</th>");
      out.println("<th>Borrar</th>");
      out.println("<th>Modificar</th>");
      out.println("</tr>");
      for(int j=0; j<alumnosPorTab ; j++){
        if(cuentaAlumnos < alumnos.size()){
        Alumno alumno = alumnos.get(cuentaAlumnos++);
        out.println("<tr>");
        out.println("<td class='resultado' id='input_noExpediente'>" + alumno.getNoExpediente() + "</td>");
        out.println("<td class='resultado' id='input_nombre'>" + alumno.getNombre() + "</td>");
        out.println("<td class='resultado' id='input_sexo'>" + alumno.getSexo() + "</td>");
        if(alumno.isTieneRegistros()){
        out.println("<td></td>");
        
        }else{
          out.println("<td class='centrado'>");
          out.println("<input class='check_alumno' type='checkbox' name='borrar'  value='" + alumno.getNoExpediente() + "'/>");
        }
        
        out.println("<td class='centrado'>");
        out.println("<input class='radio_alumno' type='radio' name='modificar' value='" + alumno.getNoExpediente() + "'/>");
        out.println("</td>");
        out.println("</tr>");
      }else{
        break;  
      }
      }
      out.println("</tbody>");
        out.println("</table>");
 
      out.println("</div>");
    }
   
    }finally{
      out.close();
    }
    
    /*
     * <div class="demo">


	
		
		<li><a href="#tabs-2">Proin dolor</a></li>
		<li><a href="#tabs-3">Aenean lacinia</a></li>
	
	
		<p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo. Vivamus sed magna quis ligula eleifend adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique tempus lectus.</p>
	</div>
	<div id="tabs-2">
		<p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
	</div>
	<div id="tabs-3">
		<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
		<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
	</div>
</div>

</div>
     */
    
    
    
    
    
  /*  
    String respuesta="";
    Alumno alumno;
    for(int i=0 ; i<alumnos.size() ; i++){
      alumno = alumnos.get(i);
      respuesta += alumno.getNoExpediente();
      respuesta += " - ";
      respuesta += alumno.getNombre();
      if (i<alumnos.size()+1){
        respuesta += ",";
      }
    }
    try {
      out.write(respuesta);
    } finally {      
      out.close();
    }*/
  }
public int divs(){
  return 0;
}
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}


