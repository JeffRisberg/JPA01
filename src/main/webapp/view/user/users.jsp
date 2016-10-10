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
            <h1>Users</h1>
        </div>
    </div>

    <c:if test="${!empty donors}">
        <h3>Users</h3>
        <a class="btn btn-default" href="<c:url value="/basket?acton=createuser" />">Create New User</a>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${donors}" var="donor">
                <tr>
                    <td>
                        <a href="<c:url value="/basket?acton=browseuser&userId=${donor.id}" />">${donor.lastName}, ${donor.firstName}</a>
                    </td>
                    <td>${donor.email}</td>
                    <td>
                        <a class="btn btn-danger" href="/basket?acton=edituser&userId=${donor.id}">Edit</a>
                        <a class="btn btn-danger" href="/basket?acton=deleteuser&userId=${donor.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

</body>
</html>
