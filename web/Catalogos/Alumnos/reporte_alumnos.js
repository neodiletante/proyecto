var id_grupo;
var vista = "";

$(function(){
 function muestraRedesRefiere(){
    var parameters = {};
  
  parameters.opcion = "refiere";
 var tr = $('#td_corte').parent().parent();
  if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }
  //alert(id_grupo);
  
  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  
  $.post('mostrarAlumnoEnRedes', parameters, function(data){
           //   alert(data);
              $('#tabla-alumno-en-redes').html(data);
               
 $('#btn-redes-referido').show();
 $('#btn-redes-participa').show();
 $('#btn-redes-reporta').hide();
            }, 'text');  
   
 }
 
 function muestraRedesReferido(){
    var parameters = {};
  parameters.opcion = "referido";
 var tr = $('#td_corte').parent().parent();
 if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }
  //alert(id_grupo);
  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  $.post('mostrarAlumnoEnRedes', parameters, function(data){
           //   alert(data);
              $('tbody').html(data); 
               $('#btn-redes-reporta').show();
 $('#btn-redes-referido').hide();
 $('#btn-redes-participa').show();
            }, 'text');  
   
 }

function muestraRedesParticipa(){
   var parameters = {};
  parameters.opcion = "participa";
var tr = $('#td_corte').parent().parent();
  
   if (id_grupo==undefined){
    id_grupo = id_grupo_g;
  }
  //alert(id_grupo);
  parameters.no_exp = $('td:eq(0)', tr).text();
  parameters.corte = $('td:eq(5)', tr).text(); 
  parameters.id_grupo = id_grupo;
  $.post('mostrarAlumnoEnRedes', parameters, function(data){
             // alert(data);
              $('#tabla-alumno-en-redes').html(data); 
               $('#btn-redes-reporta').show();
 $('#btn-redes-referido').show();
 $('#btn-redes-participa').hide();
            }, 'text');  
}

$('#btn-redes-reporta').click(function(){
  //alert(no_exp_g + " " + corte_g + " " + id_grupo_g);
 vista = "refiere";
 muestraRedesRefiere();

});

$('#btn-redes-referido').click(function(){
 vista = "referido";
 muestraRedesReferido();

});

$('#btn-redes-participa').click(function(){
  vista = "participa";
  muestraRedesParticipa();
 
});

$('#btn-borrar-red').click(function(){
  alert("buton");
  
  
});



$('#btn-borra-datos-redes').click(function(){
  //alert("presionando");
  var redes_borrar = $('.check_borra_red:checked');
   // var id_redes_check = $('.check_red:checked');
    if(redes_borrar==undefined){
      alert("Seleccione una o más redes para borrar");
      
    }
    else{
        var id_redes = "";
        for(var i=0 ; i<redes_borrar.length ; i++){
          id_redes += redes_borrar[i].value;
          if(i+1<redes_borrar.length){
            id_redes+=",";
          }
        }
        alert(id_redes);
        var parameters = {};
     
        parameters.id_redes = id_redes;
      
        $.post('borraRedes', parameters, function(data){
          if(vista=="refiere"){
            muestraRedesRefiere();
          }else if(vista=="referido"){
            muestraRedesReferido();
          } else {
            muestraRedesParticipa();
          } 
          
        
      }, 'text');
    }
  
});

});