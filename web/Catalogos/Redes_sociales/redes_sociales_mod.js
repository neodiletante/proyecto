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
    //alert ("el grupo " + grupo);
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    $.post('buscaRedesSociales', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
        $('#select-cortes-mod').val(corte);
        $('#select-turno-mod').val(turno);
        $('#select-grupos-mod').val(grupo);
        $('#no-lista-mod').val(no_lista);
    });
    
  }, 'text');
});
  
  $('#btn-modificar-red').click(function(){
    //alert("eo");
    var id_red = $('.radio_red:checked').val();
    //alert(id_red);
    var parameters = {};
    parameters.id_red = id_red;
    //alert(id_red);
    $.post('buscaElementosRed', parameters, function(data){
      //alert(data);
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_reg.jsp',data,function(){
        alert(data);
        var elementos = data.split(",");
        var red_social = $('.check-red-social');
        for(var i=0 ; i< elementos.length ; i++){
          alert("ckecked " + red_social[i]);
          red_social[i].attr('checked', true);
          
          
        }
        //var json = $.getJSON(data);
        //$.each(json, function(k,v){console.log(k+" -> " +v); });
        //$('#select-cortes-mod').val(corte);
        //$('#select-turno-mod').val(turno);
        //$('#select-grupos-mod').val(grupo);
        //$('#no-lista-mod').val(no_lista);
    });
    
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
    //alert("eo");
    var id_redes_check = $('.check_red:checked').val();
    var id_redes = "";
    for(var i=0 ; i<id_redes_check.length ; i++){
      id_redes += id_redes_check[i];
    }
    alert(id_redes);
    var parameters = {};
    parameters.id_redes = id_redes;
    //alert(id_red);
    $.post('borraRedes', parameters, function(data){}, 'text');
      //alert(data);
     // $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
        //alert(data);
        
   // });
    buscaRedesSociales();
  //}, 'text');
    
    
  });
  
});

function buscaRedesSociales(){
    var parameters={};
    var corte = $('#select-cortes-mod').val();
    var turno = $('#select-turno-mod').val();
    var grupo = $('#select-grupos-mod').val();
    var no_lista = $('#no-lista-mod').val();
    //alert ("el grupo " + grupo);
    parameters.no_lista_refiere = no_lista;
    parameters.grupo = grupo;
    $.post('buscaRedesSociales', parameters, function(data){
      $('#_principal').load('Catalogos/Redes_sociales/redes_sociales_mod.jsp',data,function(){
        $('#select-cortes-mod').val(corte);
        $('#select-turno-mod').val(turno);
        $('#select-grupos-mod').val(grupo);
        $('#no-lista-mod').val(no_lista);
    });
    
  }, 'text');
  
}