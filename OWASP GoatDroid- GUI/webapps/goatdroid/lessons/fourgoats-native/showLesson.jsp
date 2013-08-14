<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org   /TR/html4/loose.dtd">

<html>
<head>
<jsp:include page="/application.html" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OWASP GoatDroid</title>
</head>
<body>
	<jsp:include page="/common/header.html" />

	<div class="container-fluid">
	<jsp:include page="sidebar.html" />
		<div class="dashboard-wrapper">
			<div id="main-container">
				<% 
				String lesson = request.getParameter("lesson");
				String render = "";
			     if (lesson.equals("insecureDataStorage")) { %>
			    	<jsp:include page="insecure-data-storage.html" />;
			    	
			    <% }
				%>	
				
			</div>
		</div>
	</div>
	<jsp:include page="/common/footer.html" />
	</div>
	</div>
</body>
</html>