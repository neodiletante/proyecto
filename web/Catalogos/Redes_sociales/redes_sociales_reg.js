$(function(){
  $('#select-grupos').change(function(){
  
    var parameters={};
    var grupo = $('#select-grupos').val();
    parameters.grupo = grupo;
    parameters.formato = "matriz";
    $.post('actualizaNosLista', parameters, function(data){
      $('#lista-alumnos').html(data);
    
    }, 'text');
});
  $('#no-lista').change(function(){
  alert("cambiando");
    //var parameters={};
    //var grupo = $('#select-grupos').val();
    //parameters.grupo = grupo;
   // $.post('actualizaNosLista', parameters, function(data){
      //$('#no-lista').html(data);
      
  //  }, 'text');
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
  alert(red);
  var parameters={};
  parameters.id_grupo = $('#select-grupos').val();
  parameters.no_lista_refiere = $('#no-lista').val();
  parameters.no_lista_referido = referido;
  parameters.red = red;
  //alert(parameters.id_grupo + " " + parameters.no_lista_refiere + " " + parameters.no_lista_referido);

  $.post('iniciaRed',parameters, function(data){
    //alert("antes de agregar red " + data); 
     $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
    
    alert("Se ha agregado la red social"); 
     //   alert("estoy canbiamdo " + data);
      
    });
  }, 'text');
  
});

$('#btn-examinar-redes').click(function(){
  //alert("Examinado redess");
 // var parameters={};
  //parameters.url = "Catalogos/Redes_sociales/redes_sociales_mod.jsp";
  //$.post('?o=6', parameters, function(){
    // $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
 


var url = "?o=6";
 $(location).attr('href', url);


//  });
    
 // }, 'text');
  
 
  
});
/*
 $('#no-lista').change(function(){
//    alert("estoy cambiando");
    var parameters={};
    var no_lista = $('#no-lista').val();
    parameters.no_lista = no_lista;
    $.post('buscaReferidos', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
        $('#no-lista  ').val(grupo);
      
    });
    
  }, 'text');
});
  */

  
});