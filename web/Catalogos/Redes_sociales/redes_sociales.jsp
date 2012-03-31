<%-- 
    Document   : redes_sociales
    Created on : 15/02/2012, 10:48:00 AM
    Author     : ulises
--%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!--script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales_reg.js" /-->
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
        <option value="1">tre</option>
    </select>
    <span id="lista-redes">
      
      <hr>
      <br />
      
    </span>

    <br /> 
    <hr>
    <button class="ui-button" id="btn-iniciar-red"  hidden="true">Guardar</button> 
    <button class="ui-button" id="btn-examinar-redes" hidden>Ver redes</button> 
     <button class="ui-button" id="btn-modificar-red">Modificar</button>
    <button class="ui-button" id="btn-borrar-red">Borrar</button> 
    <button class="ui-button" id="btn-agregar-datos">Datos de interés</button> 
  
     