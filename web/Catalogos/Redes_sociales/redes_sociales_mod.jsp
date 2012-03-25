<%-- 
    Document   : redes_sociales_mod
    Created on : 15/02/2012, 01:24:08 PM
    Author     : ulises
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales_mod.js" />
   <center>
    
    <jsp:useBean id="cortes" class="java.util.ArrayList" scope="session" />
    <select id="select-cortes-mod" class="combo-cortes">
      <option value="" selected="true">Corte</option>
      <c:forEach var="corte" items="${cortes}">
        <option value="${corte}"> ${corte} </option>  
      </c:forEach>
      
    </select>
    <select id="select-turno-mod" class="combo-cortes">
      <option value="" selected="true">Turno</option>
      <option value="M"> M </option>  
      <option value="V"> V </option>
    </select>
      <jsp:useBean id="grupos" class="java.util.ArrayList" scope="session" />
    <select id="select-grupos-mod">
      <option value="" selected="true">Grupo</option>
      <c:forEach var="grupo" items="${grupos}">
        <option value="${grupo.idGrupo}"> ${grupo.grado} ${grupo.grupo} ${grupo.turno} </option>  
      </c:forEach>
      
    </select>
      <jsp:useBean id="lista" class="java.util.ArrayList" scope="session" />
    <select id="no-lista-mod">
      <option value="" selected="true">No lista refiere</option>
      <c:forEach var="lista" items="${lista}">
        <option value="${lista.no_lista}"> ${lista.no_lista} </option>  
      </c:forEach>
      </select>
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
            <jsp:useBean id="listaRedes" class="java.util.ArrayList" scope="session" />
            <jsp:useBean id="redesConRegistros" class="java.util.ArrayList" scope="session" />
            <c:forEach var="red" items="${listaRedes}">
               <tr>
                <td class="resultado" id="id-red">${red.idRed}</td>
                <td class="resultado" id="no-personas">${red.noPersonas}</td>
                <td class="centrado">
                    <input class="radio_red" type="radio" name="modificar" id="modificar_red" value="${red.idRed}"/>
                </td>
                
                <c:choose>
                  <c:when test='${red.tieneDatos == false}'>
                    <td class="centrado">
                      <input class="check_red" type="checkbox" name="borrar" id="borrar_red" value="${red.idRed}"/>
                    </td>
                  </c:when>
                  <c:otherwise>
                    <td></td>
                  </c:otherwise>
                </c:choose>
                
                
                
                
                <td class="resultado" id="no-lista-referido">${red.noListaReferido}</td>
               </tr> 
            </c:forEach>
        </tbody>
    </table>
            <hr>
    <button class="ui-button" id="btn-modificar-red">Modificar</button>
    <button class="ui-button" id="btn-borrar-red">Borrar</button> 
    <button class="ui-button" id="btn-agregar-datos">Datos de interés</button> 
