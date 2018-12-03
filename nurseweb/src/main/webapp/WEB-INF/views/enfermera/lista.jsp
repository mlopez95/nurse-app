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
                    <a class="navbar-brand">Pacientes</a>
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
            <th>Nombre</th>
            <th>Cedula Identidad</th>
            <th>Edad</th>
            <th>Estado</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pacienteList}" var="paciente">

            <tr id="row-${paciente.id}">
                <td>${paciente.fechaRegistro}</td>
                <td>${paciente.nombre}</td>
                <td>${paciente.ci}</td>
                <td>${paciente.edad}</td>
                <td>${paciente.estado}</td>
                <td>
                    <h3 id="edit-${paciente.id}" class="fas fa-pen-square edit-span"></h3>
                    <h3 id="details-${paciente.id}" class="fas fa-heartbeat details-span"> </h3>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button class="btn btn-primary" id="btn-agregar">Agregar</button>
    <a class="btn btn-secondary" href="${baseUrl}" id="btn-volver">Volver</a>
    <%--FIN TABLA DE DATOS--%>

    <script type="text/javascript">
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
            $('#crear-paciente').click(function() {
                var nombre = $('#cre-nombre-input').val();
                var ci = $('#cre-ci-input').val();
                var edad = $('#cre-edad-input').val();
                var estado = $('#cre-estado-select').val();

                var data = {
                    ci: ci,
                    nombre: nombre,
                    edad: edad,
                    estado: estado
                };

                ajax('${baseUrl}paciente','POST',data,null,null,doneCreate,null);
            });


            $('#guardar-cambios-btn').click(function(data) {
                var id = $('#act-id').val();

                var nombre = $('#act-nombre-input').val();
                var ci = $('#act-ci-input').val();
                var edad = $('#act-edad-input').val();
                var estado = $('#act-estado-select').val();

                var data = {
                    ci: ci,
                    nombre: nombre,
                    edad: edad,
                    estado: estado
                };

                ajax('${baseUrl}paciente/'+id,'PUT',data,null,null,doneUpdate,null);
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

        //EVENTO QUE CAPTURA EL CLICK EN EL ICONO CORAZON
        $('.details-span').click(function() {
            var idEdit = $(this).attr('id');
            var array = idEdit.split("-");
            var id = array[1];
            window.location='${baseUrl}signos-vitales/'+id;



        });

        //METHOD QUE OBTIENE LOS DATOS DE LA FILA
        var obtenerDatosFila = function (id) {
            var data = table.row($('#row-'+id));
            return data.data();
        };


        //CARGA EL MODAL DE ACTUALIZAR CON LOS DATOS ACTUALES
        var cargarModalActualizar = function (array, id) {
            $('#act-id').val(id);
            $('#act-nombre-input').val(array[1]);
            $('#act-ci-input').val(array[2]);
            $('#act-edad-input').val(array[3]);
            $('#act-estado-select').val(array[4]);
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
                                <label for="act-nombre-input" class=" form-control-label">Nombre</label>
                                <input type="text" id="act-nombre-input" name="nombre" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="act-ci-input" class=" form-control-label">Cedula Identidad</label>
                                <input type="text" id="act-ci-input" name="ci" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="act-edad-input" class=" form-control-label">Edad</label>
                                <input type="number" id="act-edad-input" name="edad" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="act-estado-select" class=" form-control-label">Estado</label>
                                <select id="act-estado-select" name="estado" class="form-control">
                                    <option value="ACTIVO">ACTIVO</option>
                                    <option value="INACTIVO">INACTIVO</option>
                                </select>
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
                <h5 class="modal-title" id="titulo-modal-crear">Agregar Paciente</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form-crear" action="crear">
                    <div class="card-body card-block">
                        <div class="form-group">
                            <div class="form-group">
                                <label for="cre-nombre-input" class=" form-control-label">Nombre</label>
                                <input type="text" id="cre-nombre-input" name="nombre" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cre-ci-input" class=" form-control-label">Cedula Identidad</label>
                                <input type="text" id="cre-ci-input" name="ci" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cre-edad-input" class=" form-control-label">Edad</label>
                                <input type="number" id="cre-edad-input" name="edad" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cre-estado-select" class=" form-control-label">Estado</label>
                                <select id="cre-estado-select" name="estado" class="form-control">
                                    <option value="ACTIVO">ACTIVO</option>
                                    <option value="INACTIVO">INACTIVO</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" id="crear-paciente" class="btn btn-primary">Guardar cambios</button>
                        <input type="hidden" name="id" id="cre-id" style="display: none"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--FIN MODAL DE EDITAR--%>