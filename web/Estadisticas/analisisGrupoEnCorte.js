/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){

function cargaComboCortes(){/*Obtiene la lista de cortes*/
    var parameters={};
    parameters.opcion=1;
    $.post('analizarGrupoEnCorteBean', parameters, function(data){
        try{
            $('#corte').html(data);
            cargaGrupos();
        }catch(ex){}   
    });
}

function cargaGrupos(){
    var parameters={};
    parameters.corte=$('#corte').val();
    parameters.turno=$('#turno').val();
    parameters.opcion=2;
    $.post('analizarGrupoEnCorteBean', parameters, function(data){
        try{
            $('#id_grupo').html(data);
        }catch(ex){}
    });
}
$('#turno').change(function(){
/*cargar lista de grupos*/
    cargaGrupos();
});


$('#id_grupo').change(/*trae el analisis del grupo*/
  function(){
    var parameters={};
    parameters.opcion=3;
    parameters.id_grupo=$('#id_grupo').val();
    $.post('analizarGrupoEnCorteBean', parameters, function(data){
        try{
            $('#tabla-redes-reales').html(data);
        }catch(ex){}
    });
  }
);

$('#analisis').click(/*trae el analisis del grupo*/
  function(){
    var parameters={};
    parameters.id_grupo=$('#id_grupo').val();
    parameters.opcion=3;
    $.post('analizarGrupoEnCorteBean', parameters, function(data){
        try{
            $('#tabla-redes-reales').html(data);
        }catch(ex){}
    });
  }
);

$('#imprimir').click(
    function (){
        var parameters ={};
        parameters.opcion=5;
        parameters.corte=$('#corte').val();
        $('#espera').addClass('imgAnalizando');
        $('#espera').html('Analizando datos...');
        $.post('analizarGrupoEnCorteBean',parameters, function(data){
           parameters.opcion=4;
           $('#espera').html('Generando reporte...');
           $("#espera").addClass('imgGenerando');
           $.post('analizarGrupoEnCorteBean', parameters, function(data){
               $('#capaArchivo').html(data);
           })
        })
    }
);

cargaComboCortes();
});