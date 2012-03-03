<%-- 
    Document   : agrega_datos_red
    Created on : 3/03/2012, 04:20:12 PM
    Author     : maria
--%>

 <table id="tabla-redes">
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
            <tr>
              <td class="resultado" id="id-red">${red.idRed}</td>
              <td class="resultado" id="no-personas">${red.noPersonas}</td>
              <td class="centrado">
                  <input class="radio_red" type="radio" name="modificar" id="modificar_red" value="${grupo.idGrupo}"/>
              </td>
              <td class="resultado" id="no-lista-referido">${red.noListaReferido}</td>
            </tr>
        </tbody>
    </table>