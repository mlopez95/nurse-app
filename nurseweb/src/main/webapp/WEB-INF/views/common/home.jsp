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

    <c:if test="${errorMsg!=null}">
        <div class="alert alert-danger" role="alert">
                ${errorMsg}
        </div>
    </c:if>

    <div class="jumbotron">
        <h1 class="display-4">BIENVENIDO A NURSEWEB</h1>
        <p class="lead">Para continuar debe seleccionar su tipo de usuario.</p>
        <hr class="my-4">
        <button type="button" id="enfermera-button" class="btn btn-primary btn-lg btn-block">Enfermera</button>
        <button type="button" class="btn btn-secondary btn-lg btn-block" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Paciente</button>
        <div class="collapse" id="collapseExample">
            <div class="card card-body">
                <form action="${baseUrl}signos-vitales/consultar">
                    <div class="form-group">
                        <label>Favor introduzca su numero de Cedula</label>
                        <input type="text" class="form-control" name="ci" id="input-ci" placeholder="Introduza su ci" required>
                        <small id="emailHelp" class="form-text text-muted">Favor NO utilizar puntos (.) ni comas (,)</small>
                        <button type="submit" id="paciente-button" class="btn btn-success btn-lg btn-block">Ingresar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>





    <script type="application/javascript">
        $('#enfermera-button').click(function() {
            window.location='${baseUrl}pacientes';
        });

        /*$('#paciente-button').click(function() {
            var ci = $('#input-ci').val();
            if(ci==null || ci===undefined || ci.length<1){

            }

            window.location='${baseUrl}buscar';
        });*/
    </script>
</t:layout>
