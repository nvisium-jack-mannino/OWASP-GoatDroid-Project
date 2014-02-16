<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<head>
<meta charset="utf-8" />
<title>Dashboard - Ace Admin</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- basic styles -->

<link
	href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/font-awesome.min.css" />

<!--[if IE 7]>
		  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/font-awesome-ie7.min.css" />
		<![endif]-->

<!-- page specific plugin styles -->

<!-- fonts -->

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/ace-fonts.css" />

<!-- ace styles -->

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/ace.min.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/ace-rtl.min.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/ace-skins.min.css" />

<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->

<script
	src="${pageContext.servletContext.contextPath}/resources/js/ace-extra.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-ui-1.10.3.custom.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery.ui.touch-punch.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery.slimscroll.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery.easy-pie-chart.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery.sparkline.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/flot/jquery.flot.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/flot/jquery.flot.pie.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/flot/jquery.flot.resize.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-2.0.3.min.js">
	
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script
	src="${pageContext.servletContext.contextPath}/resources/js/jquery.mobile.custom.min.js">
	
</script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="a${pageContext.servletContext.contextPath}/resources/js/typeahead-bs2.min.js"></script>

<!-- ace scripts -->

<script
	src="${pageContext.servletContext.contextPath}/resources/js/ace-elements.min.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/ace.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="${pageContext.servletContext.contextPath}/resources/js/html5shiv.js"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/js/respond.min.js"></script>
		<![endif]-->
</head>

<body>
	<div class="navbar navbar-default" id="navbar">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small> <i
						class="icon-bolt"></i> OWASP GoatDroid
				</small>
				</a>
				<!-- /.brand -->
			</div>
		</div>
	</div>
</body>