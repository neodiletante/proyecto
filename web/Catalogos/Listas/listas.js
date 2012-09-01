/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 $(function(){
     var availableTags=[];

    /** cambia el color del elemento color */
    function cambiaColor(color){
       var colorSel= $("#sel-color").html();
       colorSel.style="color="+color;
    }
    
    $("#grupo").change(function(){
      var parameters={};
      parameters.opcion="getLista";
      parameters.id_grupo=$("#grupo").val(); 
      refrescarLista();
      refrescarAlumnos();
      refrescarListaGrupo(parameters);
    });

    /** refresca la tabla que te muestra los elementos en listados en el grupo */
    function refrescarLista(){
      var parameters={};
      parameters.opcion="getLista";
      parameters.id_grupo=$("#grupo").val();
      $.post("listasBean", parameters, function(data){
        $("table#lista").html(data);
      });
    }
    
    /**  refresca la lista de grupos para elegir */
    function refrescarGrupo(parameters){
        $.post("listasBean", parameters, function(data){
            $("#grupo").html(data);
            refrescarAlumnos();
            refrescarListaGrupo(parameters);
            refrescarLista();
            autoCompletarAlumnos();
        });
    }
    
    $("#sel-color").change(function(){
        cambiaColor($("#sel-color").val())}
    );
        
    $("#corte").change( //carga los grupos
      function(){
         var parameters={};
         parameters.corte=$("#corte").val();
         parameters.turno=$("#turno").val();
         parameters.opcion="getGrupos";
         //refresca los grupos
        refrescarGrupo(parameters);
      }
    );   
        
     $("#turno").change( //carga los grupos
      function(){
         var parameters={};
         parameters.corte=$("#corte").val();
         parameters.turno=$("#turno").val();
         parameters.opcion="getGrupos";
         //refresca los grupos
         refrescarGrupo(parameters);
      }
     );   
        
    $("#seleccionar").click(//establecer el valor del expediente
      function(){
          var opciones=$("#alumno option");
          if( $("#buscar").length>0 )
           var valor=false;
           for (i=0; i<opciones.length; i++){
              if(opciones[i].text==$("#buscar").val()){
                  opciones[i].selected=true;
                  valor=true;
                  break;
              }    
          }
          if(!valor)
            alert("El valor no coincide con un alumno");
      }
    );   
        
     $("#agregar").click(
       function(){
          var parameters={};
          parameters.accion="agregar";
          parameters.id_grupo=$("#grupo").val();
          parameters.no_expediente=$("#alumno").val();
          parameters.no_lista=$("#no-lista").val();
          parameters.nombre=" ";
          parameters.color=$("#sel-color").val();
          parameters.grupo_est=$("#grupo_estadistico").val();
          var continuar=true;
          if( parameters.color.length>0 && parameters.id_grupo!=null && parameters.no_expediente!=null ){
              if( parameters.color=="FFFFFF" )
                  continuar=confirm("El color actual es blanco. ¿Desea continuar?");
              parameters.color="#"+parameters.color;
              if( continuar )
                  $.post("listasBean", parameters, function(data){
                      refrescarLista();
                      refrescarListaGrupo(parameters);
                      autoCompletarAlumnos();
                      refrescarAlumnos();
                  });
          }
          else{
            alert("No hay suficientes datos para agregar a la lista");
          }
       }
     );
   
   /** resfresca la lista de numeros para elegir del grupo  */
   function refrescarListaGrupo(parameters){
       parameters.opcion='getListaGrupos';
       parameters.id_grupo=$('#grupo').val();
       $.post('listasBean', parameters, function(data){
           $('#no-lista').html(data);
       });
       autoCompletarAlumnos();       
   }
   
   function refrescarAlumnos (){
         var parameters={};
         parameters.corte=$("#corte").val();
         parameters.turno=$("#turno").val();
         parameters.opcion="getGrupos";
         //refresca los alumnos
         parameters.opcion="getAlumnos";
         $.post("listasBean", parameters, function(data){
             $("#alumno").html(data);
             autoCompletarAlumnos();
         });
   }
   
      //refrescar alumnos el setea el autocompletar    
   function autoCompletarAlumnos(){
     var opciones=$("#alumno option");
     availableTags = new Array(opciones.length);
     for (i=0; i<opciones.length; i++){
        availableTags[i]=opciones[i].text;
     }
     $( "#buscar" ).autocomplete({
       source: availableTags
     });
   }
   
   $("#btn-borrar").live('click',
      function(){
         var parameters={};
         var checados=$("input[type=checkbox]:checked");
         parameters.lstborra=[];
         for(i=0; i<checados.length; i++){
                parameters.lstborra.push(checados[i].value);
         }
         parameters.corte=$("#corte").val();
         parameters.turno=$("#turno").val();
         parameters.id_grupo=$("#grupo").val();
         parameters.accion="borrar";
         $.post('listasBean', parameters, function(data){
             alert(data);
             refrescarLista();
             refrescarListaGrupo(parameters);
             refrescarAlumnos();
         });
     }
   );

   $("#cambia").dialog({
       title:'Cambio de alumno en la lista',
       autoOpen: false,
       height: 280,
       width: 400,
       modal: false,
       buttons: {
           Guardar:function(){
               //traer los valores
               var parameters={};
               parameters.no_lista= $("#ch_numero").text();
               parameters.no_expediente=$("#ch_alumno").val();
               parameters.accion='modificar';
               parameters.color='#'+$("#mod-color").val();
               parameters.id_grupo=$("#grupo").val();
               parameters.grupo_est=$("#id-gpo-es").val();
               $.post("listasBean", parameters, function(data){
                 alert(data);
                 refrescarAlumnos();
                 autoCompletarAlumnos();
                 refrescarLista();
                 refrescarListaGrupo(parameters);
                 $("#cambia").dialog( "close" );
              });
           },
           Cerrar: function() {
              $("#cambia").dialog( "close" );
           }
       }
   });

   $("#btn-cambiar").live('click',function(){
        $("#cambia").dialog( "open" );
        $("#cambia").html().style="z-index:800";
        //Da valor al numero
        var valor=$("input[type=radio]:checked");
        for(i=0; i<valor.length; valor++){
          if(valor[i].checked){
            break;
          }  
        }
        $("#ch_numero").text(valor[i].value);
        //Asigna los alumnos disponibles
        $("#ch_alumno").html($("#alumno").html());
   });
   
   $("#sel-color").ColorPicker({
	onSubmit: function(hsb, hex, rgb, el) {
		$(el).val(hex);
		$(el).ColorPickerHide();
	},
	onBeforeShow: function () {
		$(this).ColorPickerSetColor(this.value);
	}
    })
    
    $("#mod-color").ColorPicker({
	onSubmit: function(hsb, hex, rgb, el) {
		$(el).val(hex);
		$(el).ColorPickerHide();
	},
	onBeforeShow: function () {
		$(this).ColorPickerSetColor(this.value);
	}
    })
    
    .bind("keyup", function(){
	$(this).ColorPickerSetColor(this.value);
    });  
    
});