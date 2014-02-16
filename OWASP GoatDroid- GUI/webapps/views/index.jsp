<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org   /TR/html4/loose.dtd">

<html>
<head>
<jsp:include page="application.html" />
<script>
	$(document).ready(function() {
		initializeContext(url('?app'));
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OWASP GoatDroid</title>
</head>
<body>
	<jsp:include page="common/header.html" />

	<div class="container-fluid">
		<div id="sidebar"></div>
		<div class="dashboard-wrapper">
			<div id="main-container"></div>
		</div>
	</div>
	<jsp:include page="common/footer.html" />
	</div>
	</div>
</body>
</html>