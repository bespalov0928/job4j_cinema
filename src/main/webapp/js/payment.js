//var path = window.location.pathname.replace("pages/payment.html", "");

function getNumberPlace() {

    var url = window.location.href;
    var paramId = url.split('?')[1].split('=')[1];
    var row = paramId.split('/')[0];
    var col = paramId.split('/')[1];
    console.log('row:' + row);
    console.log('col:' + col);
    var heder = document.getElementById('heder');
    heder.innerHTML = 'Вы выбрали ряд ' + row + ' место ' + col + ', Сумма : 600 рублей.';
    var rowHeder = document.getElementById('rowHeder');
    rowHeder.setAttribute('value', row);

    var colHeder = document.getElementById('colHeder');
    colHeder.setAttribute('value', col);
}

function pay() {

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
    }).done(function (data) {
        console.log("data");
        console.log(data);
        alert("data");
        window.location.href = 'http://localhost:8080/cinema';
    }).fail(function (data, status, error) {
        console.log("fail");
        console.log(data);
        console.log(data.status );

        console.log(status);
        alert("fail, status: "+status);

        if (data.status == 401){
            alert("401");
            window.location.href = window.location.href+'error.html';
            //window.location.href = 'http://localhost:8080/cinema/error.html';
        }
    });

    alert("end");
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
