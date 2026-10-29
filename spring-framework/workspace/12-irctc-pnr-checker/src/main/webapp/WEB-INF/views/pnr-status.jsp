<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PNR Status Result</title>

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: Arial, Helvetica, sans-serif;
}

body {
	background: #f2f5f9;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	width: 450px;
	background: white;
	border-radius: 10px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, .2);
	overflow: hidden;
}

.header {
	background: linear-gradient(to right, #0056a6, #0b79d0);
	color: white;
	text-align: center;
	padding: 20px;
}

.header h2 {
	font-size: 28px;
}

.content {
	padding: 30px;
	text-align: center;
}

.status {
	margin: 25px 0;
	font-size: 32px;
	font-weight: bold;
	color: #d32f2f;
	background: #fff3cd;
	padding: 15px;
	border-radius: 8px;
	border: 1px solid #ffeeba;
}

.btn {
	display: inline-block;
	margin-top: 20px;
	padding: 12px 25px;
	background: #ff7a00;
	color: white;
	text-decoration: none;
	border-radius: 5px;
	font-weight: bold;
	transition: .3s;
}

.btn:hover {
	background: #e56c00;
}

.footer {
	background: #f8f8f8;
	text-align: center;
	padding: 12px;
	font-size: 13px;
	color: #666;
}
</style>

</head>
<body>

	<div class="container">

		<div class="header">
			<h2>🚆 IRCTC</h2>
			<p>Passenger Reservation Status</p>
		</div>

		<div class="content">

			<h3>Your Current Booking Status</h3>

			<div class="status">WL - 49</div>

			<a href="index.jsp" class="btn"> Check Another PNR </a>

		</div>

		<div class="footer">Indian Railway Catering & Tourism
			Corporation Ltd.</div>

	</div>

</body>
</html>