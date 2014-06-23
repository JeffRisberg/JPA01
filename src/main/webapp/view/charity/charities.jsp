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
            <h1>Charities</h1>
        </div>
    </div>

    <c:if test="${!empty charities}">
        <h3>Your list</h3>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Mission</th>
                <th>Revenue</th>
                <th>Has Chapters</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${charities}" var="charity">
                <tr>
                    <td>
                            ${charity.name}
                    </td>
                    <td>
                            ${charity.mission}
                    </td>
                    <td style="text-align: right">
                        <fmt:formatNumber value="${charity.revenue}" type="currency"/>
                    </td>
                    <td>
                            ${charity.hasChapters}
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td colspan="3">
                        <c:forEach items="${charity.charityPrograms}" var="charityProgram">
                            ${charityProgram.name} program ${charityProgram.description}<br/>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

</body>
</html>
