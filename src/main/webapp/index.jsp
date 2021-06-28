<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <%--setInterval(loadHalls, 5000);--%>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>

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
                    console.log(arrPlace[index].value);
                    console.log(arrPlace[index].id);

                    id = arrPlace[index].id;
                    place = arrPlace[index].value;
                }
            }
            window.location = 'http://localhost:8080/cinema/payment.jsp?id=' + id + '?place=' + place;
        }


        //setInterval(update, 3000);
    </script>

    <title>Бронирование билетов</title>

</head>

<body onload="update();">
<div class="container">

    <div class="row pt-3">
        <h4>
            Бронирование места на сеанс
        </h4>
        <table class="table table-bordered" id="table">
            <thead id="thead">
            <tr>
                <th style="width: 120px;">Ряд / Место</th>
                <th>1</th>
                <th>2</th>
                <th>3</th>
            </tr>
            </thead>
            <tbody id="tbody">
            </tbody>
        </table>
    </div>
    <div class="row float-right">
        <button type="submit" class="btn btn-success" onclick="forward()">Оплатить</button>
    </div>
</div>

</body>
</html>

