 $(function(){
 
 $('#select-cortes-add').change(function(){

     $('#select-turno-add').val('');
    $('#select-grupos-add').val('');
    $('#no-lista-add').val(''); 
  });

$('#select-grupos-add').change(function(){

    var parameters={};
    var grupo = $('#select-grupos-add').val();
    parameters.grupo = grupo;
    $.post('buscaNosLista', parameters, function(data){
      $('#no-lista-add').html(data);
    
    }, 'text');
});


 $('#no-lista-add').change(function(){
    //alert("estoy cambiando");
    var parameters={};
    var grupo = $('#select-grupos-add').val();
    var no_lista = $('#no-lista-add').val();
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    parameters.modo = "combo";
    $.post('buscaRedesSociales', parameters, function(data){
      $('#sel-red').html(data);
    }, 'text');
  });

$('#sel-red').change(function(){
    //alert("estoy cambiando");
    var parameters={};
    var red = $('#sel-red').val();
    parameters.id_red = red;
    parameters.modo="combo";
    $.post('buscaElementosRed', parameters, function(data){
      $('#sel-nos-lista').html(data);
    }, 'text');
    actualizaTabla();
    
});

$('#btn-agrega-dato-red').click(function(){
  var mensaje="Debe seleccionar el(los) siguiente(s) elemento(s):\n";
   var corte = $('#select-cortes-add').val();
   var turno = $('#select-turno-add').val();
   var grupo = $('#select-grupos-add').val();
   var no_lista = $('#no-lista-add').val();
   var red = $('#sel-red').val();
   var dato_interes = $('#select-datos-interes').val();
   var elemento = $('#sel-nos-lista').val();

   if(corte==""){
     mensaje+="Corte\n";
   }
   if(turno==""){
     mensaje+="Turno\n";
   }
   if(grupo==""||grupo==undefined){
     mensaje+="Grupo\n";
   }
   if(no_lista==""||no_lista==undefined){
     mensaje+="Alumno que refiere\n";
   }
   if(red==""||red==undefined){
     mensaje+="Red\n";
   }
   if(elemento==""||elemento==undefined){
     mensaje+="Elemento de la red para asignar el dato\n";
   }
   if(dato_interes==""||dato_interes==undefined){
     mensaje+="Dato de interés\n";
   }
   
   if(mensaje!="Debe seleccionar el(los) siguiente(s) elemento(s):\n"){
     alert(mensaje);
   }else{
  
  parameters={};
  parameters.id_red = red;
  parameters.no_lista = elemento;
  parameters.id_dato = dato_interes;
  $.post('agregaDatoRed', parameters, function(data){
    actualizaTabla();
  }, 'text');
  
   }
});


$('#btn-borra-datos').click(function(){
  var red = $('#sel-red').val();
  var relacion_datos = $('.check_datos:checked');
  var id_relaciones = "";
  for(var i=0 ; i< relacion_datos.length ; i++){
    id_relaciones += relacion_datos[i].value;
    if(i < relacion_datos.length-1){
      id_relaciones += ",";
    }
  }
  alert(id_relaciones);
  var parameters={};
  parameters.relaciones = id_relaciones;
  parameters.id_red = red;
  $.post('borraRegistrosDatos', parameters, function(data){
    actualizaTabla();
  }, 'text');
  
});
  
  function actualizaTabla(){
    var id_red = $('#sel-red').val();
    var parameters = {};
    parameters.id_red = id_red 
    $.post('actualizaTablaDatos', parameters, function(data){
      $('#tabla-datos').html(data);
    }, 'text');
  }


});