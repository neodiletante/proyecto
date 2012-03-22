<%-- 
    Document   : redes_sociales_reg
    Created on : 15/02/2012, 10:48:00 AM
    Author     : ulises
--%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <script type="text/javascript" src="Catalogos/Redes_sociales/redes_sociales_reg.js" />
  <%int cuenta = 0;%>
  <c:set var="noElementos" scope="session"/>
  <center>
    <jsp:useBean id="cortes" class="java.util.ArrayList" scope="session" />
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
      <jsp:useBean id="grupos" class="java.util.ArrayList" scope="session" />
    <select id="select-grupos">
      <option value="" selected="true">Grupo</option>
      <c:forEach var="grupo" items="${grupos}">
        <option value="${grupo.idGrupo}"> ${grupo.grado} ${grupo.grupo} ${grupo.turno} </option>  
      </c:forEach>
    </select>
      <jsp:useBean id="lista" class="java.util.ArrayList" scope="session" />
    <select id="no-lista">
      <option value="" selected="true">No. lista</option>
      <c:forEach var="lista" items="${lista}">
        <option value="${lista.no_lista}"> ${lista.no_lista} </option>  
      </c:forEach>
    </select>
     <!--select id="select-tipo">
      <option value="" selected="true">Tipo</option>
      <option value="AM"> Amistad </option>  
      <option value="AB"> Abuso </option>
    </select-->
    
    
    <br />
    <jsp:useBean id="tiposDato" class="java.util.ArrayList" scope="session" />
     <jsp:useBean id="datosInteres" class="java.util.ArrayList" scope="session" />
     <%--jsp:useBean id="datos" class="java.util.ArrayList" scope="request" /--%>
     <%--jsp:useBean id="referidos" class="java.util.ArrayList" scope="session"  /--%>
    <!--table id="tabla-alumnos">
        <thead>
            <th colspan="6">Alumnos</th>
        </thead>
        <tbody-->
          
            <!--jsp:useBean id="alumnos" class="java.util.ArrayList" scope="request" /-->
            <c:forEach var="alumno" items="${lista}">
               <!--tr>
               <td class="resultado" id="input_grado"--><label class="h4">${alumno.no_lista}</label>
              
                    <input class="check-red-social" type="checkbox" name="agrega_alumno"  value="${alumno.no_lista}"/>
              
               
                <input class="radio-referido" type="radio" name="referido"  value="${alumno.no_lista}"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
               <%--
               //Httpsession session = request.getSession();
               String elementos = (String)session.getAttribute("noElementos");
               Integer noElementos = null;
               if(elementos != ""){
                  noElementos= Integer.valueOf(elementos);
                }
               if(cuenta == noElementos){
                 cuenta=0;
               %>
               <br/>
               <%}--%>
                <!--/td>
                <
               </tr--> 
            </c:forEach>
               <br /> 
    <button class="ui-button" id="btn-iniciar-red">Guardar</button> 
    <button class="ui-button" id="btn-examinar-redes">Ver redes</button> 
  
     