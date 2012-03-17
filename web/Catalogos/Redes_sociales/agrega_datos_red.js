 $(function(){
 
 $('#select-cortes-add').change(function(){
    //alert("estoy cambiando");
     $('#select-turno-add').val('');
    $('#select-grupos-add').val('');
    $('#no-lista-add').val(''); 
  });
  
  $('#select-turno-add').change(function(){
    //alert('Cambiando turno');
     var parameters={};
     var corte = $('#select-cortes-add').val();
     var turno = $('#select-turno-add').val();
     parameters.corte = corte;
     parameters.turno = turno;
      $.post('buscaGruposPorTurno', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
      });
    }, 'text');
  });
  
$('#select-grupos-add').change(function(){
  //  alert("estoy cambiando");
    var parameters={};
     var corte = $('#select-cortes-add').val();
     var turno = $('#select-turno-add').val();
    var grupo = $('#select-grupos-add').val();
    parameters.grupo = grupo;
    $.post('buscaNosLista', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
        $('#select-grupos-add').val(grupo);
     //   alert("estoy canbiamdo " + data);
      
    });
    
  }, 'text');
});


 $('#no-lista-add').change(function(){
    //alert("estoy cambiando");
    var parameters={};
    var corte = $('#select-cortes-add').val();
    var turno = $('#select-turno-add').val();
    var grupo = $('#select-grupos-add').val();
    var no_lista = $('#no-lista-add').val();
    //alert ("el grupo " + grupo);
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    $.post('buscaRedesSociales', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
        $('#select-grupos-add').val(grupo);
        $('#no-lista-add').val(no_lista);
    });
    
  }, 'text');
});

$('#sel-red').change(function(){
    //alert("estoy cambiando");
    var parameters={};
    var corte = $('#select-cortes-add').val();
    var turno = $('#select-turno-add').val();
    var grupo = $('#select-grupos-add').val();
    var no_lista = $('#no-lista-add').val();
    var red = $('#sel-red').val();
    //alert ("el grupo " + grupo);
    //parameters.no_lista_refiere = no_lista;
    //parameters.grupo = grupo;
    parameters.id_red = red;
    $.post('buscaElementosRed', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
        $('#select-grupos-add').val(grupo);
        $('#no-lista-add').val(no_lista);
        $('#sel-red').val(red);
    });
    
  }, 'text');
});

$('#btn-actualiza-red').click(function(){
  
  
});


});