<%-- 
    Document   : agrega_datos_red
    Created on : 3/03/2012, 04:20:12 PM
    Author     : maria
--%>
 <script type="text/javascript" src="Catalogos/Redes_sociales/agrega_datos_red.js" />
 
  <jsp:useBean id="cortes" class="java.util.ArrayList" scope="session" />
    <select id="select-cortes-add" class="combo-cortes-add">
      <option value="" selected="true">Corte</option>
      <c:forEach var="corte" items="${cortes}">
        <option value="${corte}"> ${corte} </option>  
      </c:forEach>
      
    </select>
    <%--select id="select-turno-add" class="combo-cortes">
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
      
    </select--%>
      <%--jsp:useBean id="lista" class="java.util.ArrayList" scope="session" />
    <select id="no-lista-add">
      <option value="" selected="true">No lista refiere</option>
      <c:forEach var="lista" items="${lista}">
        <option value="${lista.no_lista}"> ${lista.no_lista} </option>  
      </c:forEach>
      </select--%>
 <%--table id="tabla-datos">
        <thead>
            <th colspan="6">Datos actuales</th>
        </thead>
        <tbody>
            <tr>
                <th>Corte</th>
                <th>Turno</th>
                <th>Grupo</th>
                <th>No. lista refiere</th>
            </tr>
          
           <jsp:useBean id="tiposDato" class="java.util.ArrayList" scope="session" />
           <tr>
              <td class="resultado" id="id-red">1</td>
              <td class="resultado" id="no-personas">2</td>
              <td class="centrado">
                 <select id ="tipo-dato" size="1">
                   <option value="" selected="true"> Seleccione un tipo de dato </option>
                     <c:forEach var="tipoDato" items="${tiposDato}">
                       <option value="${tipoDato.tipo}"> ${tipoDato.descripcion} </option>  
                     </c:forEach>
                 </select>
              </td>
              <td class="resultado" id="no-lista-referido">3</td>
            </tr>
        </tbody>
    </table--%>