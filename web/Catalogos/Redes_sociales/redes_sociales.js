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
      //$('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
       // $('#select-cortes').val(corte);
       // $('#select-turno').val(turno);
        $('#select-grupos').html(data); 
     // });
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
   // var corte = $('#select-cortes-mod').val();
   // var turno = $('#select-turno-mod').val();
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
      
 //     $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
        var parameters={};
    var grupo = $('#select-grupos').val();
    parameters.grupo = grupo;
    parameters.formato = "matriz";
    $.post('actualizaNosLista', parameters, function(data){
      $('#lista-alumnos').html(data);
    
    }, 'text');
        alert(data);
        var elementos = data.split(",");
        var red_social = $('.check-red-social');
        for(var i=0 ; i< elementos.length ; i++){
          alert("ckecked " + elementos[i]);
          red_social[elementos[i]].prop('checked', true);
          
          
        }
        //var json = $.getJSON(data);
        //$.each(json, function(k,v){console.log(k+" -> " +v); });
        //$('#select-cortes-mod').val(corte);
        //$('#select-turno-mod').val(turno);
        //$('#select-grupos-mod').val(grupo);
        //$('#no-lista-mod').val(no_lista);
  //  });
    
  }, 'text');
    
    
  });
  
  $('#btn-agregar-datos').click(function(){
    var parameters={};
    parameters.url="Catalogos/Redes_sociales/agrega_datos_red.jsp";
    $.post('muestraDatosInteres', parameters, function(data){
     $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
    //  var url = "?o=12";
 //$(location).attr('href', url);  
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
   // });
 //   buscaRedesSociales();
  //}, 'text');
    
    
  });
 
 $('#btn-iniciar-red').click(function(){

   var referido = $('.radio-referido:checked').val();
  var red_social = $('.check-red-social:checked');
  var red = '';
  for(var i=0 ; i< red_social.length ; i++ ){
    red += red_social[i].value;
    if(i+1 < red_social.length){
      red += ',';
    }
  }
 
  var parameters={};
  parameters.id_grupo = $('#select-grupos').val();
  parameters.no_lista_refiere = $('#no-lista').val();
  parameters.no_lista_referido = referido;
  parameters.red = red;
 

  $.post('iniciaRed',parameters, function(data){
  
   //  $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
    
    alert("Se ha agregado la red social"); 
     //   alert("estoy canbiamdo " + data);
      
 //   });
  }, 'text');
  
});

$('#btn-examinar-redes').click(function(){
 
 // var parameters={};
  //parameters.url = "Catalogos/Redes_sociales/redes_sociales_mod.jsp";
  //$.post('?o=6', parameters, function(){
    // $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
     opcion =6;
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
    var parameters;
 var grupo = $('#select-grupos').val();
    var no_lista = $('#no-lista').val();
   
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
   if(opcion=="6"){
   
   
    $.post('buscaRedesSociales', parameters, function(data){
      $('#lista-redes').html(data);
    
  }, 'text');
 }

//var url = "?o=6";
// $(location).attr('href', url);


//  });
    
 // }, 'text');
  
 
  
});
 
 
});