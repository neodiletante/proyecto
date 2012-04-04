<%-- 
    Document   : datos_interes
    Created on : 10/02/2012, 10:46:59 AM
    Author     : ulises
--%>

 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="Catalogos/Datos_interes/datos_interes.js" />
    
<center>
  <table id="tabla-grupos-actuales">
        <thead>
            <th colspan="6">Datos de interés actuales</th>
        </thead>
            <tr>
                <th>Id</th>
                <th>Descripción</th>
                <th>Borrar</th>
                <th>Modificar</th>
            </tr>
        <tbody>
            <jsp:useBean id="datosInteres" class="java.util.ArrayList" scope="request" />
            <c:forEach var="datoInteres" items="${datosInteres}">
               <tr>
                   <td class="resultado">${datoInteres.idDato}</td>
                   <td class="resultado">${datoInteres.descripcion}</td>
                   <td class="centrado">
                      <input class="check_datos" type="checkbox" value="${datoInteres.idDato}"/>
                   </td>
                   <td class="centrado">
                      <input class="radio_datos" type="radio" name="radio_datos" value="${datoInteres.idDato}"/>
                   </td>
               </tr> 
            </c:forEach>
        </tbody>
    </table>
            
    <input type="button" id="btn-borra-dato"  value="Borra dato interés"/> 
    <input type="button" id="btn-cambia-dato" value="Cambia dato interés"/> 
    <input type="button" id="btn-agrega-dato" value="Agregar dato de interés"/>
    <input type="hidden" id="accion" value="">
    
</center>   

<form method="POST">
    <div id="modal-dato">    
        <div class="div-izquierdo">    
            <p><label>Clave:</label>
            <p><label>Descripción</label>   
        </div>
        <div class="div-izquierdo">
            <p><label id="clave-siguiente" style="font-size: 1em">&nbsp;</label><br>
            <p><textarea id="descripcion-dato-interes" name="descripcion_dato"/>
        </div>
    </div>
</form>    
  