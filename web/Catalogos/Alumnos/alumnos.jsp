<%-- 
    Document   : alumnos
    Created on : 7/02/2012, 11:53:42 PM
    Author     : ulises
--%>

 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <script type="text/javascript" src="Catalogos/Alumnos/alumnos.js" />
 <script type="text/javascript" src="Catalogos/Alumnos/reporte_alumnos.js" />
 <br> 
<center>

    <button class="ui-button" id="btn-agrega-alumno">Nuevo alumno</button> 
    <button class="ui-button" id="btn-borra-alumno">Borra alumno</button> 
    <button class="ui-button" id="btn-cambia-alumno">Modifica alumno</button>
    <button class="ui-button" id="btn-reportar">Reportar</button>
    Buscar:<input type="text" id="buscarAlumno" size="5"/>
   <button class="ui-button" id="btn-busca-alumno">Busca alumno</button> 
   <!--En este div se muestra la tabla de alumnos, separados en tabs--> 
   <div id="tabs">

   </div>
   <div id="forma-agrega-alumno" >
     <form>
       <fieldset >
        <div class="div-izquierdo">
                  <p class="etiqueta"><label>No. expediente: </label>
                  <p class="etiqueta"><label>Nombre: </label>
                  <p class="etiqueta"><label>Sexo: </label>
               </div>
               <div>
                 <p><input type="text" id="noExpediente" size="10" maxlength="20"/>
                 <p><input type="text" id="nombre" size="30" maxlength="45"/>
                 <p><input class="radio_sexo" id="sexoM" type="radio" name="sexo"  value="Mujer"/><label>Mujer      </label>
                 <input class="radio_sexo" id="sexoH" type="radio" name="sexo" value="Hombre"/><label>Hombre</label>
               </div>
            </fieldset>
       
        </form>   
      </div>
   <div id="forma-reporta-alumno">
     <p class="etiqueta"><label>Mostrar actividad del alumno en redes</label>
     <form>
       <fieldset >
        <div class="div-izquierdo">
          <p class="etiqueta"><label>No. expediente: </label>
          <p class="etiqueta"><label>Seleccionar corte: </label>
        </div>
        <div>
          <p><input type="text" id="noExpDatos" size="10" maxlength="20"/>
            <p><select id ="corteDatos" size="1">
            </select>
        </div>
      </fieldset>
     </form>  
   </div>