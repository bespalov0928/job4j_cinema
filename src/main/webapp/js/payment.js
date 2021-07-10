//var path = window.location.pathname.replace("pages/payment.html", "");

function getNumberPlace() {
    var url = window.location.href;
    var paramId = url.split('?')[1].split('=')[1];
    var row = paramId.split('/')[0];
    var col = paramId.split('/')[1];
    // console.log(paramId);
    console.log('row:' + row);
    console.log('col:' + col);
    var heder = document.getElementById('heder');
    heder.innerHTML = 'Вы выбрали ряд ' + row + ' место ' + col + ', Сумма : 500 рублей.';
    var rowHeder = document.getElementById('rowHeder');
    rowHeder.setAttribute('value', row);

    var colHeder = document.getElementById('colHeder');
    colHeder.setAttribute('value', col);
}

function pay() {
    var url = window.location.href;
    var paramId = url.split('?')[1].split('=')[1];
    // var row = paramId.split('/')[0];
    // var col = paramId.split('/')[1];
    var place = url.split('?')[2].split('=')[1];
    // console.log(document.getElementById('username').value);
    console.log($('#rowHeder').val());
    alert('pay');
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/cinema/payment',
        data: JSON.stringify({
            row: 'row=' + $('#rowHeder').val(),
            col: 'col=' + $('#colHeder').val(),
            username: 'username=' + $('#username').val(),
            phone: 'phone=' + $('#phone').val(),
            email: 'email=' + $('#email').val(),
            place: 'place=' + place
        }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log("data");
            alert("data");
            window.location.href = 'http://localhost:8080/cinema';
        },
        error: function (data, status, error) {
            console.log("data");
            alert("err");
            window.location.href = 'http://localhost:8080/cinema';
        },
        fail: function (jqXHR, status, errorThrown) {
            console.log("data");
            alert("err");
            window.location.href = 'http://localhost:8080/cinema';
        }    });
}

function validate() {
    valid = true;
    if (document.contact_form.username.value == "") {
        alert("Заполниет поле ФИО");
        valid = false;
    }
    if (document.contact_form.username.value == "") {
        alert("Заполниет поле телефон");
        valid = false;
    }
    if (document.contact_form.username.value == "") {
        alert("Заполниет поле Email");
        valid = false;
    }
    return valid;
}
