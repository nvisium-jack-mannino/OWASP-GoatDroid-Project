<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OWASP GoatDroid</title>
</head>
<body>
	<div class="navbar navbar-default" id="navbar">
		<jsp:include page="header.jsp" />
	</div>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<jsp:include page="/WEB-INF/views/sidebar.jsp" />
		</div>
		<div class="main-content">
			<jsp:include page="${partial}" />
		</div>
		<jsp:include page="/WEB-INF/views/footer.jsp" />
	</div>
</body>
</html>