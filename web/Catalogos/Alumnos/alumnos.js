var accion = '';
//var id_grupo = null;
var no_expediente_ant;
var no_exp_g;
var corte_g;
var id_grupo_g;

function validaNombre(nombre){
  var patron = /[A-Za-z\s]/;
  return !patron.test(nombre);
}
  
  function autoCompletarAlumno(){
    // alert("presionando fnc");
     var opciones=$("#input_nombre");
     alert(opciones[0].val());
     $.each(opciones, function(index){
       alert(opciones[index].val());
       
     });
     //alert(opciones);
      var availableTags=[];
     availableTags = new Array(opciones.length);
     for (i=0; i<opciones.length; i++){
        availableTags[i]=opciones[i].text;
        alert(opciones[i]);
     }
     
     $( "#buscarAlumno" ).autocomplete({
       source: availableTags
     });
   }

   function buscaAlumnos(){
     var parameters={};
     parameters.nombre = $('#buscarAlumno').val();
     $.post('buscaAlumnos', parameters, 
              function(data){
                $('#tabs').html(data);
                $( "#tabs" ).tabs();
              },'text');
   }
$(function(){
  $('#btn-busca-alumno').click(function() {
 // buscaAlumnos();
   var parameters={};
     parameters.nombre = $('#buscarAlumno').val();
     $.post('buscaAlumnos', parameters, 
              function(data){
                $('#tabs').html(data);
                $( "#tabs" ).tabs("destroy");
                $( "#tabs" ).tabs();
              },'text');
  });  
  $( '#forma-agrega-alumno' ).dialog({
    autoOpen: false,
    height: 300,
    width: 350,
    modal: true,
    buttons: {
      'Guardar':function(){                         
        var noExpediente = $('#noExpediente').val();
        var nombre = $('#nombre').val();
        var sexo = $('.radio_sexo:checked').val();
        var mensaje = '';
        if (noExpediente == ''){
          mensaje = mensaje + 'Debe introducir un número de expediente\n';
        }else{
          if ( isNaN(noExpediente)){
            mensaje = mensaje + 'El número de expediente no es válido\n';
          }
        }
        if (nombre == ''){
          mensaje = mensaje + 'Debe introducir un nombre\n';
        }else{
          if(validaNombre(nombre)){
            mensaje = mensaje + 'El nombre no es válido\n'
          }
        }
        if (sexo == undefined){
          mensaje = mensaje + 'Debe introducir un sexo\n';
        }
        if (mensaje != ''){
          alert(mensaje);
        }else{
          var parameters={};
          parameters.noExpedienteAnt = no_expediente_ant;
          parameters.noExpediente=noExpediente;
          parameters.nombre=nombre;
          parameters.sexo=sexo;
          
          if(accion =='modificar'){
            //parameters.id_grupo=id_grupo;
            $.post('modificarAlumno', parameters, 
              function(data){
                $('#_principal').load('mostrarAlumnos');
               // alert(data);
              },'text');
          }else{
            $.post('agregarAlumno', parameters, 
              function(data){
                $('#_principal').load('mostrarAlumnos');
                alert(data);
              },'text');
          }
          $( this ).dialog( 'close' );
        } 
      }//function
    }//button,
  });
  
  $('#btn-agrega-alumno').click(function(){
    accion = 'agregar';
    $('#forma-agrega-alumno').dialog({               
      title: 'Agregar alumno'
    });
    $('#forma-agrega-alumno').dialog('open');
    $('#noExpediente').val("");
    $('#nombre').val("");
    $('#sexoM').attr('checked', false);
    $('#sexoH').attr('checked', false);
  });
  
  $('#btn-borra-alumno').click(function(){
    var checks = $('.check_alumno:checked');
    for(var i =0 ; i<checks.length ; i++){
      var parameters={};
      parameters.noExpediente = checks[i].value;
      $.post('borrarAlumno', parameters, 
      function(data){
        $('#_principal').load('mostrarAlumnos');
      },'text'   );
    }
  });
 
  $('#btn-cambia-alumno').click(function(){
    accion = 'modificar';
    $('#forma-agrega-alumno').dialog({               
      title: 'Modificar alumno'
    });
    //var check = $('.radio_alumno:checked');
    //id_grupo = check[0].value;
    $('#forma-agrega-alumno').dialog('open');
    var noExpediente = $('.radio_alumno:checked').parent().siblings("#input_noExpediente").text();
    no_expediente_ant = noExpediente;
    var nombre = $('.radio_alumno:checked').parent().siblings("#input_nombre").text();
    var sexo = $('.radio_alumno:checked').parent().siblings("#input_sexo").text();
    $('#noExpediente').val(noExpediente);
    $('#nombre').val(nombre);
    if (sexo == 'Mujer'){
      $('#sexoM').attr('checked', true);
    }else{
      $('#sexoH').attr('  checked', true);
    }
     
  });
  
  $(document).ready(function() {
    buscaAlumnos();
  });
  
  $( '#forma-reporta-alumno' ).dialog({
    autoOpen: false,
    height: 300,
    width: 350,
    modal: true,
    buttons: {
      'Mostrar':function(){ 
        //Abrir la vista para mostrar las redes a las que pertenece el alumno
        //Cargar la nueva página y mostrar los datos del alumno
      no_exp_g = 0;
      corte_g = 0;
      
        var parameters = {};
        
        no_exp_g = $('#noExpDatos').val();
        corte_g = $('#corteDatos').val();
        mensaje = "";
        if (no_exp_g == 0){
          mensaje += "Introduzca un número de expediente\n";
        }
        
        if (corte_g == 0){
          mensaje += "Introduzca un corte"
        }
        
        if (mensaje != ""){
          alert(mensaje);
        } else{
      
        parameters.no_exp = no_exp_g;
        parameters.corte = corte_g;
          $('#_principal').load('Catalogos/Alumnos/reporte_alumnos.jsp',function(){
            $.post('mostrarDatosAlumno', parameters, function(data){
              alert(data);
              var respuesta = data.split("~");
              $('#info-alumno').html(respuesta[0]);
              id_grupo_g = respuesta[1];
            }, 'text');
          });
         
       
         $( this ).dialog( 'close' );
        }  
      }
      
    }
  });
  $('#btn-reportar').click(function(){
    //alert("presionando botón reportar ");
    var parameters = {};

     $.post('cargaComboCortes', parameters, function(data){

       $('#corteDatos').html(data);
        var noExpediente = $('.radio_alumno:checked').parent().siblings("#input_noExpediente").text();
        $('#noExpDatos').val(noExpediente);
       $('#forma-reporta-alumno').dialog('open');
     }, 'text');
     
     
  });
  $('#btn-resumen').click(function(){
     var parameters = {};
     var noExpediente = $('.radio_alumno:checked').parent().siblings("#input_noExpediente").text();
     parameters.no_exp = noExpediente;
     //alert("Resumen");
     
     
      $('#_principal').load('Catalogos/Alumnos/resumen_alumno.jsp',function(){
        //alert(data);
        $.post('resumenAlumno', parameters, function(data){
          //alert(data);
          $('#sp-resumen-alumno').html(data); 
          //alert(data);
        },'text');
      });
      
      
      
      
      
     //$.post('resumenAlumno', data, callback, type);
  });
  
});