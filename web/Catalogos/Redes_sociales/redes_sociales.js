var opcion ="5";
var modificando_red = 0;
var id_red_global;

$(function(){
  function getParameter(paramName) {
  var searchString = window.location.search.substring(1),
      i, val, params = searchString.split("&");

  for (i=0;i<params.length;i++) {
    val = params[i].split("=");
    if (val[0] == paramName) {
      return unescape(val[1]);
    }
  }
  return null;
}
$(document).ready(function() {
   opcion = getParameter("o");
   
    if(opcion=="5"){
      $('#btn-modificar-red').hide();
      $('#btn-borrar-red').hide();
      $('#btn-agregar-datos').hide();
    }else{
      $('#btn-iniciar-red').hide();
      $('#btn-examinar-redes').hide();
    }
});


  
  $('#select-grupos').change(function(){
  
  modificando_red = 0;
    var parameters={};
    var grupo = $('#select-grupos').val();
    parameters.grupo = grupo;
    if(opcion=="5"){
      parameters.formato = "matriz";
    }else{
      parameters.formato = "lista";
      
    }
    parameters.opcion = opcion;
    $.post('buscaNosLista', parameters, function(data){
      $('#no-lista').html(data);
    
    }, 'text');
});

 $('#no-lista').change(function(){
  if(modificando_red == 0){
    var parameters={};

    var grupo = $('#select-grupos').val();
    var no_lista = $('#no-lista').val();
  
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    parameters.modo="lista";
   if(opcion=="6"){
   
   
    $.post('buscaRedesSociales', parameters, function(data){
     $('#lista-redes').html(data);
    
  }, 'text');
 }else{
   $.post('actualizaNosLista', parameters, function(data){
      $('#lista-redes').html(data);
    
    }, 'text');
   
 }
  }
});
 
 $('#btn-modificar-red').click(function(){
    var id_red = $('.radio_red:checked').val();
    if(id_red == undefined){
     alert("Seleccione una red para modificar");
      
    }else{
    
    modificando_red = 1;
    opcion="5";
    invierteBotones(opcion);
    var id_red = $('.radio_red:checked').val();
    id_red_global = id_red;
    var parameters = {};
    parameters.id_red = id_red;
    parameters.modo="combo";
    $.post('buscaElementosRed', parameters, function(data){
        var parameters={};
    var grupo = $('#select-grupos').val();
    parameters.grupo = grupo;
    parameters.formato = "matriz";
    $.post('actualizaNosLista', parameters, function(data){
      $('#lista-alumnos').html(data);
    
    }, 'text');
        alert("Cambie los elementos y presione 'Guardar'");

     
        var red = data.split("-");
        var elementos = red[0].split(",");


        var red_social = $('.check-red-social');
        var tiene_datos;
        var elemento;
        $.each(red_social, function(index){
          for(var i=0; i< elementos.length ; i++){
            tiene_datos = 0;
           if(elementos[i].indexOf("d") != -1){
             elemento = elementos[i].replace("d","");
               tiene_datos=1;
           }else{
             elemento = elementos[i];
             
           }

           if(index+1 == elemento){
              $(this).prop({checked:true});

              if(tiene_datos==1){
                 $(this).attr('disabled',true);
                
              }
            }
            
          }
        });
       var referido =  $('.radio-referido');

$.each(referido, function(index){

          
            if(index+1 == red[1]){
              $(this).prop({checked:true});
            }
       
        });

    
  }, 'text');
    
 }
  });
  
  $('#btn-agregar-datos').click(function(){
    var parameters={};
    parameters.url="Catalogos/Redes_sociales/agrega_datos_red.jsp";
    $.post('muestraDatosInteres', parameters, function(data){
     $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
 
     });
    
  });
  });
  
  
  $('#btn-borrar-red').click(function(){
    
    var id_redes_check = $('.check_red:checked').val();
    if(id_redes_check==undefined){
      alert("Seleccione una o más redes para borrar");
      
    }else{
    var id_redes = "";
    for(var i=0 ; i<id_redes_check.length ; i++){
      id_redes += id_redes_check[i];
    }
 
    var parameters = {};
    var grupo = $('#select-grupos').val();
    var no_lista = $('#no-lista').val();
   
   
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    parameters.id_redes = id_redes;
    parameters.modo="lista";
  
    $.post('borraRedes', parameters, function(data){}, 'text');

        $.post('buscaRedesSociales', parameters, function(data){
     $('#lista-redes').html(data);
    }, 'text');
  }
  });
 
 $('#btn-iniciar-red').click(function(){
   
   var mensaje="";
   var corte = $('#select-cortes').val();
   var turno = $('#select-turno').val();
   var grupo = $('#select-grupos').val();
   var no_lista = $('#no-lista').val();
   if(corte==""){
     mensaje+="Debe seleccionar un corte\n";
   }
   if(turno==""){
     mensaje+="Debe seleccionar un turno\n";
   }
   if(grupo==""||grupo==undefined){
     mensaje+="Debe seleccionar un grupo\n";
   }
   if(no_lista==""||no_lista==undefined){
     mensaje+="Debe seleccionar un número de lista";
   }
   if(mensaje!=""){
     alert(mensaje);
   }else{
   var referido = $('.radio-referido:checked').val();
  var red_social = $('.check-red-social:checked');
  var red = '';
  for(var i=0 ; i< red_social.length ; i++ ){
    red += red_social[i].value;
    if(i+1 < red_social.length){
      red += ',';
    }
  }
  
  if(referido==undefined){
    alert('Debe seleccionar al dueño de la red');
  }else{

  var parameters={};
  parameters.id_grupo = grupo;
  parameters.no_lista_refiere = no_lista;
  parameters.no_lista_referido = referido;
  parameters.red = red;
 
if(modificando_red==0){
  $.post('iniciaRed',parameters, function(data){

    alert("Se ha agregado la red social");
 
  // var parameters={};

  //  var grupo = $('#select-grupos').val();
  //  var no_lista = $('#no-lista').val();
  
 //   parameters.no_lista_refiere = no_lista;
 //  parameters.grupo = grupo;
 //   $.post('actualizaNosLista', parameters, function(data){
 //     $('#lista-redes').html(data);
    
 //   }, 'text');
 
 
 
 $('.radio-referido').prop('checked',false);
 $('.check-red-social').prop('checked',false);
// $('.check-red-social').attr('disabled',false);
// $('.check-red-social').attr('enabled',true);
// $('.check-red-social').prop({disabled:false});
  $('.check-red-social').removeAttr('disabled');
  

 //$.each(red_social, function(index){
 //  $(this).prop({checked:false});
       
 //       });

  //var parameters={};

    //var grupo = $('#select-grupos').val();
    //var no_lista = $('#no-lista').val();
  
  //  parameters.no_lista_refiere = no_lista;
   // parameters.grupo = grupo;
 //   $.post('actualizaNosLista', parameters, function(data){
 //     $('#lista-redes').html(data);
    
 //   }, 'text');
  }, 'text');
  }else{
    parameters.id_red = id_red_global;

    $.post('modificaRedesSociales',parameters, function(data){

    alert("Se ha modificado la red social");
 
 $('.radio-referido').prop('checked',false);
 $('.check-red-social').prop('checked',false);
 modificando_red = 0;
     }, 'text');
  }
  
  }
   }
});

$('#btn-examinar-redes').click(function(){
 modificando_red = 0;
     opcion ="6";
     invierteBotones(opcion);
     

    var grupo = $('#select-grupos').val();
    var no_lista = $('#no-lista').val();
    var parameters={};
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    parameters.modo="lista";
  
   if(opcion=="6"){
   
   
    $.post('buscaRedesSociales', parameters, function(data){
      $('#lista-redes').html(data);
    
  }, 'text');
 }

});
  
  function invierteBotones(opcion){
    if(opcion=="5"){
      $('#btn-modificar-red').hide();
      $('#btn-borrar-red').hide();
      $('#btn-agregar-datos').hide();
      $('#btn-iniciar-red').show();
      $('#btn-examinar-redes').show();
    }else{
      $('#btn-iniciar-red').hide();
      $('#btn-examinar-redes').hide();
      $('#btn-modificar-red').show();
      $('#btn-borrar-red').show();
      $('#btn-agregar-datos').show();
      
    }
  }
  
  
  function actualizaRedes(){
    
    
  }
  
});