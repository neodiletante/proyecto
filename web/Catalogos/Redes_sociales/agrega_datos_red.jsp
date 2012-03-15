<%-- 
    Document   : agrega_datos_red
    Created on : 3/03/2012, 04:20:12 PM
    Author     : maria
--%>
 <script type="text/javascript" src="Catalogos/Redes_sociales/agrega_datos_red.js" />
 <table id="tabla-datos">
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
            <%--jsp:useBean id="listaRedes" class="java.util.ArrayList" scope="session" /--%>
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
    </table>