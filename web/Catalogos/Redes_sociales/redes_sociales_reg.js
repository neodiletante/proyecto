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
  $.post('iniciaRed',parameters, function(){
    
  }, 'text');
  
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