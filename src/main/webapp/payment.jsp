<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Оплата</title>
</head>
<body onload="getNumberPlace();">

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>

    function getNumberPlace() {
        var url = window.location.href;
        var paramId = url.split('?')[1].split('=')[1];
        var row = paramId.split('/')[0];
        var col = paramId.split('/')[1];
        console.log(paramId);
        console.log(row);
        console.log(col);
        var heder = document.getElementById('heder');
        heder.innerHTML = 'Вы выбрали ряд ' + row + ' место ' + col + ', Сумма : 500 рублей.';
    }

    function pay() {
        var url = window.location.href;
        var paramId = url.split('?')[1].split('=')[1];
        var row = paramId.split('/')[0];
        var col = paramId.split('/')[1];
        var place = url.split('?')[2].split('=')[1];
        // console.log(document.getElementById('username').value);
        // console.log($('#username').val());

        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/cinema/payment',
            data: JSON.stringify({
                row: 'row=' + row,
                col: 'col=' + col,
                username: 'username=' + $('#username').val(),
                phone: 'phone=' + $('#phone').val(),
                email: 'email=' + $('#email').val(),
                place: 'place=' + place
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json'
        }).done(function (data) {
            alert(data);
            window.location.href = 'http://localhost:8080/cinema';
        }).fail(function (err) {

        })
    }

    function validate() {
        valid = true;
        if (document.contact_form.username.value == ""){
            alert("Заполниет поле ФИО");
            valid = false;
        }
        if (document.contact_form.username.value == ""){
            alert("Заполниет поле телефон");
            valid = false;
        }
        if (document.contact_form.username.value == ""){
            alert("Заполниет поле Email");
            valid = false;
        }
        return valid;
    }

</script>

<div class="container">
    <div class="row pt-3">
        <h3 id='heder'>
            Вы выбрали ряд 1 место 1, Сумма : 500 рублей.
        </h3>
    </div>
    <div class="row">
        <form name="contact_form" onsubmit="return validate();">
            <div class="form-group">
                <label for="username">ФИО</label>
                <input type="text" class="form-control" name="username" id="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="phone">Номер телефона</label>
                <input type="text" class="form-control" name="phone" id="phone" placeholder="Номер телефона">
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="text" class="form-control" name="email" id="email" placeholder="Email">
            </div>
            <button type="submit" class="btn btn-success" onclick="pay();">Оплатить</button>
        </form>
    </div>
</div>
</body>
</html>