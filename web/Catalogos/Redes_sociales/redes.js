$(function(){  
  
  
  $('.combo-turnos').change(function(){
   modificando_red = 0;
     var parameters={};
     var corte = $('.combo-cortes').val();
     var turno = $('.combo-turnos').val();
     
     parameters.corte = corte;
     parameters.turno = turno;
     
     $.post('actualizaGrupos', parameters, function(data){

        $('.combo-grupos').html(data); 
    }, 'text');
  });
  



});