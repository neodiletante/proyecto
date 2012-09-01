<%-- 
    Document   : redes_sociales
    Created on : 15/02/2012, 10:48:00 AM
    Author     : ulises
--%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!--script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales_reg.js" /-->
  <script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales.js" />
  <script type="text/javascript" src="Catalogos/Redes_sociales/redes.js" />

  <center>
    <jsp:useBean id="cortes" class="java.util.ArrayList" scope="request" />
   Corte: <select id="select-cortes" class="combo-cortes">
      <option value="" selected="true">Corte</option>
      <c:forEach var="corte" items="${cortes}">
        <option value="${corte}"> ${corte} </option>  
      </c:forEach>
    </select>
   Turno: <select id="select-turno" class="combo-turnos">
      <option value="" selected="true">Turno</option>
      <option value="M"> M </option>  
      <option value="V"> V </option>
    </select>
      
    Grupo:
    <select id="select-grupos" class="combo-grupos">
      <option value="" selected="true"></option>
    </select>
    No. de Lista
    <select id="no-lista">
        <option value="" selected="true"></option>
    </select>
    
    Red actual:
    <select id="red-actual">
        <option>Selecciona no.</option>
    </select>
    
    <div id="lista-redes" class="columnas" style="overflow: scroll;">
      
      <hr>
      <br/>
      
    </div>

    <br/> 
    <hr>
    <button class="ui-button" id="btn-iniciar-red"  hidden="true">Guardar</button> 
    <button class="ui-button" id="btn-examinar-redes" hidden>Ver redes</button> 
    <button class="ui-button" id="btn-modificar-red">Modificar</button>
    <button class="ui-button" id="btn-borrar-red">Borrar</button> 
    <button class="ui-button" id="btn-agregar-datos">Datos de interés</button> 
  
     