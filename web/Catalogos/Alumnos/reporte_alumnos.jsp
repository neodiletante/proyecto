<%-- 
    Document   : reporte_alumnos
    Created on : 19/05/2012, 01:38:03 PM
    Author     : maria
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="alumno" class="catalogos.alumnos.AlumnoEnRedes" scope="request" />
<div id="forma-reporta-alumno">
  
  <form>
    <fieldset >
      <div class="div-izquierdo">
        <p class="etiqueta"><label>No. expediente: </label>
        <p class="etiqueta"><label>Nombre: </label>
        <p class="etiqueta"><label>Sexo: </label>
        <p class="etiqueta"><label>Grupo: </label>
        <p class="etiqueta"><label>No. lista</label>
      </div>
      <div id="info-alumno">
        
      </div>
      </fieldset>
     </form>  
   </div>
 <button class="ui-button" id="btn-redes-reporta">Reporta</button> 
 <button class="ui-button" id="btn-redes-referido">Referido</button> 
 <button class="ui-button" id="btn-redes-participa">Participa</button>
