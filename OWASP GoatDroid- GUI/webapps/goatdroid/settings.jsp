<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org   /TR/html4/loose.dtd">

<html>
<head>
<jsp:include page="application.html" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settings</title>
</head>
<body>
	<jsp:include page="common/header.html" />

	<div class="container-fluid">
		<div class="dashboard-wrapper">
			<div id="main-container">
				<div class="row-fluid">
					<div class="span12">
						<div class="widget">
							<div class="widget-header">
								<div class="title">
									<span class="fs1" aria-hidden="true" data-icon="&#xe022;"></span>
									Android SDK Configuration
								</div>
							</div>
							<div class="widget-body">
								<form class="form-horizontal no-margin">
									<div class="control-group">
										<label class="control-label"> Proxy Host</label>
										<div class="controls">
											<input type="text" placeholder="Proxy host IP">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"> Proxy Port</label>
										<div class="controls">
											<input type="text" placeholder="Proxy port">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"> Emulator Screen Size</label>
										<div class="controls">
											<input type="text" placeholder="Must be between x and y">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"> Android SDK Path </label>
										<div class="controls">
											<input type="file" placeholder="Path to Android SDK">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"> Virtual Devices Path </label>
										<div class="controls">
											<input type="file"
												placeholder="This is the path where Android SDK stores vms" />
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											<button type="submit" class="btn btn-info">Submit</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span12">
							<div class="widget">
								<div class="widget-header">
									<div class="title">
										<span class="fs1" aria-hidden="true" data-icon="&#xe022;"></span>
										GoatDroid Config
									</div>
								</div>
								<div class="widget-body">
									<form class="form-horizontal no-margin">
										<div class="control-group">
											<label class="control-label"> Web UI Port </label>
											<div class="controls">
												<input type="text" placeholder="Port to tutorial website">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"> Web Service Port </label>
											<div class="controls">
												<input type="text"
													placeholder="Listening web service for apps and configuration API" />
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												<button type="submit" class="btn btn-info">Submit</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>

					</div>

				</div>
			</div>
		</div>
	</div>
	<jsp:include page="common/footer.html" />
	</div>
	</div>
</body>
</html>