<%-- 
    Document   : reporte_alumnos
    Created on : 19/05/2012, 01:38:03 PM
    Author     : maria
--%>
<script type="text/javascript" src="Catalogos/Alumnos/alumnos.js" />
<script type="text/javascript" src="Catalogos/Alumnos/reporte_alumnos.js" />

<table id="info-alumno">
  
</table>
 <button class="ui-button" id="btn-redes-reporta">Reporta</button> 
 <button class="ui-button" id="btn-redes-referido">Referido</button> 
 <button class="ui-button" id="btn-redes-participa">Participa</button>
 <button class="ui-button" id="btn-borra-datos-redes">Borrar registros</button>
 <button class="ui-button" id="btn-genera-reporte">Generar reporte</button>
<span id="btn-reporte"></span>

  <table id="tabla-alumno-en-redes">
   <thead>
     <th colspan='6'>Alumno en Redes</th>
     </thead>
     <tbody>
       <tr>
         <th>Id Red</th>
         <th>Integrantes</th>
         <!--th><button class='ui-button' id='btn-borrar-red'>Borrar</button></th-->
         <th>Borrar</th>
         </tr>
 </table>
   <div id="modal-borra-redes">    
        <div class="div-izquierdo">    
            <p><label>Contraseña</label>
        </div>
        <div class="div-izquierdo">
       
            <p><input id="input-passwd" type="password" name="passwd" />
        </div>
    </div>
 
