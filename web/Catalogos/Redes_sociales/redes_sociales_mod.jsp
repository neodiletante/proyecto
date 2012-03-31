<%-- 
    Document   : redes_sociales_mod
    Created on : 15/02/2012, 01:24:08 PM
    Author     : ulises
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales_mod.js" />
<script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales.js" />
   <center>
    
    <jsp:useBean id="cortes" class="java.util.ArrayList" scope="request" />
    <select id="select-cortes" class="combo-cortes">
      <option value="" selected="true">Corte</option>
      <c:forEach var="corte" items="${cortes}">
        <option value="${corte}"> ${corte} </option>  
      </c:forEach>
      
    </select>
    <select id="select-turno" class="combo-cortes">
      <option value="" selected="true">Turno</option>
      <option value="M"> M </option>  
      <option value="V"> V </option>
    </select>
     
    <select id="select-grupos">
      <option value="" selected="true"></option>
        
    </select>
    
    <select id="no-lista">
        <option value="" selected="true"></option>
    </select>
      <br />
    <br />
    <hr>
    <table id="tabla-redes">
        <thead>
            <th colspan="6">Redes actuales</th>
        </thead>
        <tbody>
            <tr>
                <th>Id Red</th>
                <th>No. personas</th>
                <th>Modificar</th>
                <th>Borrar</th>
                <th>No. lista referido</th>
            </tr>
           
        </tbody>
    </table>
    <hr>
    <button class="ui-button" id="btn-modificar-red">Modificar</button>
    <button class="ui-button" id="btn-borrar-red">Borrar</button> 
    <button class="ui-button" id="btn-agregar-datos">Datos de inter�s</button> 
