<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<body>
<h2>test</h2>
${info}
<c:forEach items="${list }" var="i">
数据库信息: ${i.title}
</c:forEach>
</body>
</html>
