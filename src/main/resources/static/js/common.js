
$.fn.serializeObject = function() {
    var result = {}
    var extend = function(i, element) {
        var node = result[element.name]
        if ("undefined" !== typeof node && node !== null) {
            if ($.isArray(node)) {
                node.push(element.value)
            } else {
                result[element.name] = [node, element.value]
            }
        } else {
            result[element.name] = element.value
        }
    }

    $.each(this.serializeArray(), extend)
    return result
}

Number.prototype.format = function(){
    if(this==0) return 0;

    var reg = /(^[+-]?\d+)(\d{3})/;
    var n = (this + '');

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');

    return n;
};

String.prototype.numberFormat = function(){
    var num = parseFloat(this);
    if( isNaN(num) ) return "0";

    return num.format();
};

String.prototype.makeShort = function (maxLength) {
    maxLength = typeof maxLength !== 'undefined' ? maxLength : 15;
    if (this.length <= maxLength) {
        return this;
    }

    return this.substring(0, maxLength) + "...";
}

const _request = function (url, method, data, successCallback, failureCallback) {
    const defaultFailureCallback = function (xhr, status, errorThrown) {
        if (status === "canceled")
            return;

        alert("처리 중 오류가 발생하였습니다.")
        console.warn(xhr)
        console.warn(status)
        console.warn(errorThrown)
    };

    failureCallback = typeof failureCallback !== "undefined" ? failureCallback : defaultFailureCallback;

    $.ajax({
        url: url,
        data: data,
        type: method,
        contentType: "application/json",
        dataType: "json"
    })
    .done(successCallback)
    .fail(failureCallback)
    .always(function () {
        // setTimeout(function () { isAjaxing = false; }, 1000);
    });
}

const getRequest = function (url, successCallback, failureCallback) {
    return _request(url, "GET", null, successCallback, failureCallback);
}

const postRequest = function (url, data, successCallback, failureCallback) {
    return _request(url, "POST", data, successCallback, failureCallback);
}
