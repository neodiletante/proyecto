<%-- 
    Document   : desviacionEstandar
    Created on : 23/05/2012, 08:15:39 PM
    Author     : daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Analisis estadistico grupo</title>
        <script type="text/javascript" src="Estadisticas/analisisGrupoEnCorte.js" />
    </head>
    <%
    int idGrupo=0;
    int corte=0;
       if(session.getAttribute("idGrupo")!=null)
           idGrupo = (Integer)session.getAttribute("idGrupo");
       if(session.getAttribute("corte")!=null)
           corte = (Integer)session.getAttribute("corte");
       if(request.getParameter("id_grupo")!=null){
           idGrupo=Integer.parseInt(request.getParameter("id_grupo"));
           session.setAttribute("id_grupo", idGrupo);
       }
       if(request.getParameter("corte")!=null){
           corte=Integer.parseInt(request.getParameter("corte"));
           session.setAttribute("corte", corte);
       }   
       String turno=request.getParameter("turno");
    %>
    <body>
        <span style="font-size:1.5em">Analisis de un grupo dentro de un corte seleccionado.</span>
        <hr/>
        corte:<select id="corte"></select>
        turno:<select id="turno">
            <option>Selecciona turno</option>
            <option value="M"<%= (turno!=null&&turno.equals("M")?"selected":"") %>>M</option>
                <option value="V" <%= (turno!=null&&turno.equals("V")?"selected":"") %>>V</option>
              </select>
        grupo:<select id="id_grupo"></select>
        <input id="analizar" type="button" value="Analizar"/>
        <input id="imprimir" type="button" value="Generar reporte del corte"/>
        <hr>
        <center>
            <div id="capaArchivo">
                <img id="espera"></img>
            </div>
            <table>
                <thead>
                    <tr>
                        <th colspan="5"> Resumen del analisis del grupo </th>
                    </tr>
                </thead>
                <tbody  id="tabla-redes-reales">
                </tbody>
            </table>
        </center>
    </body>
</html>
