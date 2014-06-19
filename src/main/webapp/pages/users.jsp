<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8">
    <title>JAP01</title>

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

    <c:if test="${!empty users}">
        <h3>Users</h3>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>
                        <a href="<c:url value="/show/${user.id}" />">${user.lastName}, ${user.firstName}</a>
                    </td>
                    <td>${user.email}</td>
                    <td>
                        <form action="delete/${user.id}" method="post">
                            <input type="submit"
                                   class="btn btn-danger btn-mini"
                                   value="Delete"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

</body>
</html>
