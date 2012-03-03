$(function(){
  
  $('#select-cortes-mod').change(function(){
    //alert("estoy cambiando");
     $('#select-turno-mod').val('');
    $('#select-grupos-mod').val('');
    $('#no-lista-mod').val(''); 
  });
  
  $('#select-turno-mod').change(function(){
    //alert('Cambiando turno');
     var parameters={};
     var corte = $('#select-cortes-mod').val();
     var turno = $('#select-turno-mod').val();
     parameters.corte = corte;
     parameters.turno = turno;
      $.post('buscaGruposPorTurno', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
        $('#select-cortes-mod').val(corte);
        $('#select-turno-mod').val(turno);
      });
    }, 'text');
  });
  
$('#select-grupos-mod').change(function(){
  //  alert("estoy cambiando");
    var parameters={};
     var corte = $('#select-cortes-mod').val();
     var turno = $('#select-turno-mod').val();
    var grupo = $('#select-grupos-mod').val();
    parameters.grupo = grupo;
    $.post('buscaNosLista', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
        $('#select-cortes-mod').val(corte);
        $('#select-turno-mod').val(turno);
        $('#select-grupos-mod').val(grupo);
     //   alert("estoy canbiamdo " + data);
      
    });
    
  }, 'text');
});


 $('#no-lista-mod').change(function(){
    //alert("estoy cambiando");
    var parameters={};
    var corte = $('#select-cortes-mod').val();
    var turno = $('#select-turno-mod').val();
    var grupo = $('#select-grupos-mod').val();
    var no_lista = $('#no-lista-mod').val();
    parameters.no_lista = no_lista;
    parameters.grupo = grupo;
    $.post('buscaRedesSociales', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
        $('#select-cortes-mod').val(corte);
        $('#select-turno-mod').val(turno);
        $('#select-grupos-mod').val(grupo);
        $('#no-lista-mod').val(no_lista);
    });
    
  }, 'text');
});
  

  
});