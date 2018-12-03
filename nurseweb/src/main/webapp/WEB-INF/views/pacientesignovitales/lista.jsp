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
    <div class="card">
        <div class="card-header">
            Paciente <b>${paciente.nombre}</b>
        </div>
        <div class="card-body">
            <b>Cedula Identidad: </b><label>${paciente.ci}</label><br>
            <b>Edad: </b><label>${paciente.edad}</label><br>
            <b>Fecha Registro: </b><label>${paciente.fechaRegistro}</label><br>
        </div>
    </div>
    <hr>

    <div class="row">
        <div class="col-md-12">
            <div class="overview-wrap">
                <nav class="navbar navbar-light bg-light">
                    <a class="navbar-brand">Signos Vitales</a>
                </nav>

            </div>
        </div>
    </div>
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
            <%--<th></th>--%>
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
                <%--<td>
                    <h3 id="edit-${signo.id}" class="fas fa-pen-square edit-span"></h3>
                    <h3 id="delete-${signo.id}" class="fas fa-times-circle delete-span"> </h3>
                </td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%--FIN TABLA DE DATOS--%>
    <button class="btn btn-primary" id="btn-ver">Ver Grafico</button>
    <a class="btn btn-secondary" href="${baseUrl}">Volver</a>


    <div id="chartContainer" style="height: 370px; width: 100%;"></div>
    <script>
        window.onload = function () {

            var signosList = '${signosList}';


        }
    </script>

    <script type="text/javascript">
        var idPaciente = '${idPaciente}';
        var table;

        var methodRenderCharts = function(data){

            var chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: "Grafico de Frecuencia Cardiaca"
                },
                axisY:{
                    includeZero: false
                },
                data: [{
                    type: "line",
                    dataPoints: data
                }]
            });
            chart.render();
        };

        var datosporlasdudas = [
            { y: 450 },
            { y: 414},
            { y: 520, indexLabel: "xzc",markerColor: "red", markerType: "triangle" },
            { y: 460 },
            { y: 450 },
            { y: 500 },
            { y: 480 },
            { y: 480 },
            { y: 410 , indexLabel: "lowest",markerColor: "DarkSlateGrey", markerType: "cross" },
            { y: 500 },
            { y: 480 },
            { y: 510 }
        ];


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
            $('#btn-ver').click(function() {
                ajax('${baseUrl}signos-vitales-grafico/'+idPaciente,'GET',null,null,null,methodRenderCharts,null,null);

            });


        });//------ FIN DEL DOCUMENT READY

    </script>
</t:layout>