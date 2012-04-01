$(function(){
    
var id_dato;
    
  
$('#btn-agrega-dato').click(function(){
      var accion='agregar';
      var  parameters ={};
      parameters.accion=accion;
      $("#accion").val(accion);
      //traer el proximo id a agregar
        $.post('buscaDatoInteres', parameters, 
        function(data){
          //recuperar el id
          $('#clave-siguiente').text(data);
          $('#descripcion-dato-interes').val("");
          //abre el dialogo
          $('#modal-dato').dialog( "open" );  
          $('#descripcion-dato-interes').focus();
        },'text');
  });
  

$('#btn-borra-dato').click(function(){
    //recupera id de datos a borrar
    var checks = $('.check_datos:checked');
    for(var i =0 ; i<checks.length ; i++){
      var parameters={};
      parameters.id_dato = checks[i].value;
      //borra
      $.post('borraDatoInteres', parameters, 
      function(data){
        alert(data);
        if( data.indexOf("borrado") )
            $('#_principal').load('muestraDatosInteres?url=Catalogos/Datos_interes/datos_interes.jsp');
      },'text'   );
    }
  });
 
 
 $('#btn-cambia-dato').click(function(){
    var check = $('.radio_datos:checked');
    var accion = 'modificar';
    $("#accion").val(accion);
    var parameters={};
    var id_dato = check[0].value;
    parameters.id_dato = id_dato;
    parameters.accion = accion;

    $.post('buscaDatoInteres', parameters, 
        function(data){
          //recuperar la descripcion
          $('#descripcion-dato-interes').val(data);
          $('#clave-siguiente').text(id_dato);
          //abre el dialogo
          $('#modal-dato').dialog( "open" );  
          $('#descripcion-dato-interes').focus();
        },'text');
  });
  
  
  $( '#modal-dato' ).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
           Guardar:function(){
               var parameters={};
               var descripcion = $('#descripcion-dato-interes').val();
               var ruta;
               parameters.descripcion = descripcion;
               parameters.accion=$("#accion").val();;
               if(parameters.accion=='agregar'){
                 ruta = 'agregaDatoInteres';
               }
               else{
                 var check = $('.radio_datos:checked');
                 var id_dato = check[0].value;               
                 ruta='modificaDatoInteres';
                 parameters.id_dato=id_dato;
               }  
               $.post(ruta, parameters, 
                  function(data){
                    alert(data);
                    if(data.indexOf("modificado")&&data.indexOf("agregado"))
                      $('#modal-dato').dialog( "close" );   
                      $('#_principal').load('muestraDatosInteres?url=Catalogos/Datos_interes/datos_interes.jsp');
                  },'text');
           },//guardar
           Cerrar: function() {
              $('#modal-dato').dialog( "close" );
           }
        }//buttons
    });
    
});