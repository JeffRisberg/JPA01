<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8">
    <title>JPA01</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="static/css/bootstrap.css" rel="stylesheet">
    <link href="static/css/bootstrap-theme.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <div class="row">
        <div class="span8 offset2">
            <h1>User Details</h1>

            <table style="background: #mmm">
                <tr>
                    <td>First Name:</td>
                    <td>${user.firstName}</td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td>${user.lastName}</td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>${user.email}</td>
                </tr>
            </table>
            <a href="<c:url value="/" />">Back</a>
        </div>
    </div>
</div>

</body>
</html>
