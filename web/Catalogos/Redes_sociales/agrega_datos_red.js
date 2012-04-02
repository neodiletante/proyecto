 $(function(){
 function actualizaTabla(){
   
    var corte = $('#select-cortes-add').val();
  var turno = $('#select-turno-add').val();
  var grupo = $('#select-grupos-add').val();
  var red = $('#sel-red').val();
  var no_lista = $('#no-lista-add').val();
  var dato_interes = $('#select-datos-interes').val();
  var no_lista_referido = $('#sel-nos-lista').val();
  parameters={};
  parameters.id_red = red;
  parameters.no_lista = no_lista_referido;
  parameters.id_dato = dato_interes;
  $.post('actualizaRed', parameters, function(data){
   
      $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
        $('#select-grupos-add').val(grupo);
        $('#no-lista-add').val(no_lista);
        $('#sel-red').val(red);
        $('#select-datos-interes').val(dato_interes);
        $('#sel-nos-lista').val(no_lista_referido);
    });
    
  }, 'text');
   
 }
 
 
 $('#select-cortes-add').change(function(){

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
    //   alert(data);
    
    //var redes = data.split(",");
    //for(var i = 0 ; i < 3 ; i++ ){
    //  alert(redes[i]);
    //}
   //alert(redes);
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
  var corte = $('#select-cortes-add').val();
  var turno = $('#select-turno-add').val();
  var grupo = $('#select-grupos-add').val();
  var red = $('#sel-red').val();
  var no_lista = $('#no-lista-add').val();
  var dato_interes = $('#select-datos-interes').val();
  var no_lista_referido = $('#sel-nos-lista').val();
  parameters={};
  parameters.id_red = red;
  parameters.no_lista = no_lista_referido;
  parameters.id_dato = dato_interes;
  $.post('actualizaRed', parameters, function(data){
   
      $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
        $('#select-grupos-add').val(grupo);
        $('#no-lista-add').val(no_lista);
        $('#sel-red').val(red);
        $('#select-datos-interes').val(dato_interes);
        $('#sel-nos-lista').val(no_lista_referido);
    });
    
  }, 'text');
});


$('#btn-borra-datos').click(function(){
  var corte = $('#select-cortes-add').val();
  var turno = $('#select-turno-add').val();
  var grupo = $('#select-grupos-add').val();
  var red = $('#sel-red').val();
  var no_lista = $('#no-lista-add').val();
  var dato_interes = $('#select-datos-interes').val();
  var no_lista_referido = $('#sel-nos-lista').val();
  var relacion_datos = $('.check_datos:checked').val();
  var id_relaciones = "";
  for(var i=0 ; i< relacion_datos.length ; i++){
    id_relaciones += relacion_datos[i];
    
  }
  var parameters={};
  //parameters.id_red = red;
  //parameters.no_lista = no_lista_referido;
  //parameters.id_dato = dato_interes;
  parameters.relaciones = id_relaciones;
  parameters.id_red = red;
  $.post('borraRegistrosDatos', parameters, function(data){
   
     $('#_principal').load('Catalogos/Redes_sociales/agrega_datos_red.jsp',data,function(){
        $('#select-cortes-add').val(corte);
        $('#select-turno-add').val(turno);
        $('#select-grupos-add').val(grupo);
        $('#no-lista-add').val(no_lista);
        $('#sel-red').val(red);
        $('#select-datos-interes').val(dato_interes);
        $('#sel-nos-lista').val(no_lista_referido);
    });
  }, 'text');
});

});