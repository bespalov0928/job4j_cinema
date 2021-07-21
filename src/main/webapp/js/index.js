//setInterval(update, 60000);
function update() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cinema/update',
        dataType: 'json'
    }).done(function (data) {

        // таблица пустая
        var col = 3;
        var row = 3;
        var tbody = document.getElementById('tbody');
        for (r = 1; r <= row; r++) {
            if (document.getElementById(r + '/tr')) {
                continue;
            }

            var tr = document.createElement('tr');
            tr.setAttribute("id", r + '/tr');

            var th = document.createElement('th');
            th.innerHTML = r;
            tr.appendChild(th);

            for (c = 1; c <= col; c++) {
                var td = document.createElement('td');
                var textHtml = 'Ряд ' + r + ', Место ' + c;
                td.innerHTML = textHtml;
                td.setAttribute("id", r + '/' + c + '/td');
                tr.appendChild(td);

                var input = document.createElement('input');
                input.setAttribute("type", 'radio');
                input.setAttribute('name', "place");
                input.setAttribute('value', "11");
                //input.setAttribute('disabled', "disabled");
                input.setAttribute('id', r + '/' + c + '/input');
                td.appendChild(input);
            }
            tbody.appendChild(tr);
        }


        //таблица загрузка информации
        var tbody = document.getElementById('tbody');
        //console.log(data.length);
        for (index = 0; index < data.length; index++) {

            var td = document.getElementById(data[index].row + '/' + data[index].cell + '/td');
            var input = document.createElement('input');
            input.setAttribute("type", 'radio');
            input.setAttribute('name', "place");
            input.setAttribute('value', data[index].place);
            input.setAttribute('id', data[index].row + '/' + data[index].cell + '/input');

            if (data[index].account_id == 0) {
                td.innerHTML = 'Ряд ' + data[index].row + ', Место ' + data[index].cell + ' ';
            } else {
                td.innerHTML = 'Ряд ' + data[index].row + ', Место ' + data[index].cell + ' забронировано ';
                input.setAttribute('disabled', "disabled");
            }
            td.appendChild(input);
        }

    }).fail(function (err) {
        alert("fail");
        //alert(err);
    });
}

function forward() {
    var arrPlace = document.getElementsByName('place');
    var id = 0;
    var place = 0;
    for (index = 0; index < arrPlace.length; index++) {
        if (arrPlace[index].checked) {
            id = arrPlace[index].id;
            place = arrPlace[index].value;
        }
    }
    window.location = window.location.href+'pages/payment.html?id=' + id + '?place=' + place
    //window.location = 'http://localhost:8080/cinema/pages/payment.html?id=' + id + '?place=' + place;
}
