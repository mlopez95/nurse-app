<%--
  Created by IntelliJ IDEA.
  User: luis
  Date: 07/10/18
  Time: 03:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<t:layout>


    <div class="row">
        <div class="col-md-12">
            <div class="overview-wrap">
                <nav class="navbar navbar-light bg-light">
                    <a class="navbar-brand">Signos Vitales</a>
                </nav>

            </div>
        </div>
    </div>
    <hr>
    <%--TABLA DE DATOS--%>
    <table id="table_id" class="display" width="100%">
        <thead>
        <tr>
            <th>Fecha Registro</th>
            <th>Sistolica</th>
            <th>Diastolica</th>
            <th>Frecuencia Cardiaca</th>
            <th>Estado Presion Art.</th>
            <th>Estado Frec. Cardiaca</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${signosList}" var="signo">

            <tr id="row-${signo.id}">
                <td>${signo.fechaRegistro}</td>
                <td>${signo.sistolica}</td>
                <td>${signo.diastolica}</td>
                <td>${signo.frecuenciaCardiaca}</td>
                <td>${signo.estadoPresionArterial}</td>
                <td>${signo.estadoFrecuenciaCardiaca}</td>
                <td>
                    <h3 id="edit-${signo.id}" class="fas fa-pen-square edit-span"></h3>
                    <h3 id="delete-${signo.id}" class="fas fa-times-circle delete-span"> </h3>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%--FIN TABLA DE DATOS--%>
    <button class="btn btn-primary" id="btn-agregar">Agregar</button>
    <button class="btn btn-secondary" onclick="window.history.back();">Volver</button>

    <script type="text/javascript">
        var idPaciente = '${idPaciente}';
        var table;
        $(document).ready( function () {

            //INICIALIZAMOS EL DATATABLE
            table = $('#table_id').DataTable({
                info: false,
                "language": {
                    "search": "Buscar",
                    "emptyTable": "No se encontraron resultados",
                    "zeroRecords": "No se encontraron resultados",
                    "lengthMenu": "Mostrando _MENU_ resultados",
                    "paginate":{
                        "next": "Siguiente",
                        "previous": "Anterior"
                    }

                }
            });


            //EVENTO QUE CAPTURA EL CLICK EN EL BOTON GUARDAR
            $('#btn-agregar').click(function() {
                mostrarModal("modal-crear");

            });


            //EVENTO QUE CAPTURA EL CLICK EN EL BOTON GUARDAR DEL MODAL CREAR
            $('#crear-signovital').click(function() {
                var sistolica = $('#cre-sistolica-input').val();
                var diastolica = $('#cre-diastolica-input').val();
                var frecuenciaCardiaca = $('#cre-cardiaca-input').val();

                var data = {
                    sistolica: sistolica,
                    diastolica: diastolica,
                    frecuenciaCardiaca: frecuenciaCardiaca
                };

                ajax('${baseUrl}signos-vitales/'+idPaciente+'/paciente','POST',data,null,null,doneCreate,null);
            });


            $('#guardar-cambios-btn').click(function() {
                var id = $('#act-id').val();

                var sistolica = $('#act-sistolica-input').val();
                var diastolica = $('#act-diastolica-input').val();
                var frecuenciaCardiaca = $('#act-cardiaca-input').val();

                var data = {
                    sistolica: sistolica,
                    diastolica: diastolica,
                    frecuenciaCardiaca: frecuenciaCardiaca
                };

                ajax('${baseUrl}signos-vitales/'+id,'PUT',data,null,null,doneUpdate,null);
            });

            $('#eliminar-btn').click(function() {
                var id = $('#delete-id').val();

                ajax('${baseUrl}signos-vitales/'+id,'DELETE',null,null,null,doneDelete,null);
            });

        });//------ FIN DEL DOCUMENT READY




        //EVENTO QUE CAPTURA EL CLICK EN EL ICONO EDITAR
        $('.edit-span').click(function() {
            var idEdit = $(this).attr('id');
            var array = idEdit.split("-");
            var id = array[1];
            var data = obtenerDatosFila(id);
            cargarModalActualizar(data, id);
        });


        //EVENTO QUE CAPTURA EL CLICK EN EL ICONO ELIMINAR
        $('.delete-span').click(function() {
            var idEdit = $(this).attr('id');
            var array = idEdit.split("-");
            var id = array[1];
            $('#delete-id').val(id);
            mostrarModal('modal-eliminar');
        });

        //METHOD QUE OBTIENE LOS DATOS DE LA FILA
        var obtenerDatosFila = function (id) {
            var data = table.row($('#row-'+id));
            return data.data();
        };


        //CARGA EL MODAL DE ACTUALIZAR CON LOS DATOS ACTUALES
        var cargarModalActualizar = function (array, id) {
            $('#act-id').val(id);
            $('#act-sistolica-input').val(array[1]);
            $('#act-diastolica-input').val(array[2]);
            $('#act-cardiaca-input').val(array[3]);
            mostrarModal("modal-actualizar");
        };

        //METHODO PARA LIMPIAR LOS CAMPOS LUEGO DE INSERTAR/ACTUALIZAR
        var limpiarCampos = function (type) {
            $('#'+type+'-nombre-input').val();
            $('#'+type+'-ci-input').val();
            $('#'+type+'-edad-input').val();
            $('#'+type+'-estado-select').val('ACTIVO');
        };

        //FUNCION QUE SE EJECUTA SI LA PETICION FUE CORRECTA
        var doneCreate = function(data){
            ocultarModal("modal-crear");
            location.reload();
        };

        //FUNCION QUE SE EJECUTA SI LA PETICION FUE CORRECTA
        var doneUpdate = function(data){
            ocultarModal("modal-actualizar");
            location.reload();
        };

        //FUNCION QUE SE EJECUTA SI ELIMINAR FUE CORRECTA
        var doneDelete = function(data){
            ocultarModal("modal-eliminar");
            location.reload();
        };

        /*var addRow = function(data){
            var icon = '<span id="edit-'+data.id+'" class="fas fa-pen-square edit-span"></span>' +
                '<span style="margin-left: 10px" id="details-'+data.id+'" class="fas fa-heartbeat details-span"> </span>';

            var row = table
                .row.add(
                    [
                        data.nombre,
                        data.ci,
                        data.edad,
                        data.fechaRegistro,
                        data.estado,
                        icon
                    ]);

            row.id = 'row-'+data.id;
            row.node().id='row-'+data.id;
            row.draw();
        };*/


        var update = function () {

        };



    </script>
</t:layout>
<%--MODAL DE EDITAR--%>
<div class="modal fade" id="modal-actualizar" tabindex="-1" role="dialog" aria-labelledby="modal-actualizar" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titulo-modal-actualizar">Actualizar Paciente</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form-actualizar" action="actualizar">
                    <div class="card-body card-block">
                        <div class="form-group">
                            <div class="form-group">
                                <label for="act-sistolica-input" class=" form-control-label">Presion Sistolica</label>
                                <input type="text" id="act-sistolica-input" name="sistolica" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="act-diastolica-input" class=" form-control-label">Presion Diastolica</label>
                                <input type="text" id="act-diastolica-input" name="diastolica" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="act-cardiaca-input" class=" form-control-label">Frecuencia Cardiaca</label>
                                <input type="number" id="act-cardiaca-input" name="cardiaca" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" id="guardar-cambios-btn" class="btn btn-primary">Guardar cambios</button>
                        <input type="hidden" name="id" id="act-id" style="display: none"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--FIN MODAL DE EDITAR--%>
<%--MODAL DE EDITAR--%>
<div class="modal fade" id="modal-crear" tabindex="-1" role="dialog" aria-labelledby="modal-crear" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titulo-modal-crear">Nuevo Signo Vital</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form-crear" action="crear">
                    <div class="card-body card-block">
                        <div class="form-group">
                            <div class="form-group">
                                <label for="cre-sistolica-input" class=" form-control-label">Presion Sistolica</label>
                                <input type="text" id="cre-sistolica-input" name="sistolica" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cre-diastolica-input" class=" form-control-label">Presion Diastolica</label>
                                <input type="text" id="cre-diastolica-input" name="diastolica" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cre-cardiaca-input" class=" form-control-label">Frecuencia Cardiaca</label>
                                <input type="number" id="cre-cardiaca-input" name="cardiaca" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" id="crear-signovital" class="btn btn-primary">Guardar cambios</button>
                        <input type="hidden" name="id" id="cre-id" style="display: none"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--FIN MODAL DE EDITAR--%>

<%--MODAL DE ELIMINAR--%>
<!-- Modal -->
<div class="modal fade" id="modal-eliminar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Eliminar registro</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Esta seguro que desea eliminar este registro ?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="button" id="eliminar-btn" class="btn btn-primary">Confirmar</button>
                <input type="hidden" name="id" id="delete-id" style="display: none"/>
            </div>
        </div>
    </div>
</div>
<%--FIN DEL MODAL DE ELIMINAR--%>