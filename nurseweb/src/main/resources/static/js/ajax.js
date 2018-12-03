var ajax = function ajax(url,method, params, headers, beforeSend, done, always, responseType){

    var data = {
        type: method,
        contentType: "application/json;charset=UTF-8",
        url: url,
        data: JSON.stringify(params),
        headers:headers
    };


    if(!(beforeSend===undefined || beforeSend===null)){
        data.beforeSend=beforeSend;
    }

    $.ajax(data)
        .done(done)
        .fail(errorFun)
        .always(always);
};


var errorFun = function(data) {
    var json = data.responseJSON;

    if(json.hasError){
        var errorBody = JSON.parse(json.errorBody);

        if(errorBody.useApiMessage)
                alertify.error(errorBody.message);
        else
            alertify.error("Ocurrio un error inesperado.");
    }else{

    }

};

var mostrarModal = function (id) {
    $('#'+id).modal('show');
};

var ocultarModal = function (id) {
    $('#'+id).modal('hide');
};
