<%@page import="catalogos.alumnos.Alumno"%>
<%@page import="catalogos.grupos.GruposDAO, catalogos.grupos.Grupo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="catalogos.listas.ListasDAO"%>
<%@page import="catalogos.listas.listasUtil"%>
<script src="Catalogos/Listas/listas.js" ></script>
<!script type="text/javascript" src="JavaScript/colorpicker/jscolor.js"script-->


<span style="color:black;font-size:1.5em">
<%
    ListasDAO dao =new ListasDAO(request.getSession());
    Grupo gdao = null;
    listasUtil util = new listasUtil(request.getSession());
    int corte = Integer.parseInt(request.getParameter("corte")!=null?request.getParameter("corte"):"0");
    int id_grupo = Integer.parseInt(request.getParameter("grupo")!=null?request.getParameter("grupo"):"0");
    dao=dao.mapRqt(request);
    int[] cortes = util.getCortes();
    List<Grupo> grupos = util.getgrupos(corte);
    List<ListasDAO> lista = dao.getDatos(id_grupo);
    List<Alumno> alumnos =null;
        
%>
<form id="datos" action="">
    <input type="hidden" id="o" value="<%=request.getParameter("o")%>"/>    
Corte:
<select id="corte">
    <option>Elige corte</option>
    <% if(cortes!=null && cortes.length>0){ 
        for(int i=0; i<cortes.length; i++){ %>
         <option value="<%=cortes[i]%>" <%=corte!=cortes[i]?"":"selected"%>><%=cortes[i]%></option>
      <%}
    }
    else{%>
        <option>No hay cortes</option>
  <%}%>
</select> 

Turno:
<select id="turno">
    <option value="M">Matutino</option>
    <option value="V">Vespertino</option>
</select>

Grupo:
<select id="grupo" >
    <option>Elija el grupo</option>
   <%if( grupos!=null && !grupos.isEmpty() ){
        for (int i=0; i<grupos.size(); i++){
           gdao=grupos.get(i);%>
           <option value="<%=gdao.getIdGrupo()%>" <%=gdao.getIdGrupo()==id_grupo?"selected":"" %>>
               <%=gdao.getGrado()+gdao.getGrupo()+gdao.getTurno()%>
           </option> 
      <% gdao=null;
        }
    }
    else{%>
        <option>No hay grupos</option>
  <%}%>  
</select></span>  

<hr>
<span style="color:black;">
    Numero de Lista: 
    <select id="no-lista">    
     <%if(lista!=null){
        for(int j=1; j<=50; j++){
         boolean esta=false;   
         for(int i=0; i<lista.size(); i++){
             if(lista.get(i).getNo_lista()==j){
                esta=true;
                break;
             }   
          }
          if(!esta)
            out.println("<option value=\""+j+"\">"+j+"</option>");
          }
      }
      else{   //no hay elementos en la lista los pintamos todos
      for(int m=1; m<=100; m++)
          out.println("<option value=\""+m+"\">"+m+"</option>");
      }
      %>
    </select>  
    Numero de expediente: 
    <select id="alumno">
      <%if(corte!=0){
          alumnos = util.getAlumnos(corte);
          if(alumnos.size()>0){
             for(int i=0; i< alumnos.size(); i++){
                Alumno al=alumnos.get(i);%>
                <option value="<%=al.getNoExpediente()%>"><%=al.getNoExpediente()+" - "+al.getNombre()%><option>
           <%   al=null;
             }
          }
          else{%>
           <option>Sin alumnos</option>
        <%}
        }
        else{%>
        <option>Sin corte</option>
      <%}%>
    </select>
    Buscar:<input type="text" id="buscar" size="5"/>
    <input type="button" value="Seleccionar" id="seleccionar"/>
    Color: 
    <!--input id="sel-color"  size="3 "class="color" /-->
    <input id="sel-color"  size="3" onChange="this.backgroundColor='#'+this.value;"/>
    Grupo estadistico: 
    <select id="grupo_estadistico">
        <option value="control">control</option>
        <option value="riesgo">riesgo</option>
    </select>
    <input type="button" value="Agregar" id="agregar"/>
 <hr>
 <center>
     <table id="lista">
         <thead>
             <tr>
                <th colspan="8">Lista de alumnos del grupo</th>
             </tr>
             <tr>
                 <th width="5%">Numero</th>
                 <th width="30%">Nombre</th>
                 <th width="10%">Expediente</th>
                 <th width="10%">Grupo</th>
                 <th width="5%">Color</th>
                 <th>Gpo est</th>
                 <th>
                    <input type="button"  class="boton" id="borrar" value="borrar" />
                </th>
                 <th>
                    <input type="button" class="boton" id="cambiar" value="cambiar" />
                 </th>
             </tr>
         </thead>
         <tbody>
             <%if(lista!=null && lista.size()>0){
                    for(int i=0; i<lista.size(); i++) {
                        dao=lista.get(i);%>
                     <tr>  
                        <td><%=dao.getNo_lista() %></td>
                        <td><%=dao.getNo_exp() %></td>
                        <td><%=dao.getNombre() %></td>
                        <td><%=dao.getNombreGrupo() %></td>
                        <td style="background-color:<%=dao.getColor()%> "></td>
                        <td><%=dao.getGrupo_estadistico()%></td>
                        <td>
                            <%if(!dao.getTieneRegistros()){%>
                            <input type="checkbox" name="num-borrar"  value="<%=dao.getNo_lista()%>"/>
                            <%}%>
                        </td>
                        <td>
                            <input type="radio" name="cambiar" value="<%=dao.getNo_lista()%>"/>
                        </td>
                     </tr>   
                    <%}
                    dao=null;
                }
                else{%>
             <tr>
                 <td colspan="8"> No se encontr&oacute; informaci&oacute;n </td>
             </tr>
              <%}%>
         </tbody>
     </table>
        <div id="cambia">
            <br>
              Seleccione el alumno a enlazar al numero de lista en este grupo
            <br><br>
            <div class="div-izquierdo">
               <p class="etiqueta"><label style="font-size: 1em">Numero: </label></p>
               <p class="etiqueta"><label style="font-size: 1em">Alumno:</label></p>
               <p class="etiqueta"><label style="font-size: 1em">Color:</label></p>
               <p class="etiqueta"><label style="font-size: 1em">Grupo estad&iacute;stico</label></p>
            </div>
            <div class="div-izquierdo">
               <p><label id="ch_numero" style="font-size: 1em"></label></p>
               <p><select id="ch_alumno"></select></p>
               <p><br>
                   <!--input class="color" id="mod-color" size="3"/></p-->
                <input id="mod-color" size="7" onChange="this.backgroundColor='#'+this.value;"/></p>
               <p><select id="id-gpo-es">
                    <option value="control">control</option>
                    <option value="riesgo">riesgo</option>
               </select></p>
            </div>    
        </div>
    </form>  
 </center>