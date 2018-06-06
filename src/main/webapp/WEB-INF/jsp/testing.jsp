<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:forEach var="myVar" items="${testing}">
${myVar.name}<br>
${myVar.password}<br>

<!-- // looping through the set of returned roles - if they are assigned 
// currently only the admin from test is assigned a role -->
   <c:forEach items="${myVar.roles}" var="entry">
       
      Role:  ${entry.role}<br>
		</c:forEach>
		<br>
	</c:forEach>
</body>
</html>