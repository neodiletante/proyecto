$(function(){
  
  $('#select-cortes').change(function(){
    //alert("estoy cambiando");
      $('#select-turno').val('');
      $('#select-grupos').val('');
      $('#no-lista').val('');    
  });
  
  $('#select-turno').change(function(){
    //alert('Cambiando turno');
     var parameters={};
     var corte = $('#select-cortes').val();
     var turno = $('#select-turno').val();
     parameters.corte = corte;
     parameters.turno = turno;
      $.post('buscaGruposPorTurno', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
        $('#select-cortes').val(corte);
        $('#select-turno').val(turno);
      });
    }, 'text');
  });
  
  $('#select-grupos').change(function(){
  //  alert("estoy cambiando");
    var parameters={};
     var corte = $('#select-cortes').val();
     var turno = $('#select-turno').val();
    var grupo = $('#select-grupos').val();
    parameters.grupo = grupo;
    $.post('buscaNosLista', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
        $('#select-cortes').val(corte);
        $('#select-turno').val(turno);
        $('#select-grupos').val(grupo);
     //   alert("estoy canbiamdo " + data);
      
    });
    
  }, 'text');
});
$('#btn-iniciar-red').click(function(){
  var referido = $('.radio-referido:checked').val();
  var red_social = $('.check-red-social:checked');
  var json_red = '{"red":[';
  for(var i=0 ; i< red_social.length ; i++ ){
    json_red += '{"elemento": "';
    //alert(red_social[i].value);
    json_red += red_social[i].value;
    json_red += '"}';
    if(i+1 < red_social.length){
      json_red += ',';
    }else{
      json_red += ']}'
    }
  }
  alert(json_red);
  var parameters={};
  parameters.id_grupo = $('#select-grupos').val();
  parameters.no_lista_refiere = $('#no-lista').val();
  parameters.no_lista_referido = referido;
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
  var parameters={};
  parameters.url = "Catalogos/Redes_sociales/redes_sociales_mod.jsp";
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