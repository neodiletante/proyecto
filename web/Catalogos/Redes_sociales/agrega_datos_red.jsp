<%-- 
    Document   : agrega_datos_red
    Created on : 3/03/2012, 04:20:12 PM
    Author     : maria
--%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <script type="text/javascript" src="Catalogos/Redes_sociales/agrega_datos_red.js" />
 <center>
  <jsp:useBean id="cortes" class="java.util.ArrayList" scope="session" />
    <select id="select-cortes-add" class="combo-cortes">
      <option value="" selected="true">Corte</option>
      <c:forEach var="corte" items="${cortes}">
        <option value="${corte}"> ${corte} </option>  
      </c:forEach>
      
    </select>
    <select id="select-turno-add" class="combo-cortes">
      <option value="" selected="true">Turno</option>
      <option value="M"> M </option>  
      <option value="V"> V </option>
    </select>
      <jsp:useBean id="grupos" class="java.util.ArrayList" scope="session" />
    <select id="select-grupos-add">
      <option value="" selected="true">Grupo</option>
      <c:forEach var="grupo" items="${grupos}">
        <option value="${grupo.idGrupo}"> ${grupo.grado} ${grupo.grupo} ${grupo.turno} </option>  
      </c:forEach>
      
    </select>
      <jsp:useBean id="lista" class="java.util.ArrayList" scope="session" />
    <select id="no-lista-add">
      <option value="" selected="true">No lista refiere</option>
      <c:forEach var="lista" items="${lista}">
        <option value="${lista.no_lista}"> ${lista.no_lista} </option>  
      </c:forEach>
      </select>
     <%--jsp:useBean id="redes" class="java.util.ArrayList" scope="session" /--%>
      <jsp:useBean id="listaRedes" class="java.util.ArrayList" scope="session" />
     <select id="sel-red">
      <option value="" selected="true">Red</option>
      <c:forEach var="red" items="${listaRedes}">
        <option value="${red.idRed}"> ${red.idRed} </option>  
      </c:forEach>
      </select>
      <jsp:useBean id="nosLista" class="java.util.ArrayList" scope="session" />
     <select id="sel-nos-lista">
      <option value="" selected="true">Nos. lista</option>
      <c:forEach var="noLista" items="${nosLista}">
        <option value="${noLista}"> ${noLista} </option>  
      </c:forEach>
      </select>
      <jsp:useBean id="datosInteres" class="java.util.ArrayList" scope="session" />
      <select id="select-datos-interes">
      <option value="" selected="true">Dato Interés</option>
      <c:forEach var="dato" items="${datosInteres}">
        <option value="${dato.idDato}"> ${dato.descripcion} </option>  
      </c:forEach>
      </select>
      <br />
      <button class="ui-button" id="btn-actualiza-red">Agregar</button> 
      <button class="ui-button" id="btn-borra-datos">Borrar</button> 
      ${red.idRed}
      <br />
 <table id="tabla-datos">
        <thead>
            <th colspan="6">Datos actuales</th>
        </thead>
        <tbody>
            <tr>
               
                <th>No. lista referido</th>
                <th>Dato Interés</th>
                <th>Borrar</th>
            </tr>
          
           <jsp:useBean id="datosPorRed" class="java.util.ArrayList" scope="session" />
        <c:forEach var="dato" items="${datosPorRed}">
           <tr>
             
               <td class="resultado" id="td-no-lista-referido">${dato.noListaReferido}</td>
               <td class="resultado" id="td-dato-interes">${dato.descDatoInteres}</td>
            <td class="centrado">
                    <input class="check_datos" type="checkbox" name="borrar"  value="${dato.idRelacion}"/>
                </td>
           </tr>
            </c:forEach>
        </tbody>
    </table>
       