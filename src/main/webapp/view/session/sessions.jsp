<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
            <h1>Sessions</h1>
        </div>
    </div>

    <c:if test="${!empty sessions}">
        <h3>Your list</h3>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Date</th>
                <th>jSession Id</th>
                <th>Return Id</th>
                <th>Donor Id</th>
                <th>Vendor Id</th>
                <th>Affiliate Id</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessions}" var="session">
                <tr>
                    <td>
                            ${session.dateCreated}
                    </td>
                    <td>
                            ${session.JSessionId}
                    </td>
                    <td>
                            ${session.returnId}
                    </td>
                    <td>
                            ${session.donorId}
                    </td>
                    <td>
                            ${session.vendorId}
                    </td>
                    <td>
                            ${session.affiliateId}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

</body>
</html>
