var id_grupo;
var vista = "";
var tipo_reporte = 0;

$(function(){
 function muestraRedesRefiere(){
    var parameters = {};
  tipo_reporte = 1;
  parameters.opcion = 1;
 var tr = $('#td_corte').parent().parent(); 
  if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }
  
  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  
  $.post('mostrarAlumnoEnRedes', parameters, function(data){

              $('#tabla-alumno-en-redes').html(data);
               
 $('#btn-redes-referido').show();
 $('#btn-redes-participa').show();
 $('#btn-redes-reporta').hide();
            }, 'text');  
   
 }
 
 function muestraRedesReferido(){
   tipo_reporte = 2;
    var parameters = {};
  parameters.opcion = 2;
 var tr = $('#td_corte').parent().parent();
 if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }

  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  $.post('mostrarAlumnoEnRedes', parameters, function(data){
              $('#tabla-alumno-en-redes').html(data); 
               $('#btn-redes-reporta').show();
 $('#btn-redes-referido').hide();
 $('#btn-redes-participa').show();
            }, 'text');  
   
 }

function muestraRedesParticipa(){
  tipo_reporte = 3;
   var parameters = {};
  parameters.opcion = 3;
var tr = $('#td_corte').parent().parent();
  
   if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }

  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  $.post('mostrarAlumnoEnRedes', parameters, function(data){
              $('#tabla-alumno-en-redes').html(data); 
               $('#btn-redes-reporta').show();
 $('#btn-redes-referido').show();
 $('#btn-redes-participa').hide();
            }, 'text');  
}

function imprimeReporte(){
   var parameters = {};
  parameters.opcion = 4;
  parameters.tipo_reporte = tipo_reporte;
var tr = $('#td_corte').parent().parent();
  
   if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }
  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  $.post('mostrarAlumnoEnRedes', parameters, function(data){
        $('#btn-reporte').html(data);
             
           
            }, 'text');  
}

$('#btn-redes-reporta').click(function(){
 vista = "refiere";
 muestraRedesRefiere();

});

$('#btn-redes-referido').click(function(){
 vista = "referido";
 muestraRedesReferido();

});

$('#btn-redes-participa').click(function(){
  vista = "participa";
  muestraRedesParticipa();
 
});

$('#btn-borrar-red').click(function(){
  alert("buton");
  
  
});

function borraDatosRedes(){
  var tr = $('#td_corte').parent().parent();
  var redes_borrar = $('.check_borra_red:checked');
  //  if(redes_borrar==undefined || redes_borrar.length == 0){
  //    alert("Seleccione una o más redes para borrar");
      
  //  }
 //   else{
        var id_redes = "";
        for(var i=0 ; i<redes_borrar.length ; i++){
          id_redes += redes_borrar[i].value;
          if(i+1<redes_borrar.length){
            id_redes+=",";
          }
        }
        var parameters = {};
     
        parameters.id_redes = id_redes;
         parameters.no_exp = $('td:eq(0)', tr).text();
        if(vista=="refiere" || vista=="referido"){
          parameters.borra_redes = true;
        }else{
          parameters.borra_redes = false;
         
        }
        
        
      
        $.post('borraRedesYRelaciones', parameters, function(data){
          if(vista=="refiere"){
            muestraRedesRefiere();
          }else if(vista=="referido"){
            muestraRedesReferido();
          } else {
            muestraRedesParticipa();
          } 
          
        
      }, 'text');
//    }
  
}

$('#btn-borra-datos-redes').click(function(){
  var redes_borrar = $('.check_borra_red:checked');
    if(redes_borrar==undefined || redes_borrar.length == 0){
      alert("Seleccione una o más redes para borrar");
      
    }else{
      $('#input-passwd').val("");
      $('#modal-borra-redes').dialog( "open" );
    }
});

$('#btn-genera-reporte').click(function(){
  //if (tipo_reporte == 0){
  //  alert("Seleccione primero una opción");
  //}else{
    imprimeReporte();
$('#btn-reporte').html('Generando reporte...<br />');
           $("#btn-reporte").addClass('imgGenerando');
       // var parameters ={};
       // parameters.opcion=5;
       // parameters.corte=$('#corte').val();
       // $('#espera').addClass('imgAnalizando');
       // $('#espera').html('Analizando datos...');
       // $.post('analizarGrupoEnCorteBean',parameters, function(data){
       //    parameters.opcion=4;
       //    $('#espera').html('Generando reporte...');
        //   $("#espera").addClass('imgGenerando');
        //   $.post('analizarGrupoEnCorteBean', parameters, function(data){
        //       $('#capaArchivo').html(data);
        //   })
       // })

 // }
  
});

$( '#modal-borra-redes' ).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
           Aceptar: function(){
             var parameters = {}
             parameters.nombre = "admin";
             var passwd = $('#input-passwd').val();
             parameters.passwd = passwd;
             $.post('autentificaUsuario', parameters, function(data){
               if (data == 'true'){
                  borraDatosRedes();
                  $('#modal-borra-redes').dialog( "close" );
               }else{
                 alert("Contraseña inválida");
               }
             });  
             
             
           },//guardar
           Cancelar: function() {
              $('#modal-borra-redes').dialog( "close" );
           }
        }//buttons
    });

});