var opcion;

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
  
  
  $('#select-turno').change(function(){
   
     var parameters={};
     var corte = $('#select-cortes').val();
     var turno = $('#select-turno').val();
     
     parameters.corte = corte;
     parameters.turno = turno;
     
     $.post('actualizaGrupos', parameters, function(data){

        $('#select-grupos').html(data); 
    }, 'text');
  });
  
  $('#select-grupos').change(function(){
  
  
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
  
    var parameters={};

    var grupo = $('#select-grupos').val();
    var no_lista = $('#no-lista').val();
  
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
   if(opcion=="6"){
   
   
    $.post('buscaRedesSociales', parameters, function(data){
     $('#lista-redes').html(data);
    
  }, 'text');
 }else{
   $.post('actualizaNosLista', parameters, function(data){
      $('#lista-redes').html(data);
    
    }, 'text');
   
 }
});
 
 $('#btn-modificar-red').click(function(){
   
    opcion=5;
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
    var id_red = $('.radio_red:checked').val();
    
    var parameters = {};
    parameters.id_red = id_red;
    
    $.post('buscaElementosRed', parameters, function(data){
        var parameters={};
    var grupo = $('#select-grupos').val();
    parameters.grupo = grupo;
    parameters.formato = "matriz";
    $.post('actualizaNosLista', parameters, function(data){
      $('#lista-alumnos').html(data);
    
    }, 'text');
        alert(data);
        var elementos = data.split(",");
        alert(elementos[0]);
        alert(elementos[1]);
        var red_social = $('.check-red-social');
        for(var i=0 ; i< elementos.length ; i++){
          alert("ckecked " + elementos[i]);
          red_social[elementos[i]].prop('checked', true);
 
        }

    
  }, 'text');
    
    
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
  
    $.post('borraRedes', parameters, function(data){}, 'text');
   
     // $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
      
        $.post('buscaRedesSociales', parameters, function(data){
     $('#lista-redes').html(data);
    }, 'text');

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
  }

  var parameters={};
  parameters.id_grupo = grupo;
  parameters.no_lista_refiere = no_lista;
  parameters.no_lista_referido = referido;
  parameters.red = red;
 

  $.post('iniciaRed',parameters, function(data){

    alert("Se ha agregado la red social");
    $('.radio-referido').prop('checked',false);
    var checks = $('.check-red-social');
   for(var i=0 ; i< checks.length ; i++ ){
     alert(i);
     var check = checks[i];
     check.prop('checked',false);
   } 

  }, 'text');
  
   }
});

$('#btn-examinar-redes').click(function(){
 
     opcion ="6";
      
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

 var grupo = $('#select-grupos').val();
    var no_lista = $('#no-lista').val();
   var parameters={};
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
  
   if(opcion=="6"){
   
   
    $.post('buscaRedesSociales', parameters, function(data){
      $('#lista-redes').html(data);
    
  }, 'text');
 }

});
  
});