<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
	<div class="sidebar" id="sidebar">
		<script type="text/javascript">
			try {
				ace.settings.check('sidebar', 'fixed')
			} catch (e) {
			}
		</script>

		<!-- #sidebar-shortcuts -->

		<ul class="nav nav-list">
			<li class="active"><a href="index.html"> <i
					class="icon-dashboard"></i> <span class="menu-text">
						Dashboard </span>
			</a></li>

			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-desktop"></i> <span class="menu-text"> Lessons </span>
					<b class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="/lessons/all"> <i
							class="icon-double-angle-right"></i> All
					</a></li>

					<li><a href="/lessons/top-10"> <i
							class="icon-double-angle-right"></i> OWASP Mobile Top 10 Risks
					</a></li>

					<li><a href="/lessons/developer"> <i
							class="icon-double-angle-right"></i> Developer Track
					</a></li>

					<li><a href="/lessons/pentester"> <i
							class="icon-double-angle-right"></i> Pentester Track
					</a></li>
				</ul></li>

			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-leaf"></i> <span class="menu-text"> Apps </span> <b
					class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="/apps/fourgoats"> <i
							class="icon-double-angle-right"></i> FourGoats
					</a></li>

					<li><a href="/apps/herdfinancial"> <i
							class="icon-double-angle-right"></i> Herd Financial
					</a></li>
				</ul></li>

			<li><a href="#" class="dropdown-toggle"> <i
					class="icon-cogs"></i> <span class="menu-text"> Tools </span> <b
					class="arrow icon-angle-down"></b>
			</a>

				<ul class="submenu">
					<li><a href="form-elements.html"> <i
							class="icon-double-angle-right"></i> Form Elements
					</a></li>

					<li><a href="form-wizard.html"> <i
							class="icon-double-angle-right"></i> Wizard &amp; Validation
					</a></li>

					<li><a href="wysiwyg.html"> <i
							class="icon-double-angle-right"></i> Wysiwyg &amp; Markdown
					</a></li>

					<li><a href="dropzone.html"> <i
							class="icon-double-angle-right"></i> Dropzone File Upload
					</a></li>
				</ul></li>

		</ul>
		<!-- /.nav-list -->
	</div>
</body>
</html>