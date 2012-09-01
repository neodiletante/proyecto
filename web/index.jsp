<%-- 
    Document   : index
    Created on : 14/01/2012, 01:13:58 PM
    Author     : daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="Catalogos/Genericos/conectar.jspf" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina inicial saes</title>
        <style media="all" type="text/css">@import "Estilos/saes-ie.css";</style>
        <style media="all" type="text/css">@import "Estilos/css/dark-hive/jquery-ui-1.8.17.custom.css";</style>
        <link rel="stylesheet" media="screen" type="text/css" href="JavaScript/selector/css/colorpicker.css" />
        <script src="JavaScript/js/jquery-1.7.1.min.js"></script>  
        <script src="JavaScript/js/jquery-ui-1.8.17.custom.min.js"></script>  
        <style media="all" type="text/css">@import "Estilos/forma-dialogo.css";</style>
        <script type="text/javascript" src="JavaScript/selector/js/colorpicker.js"></script>
        <!--script type="text/javascript" src="JavaScript/colorpicker/jscolor.js"></script-->

      <%String ruta=null;
        int o= request.getParameter("o")==null?0:Integer.parseInt(request.getParameter("o"));
        switch(o){
            case 1:ruta="Catalogos/Cortes/cortes.jsp";break;
            case 2:ruta="Catalogos/Listas/modificarListas.jsp";break;
            case 3:ruta="mostrarAlumnos";break;
            case 4:ruta="muestraDatosInteres?url=Catalogos/Datos_interes/datos_interes.jsp";break;
            case 5:ruta="buscaCortes?url=Catalogos/Redes_sociales/redes_sociales.jsp";break;
            case 6:ruta="buscaCortes?url=Catalogos/Redes_sociales/redes_sociales.jsp";break;
            case 7:ruta="buscaCortes?url=Catalogos/Grupos/grupos.jsp";break;
            case 12:ruta="buscaCortes?url=Catalogos/Redes_sociales/agrega_datos_red.jsp";break;
            case 13:ruta="Estadisticas/analizarGrupoEnCorte.jsp";break;
            case 14:ruta="Estadisticas/diseñarReporte.jsp";break;
           // default:ruta="Catalogos/Listas/colorpicker.jsp";break;
        }%> 

        <script type="text/javascript">
            $(function(){
             //$.datepicker.setDefaults( $.datepicker.regional[ "es" ] );
             <%if(ruta!=null){%>
                $('#_principal').load('<%=ruta%>');
             <%}%>
            });
         </script>
    </head>
    <body>
        
        <label class="h4">Departamento de Psicolog&iacute;a</label><hr>
        <label class="h6">Sistema de registro y análisis de interacciones escolares</label>
        <%@include file="Menu/menu.jspf" %>
        <br>
        

        
        <div id="_principal" class="carga-paginas" style="overflow:auto;">
            
        </div>
        <!-- solo para probar la bd %@include file="Catalogos/Genericos/newjsp.jsp" %-->
    </body>
</html>
<!--%@include file="Catalogos/Genericos/desconectar.jspf" %-->
