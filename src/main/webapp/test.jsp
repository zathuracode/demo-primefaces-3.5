<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="java:jboss/datasources/saludmp-oracleDS-SAO">
SELECT cust_id, name FROM public.customer
</sql:query>

<html>
  <head>
    <title>DB Test</title>
  </head>
  <body>

  <h2>Results</h2>

<c:forEach var="row" items="${rs.rows}">
    Foo ${row.cust_id}<br/>
    Bar ${row.name}<br/>
</c:forEach>

  </body>
</html>