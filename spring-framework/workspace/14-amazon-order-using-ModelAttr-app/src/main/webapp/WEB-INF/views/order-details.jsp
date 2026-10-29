<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Amazon - Order Details</title>

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: Arial, Helvetica, sans-serif;
}

body {
	background: #eaeded;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	width: 550px;
	background: #fff;
	border-radius: 12px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, .2);
	overflow: hidden;
}

.header {
	background: #131921;
	color: #fff;
	padding: 20px;
	text-align: center;
}

.header h1 {
	font-size: 30px;
}

.header span {
	color: #ff9900;
}

.content {
	padding: 30px;
}

.order-card {
	background: #f8f8f8;
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 20px;
	line-height: 2;
}

.order-card h2 {
	color: #232f3e;
	margin-bottom: 15px;
	text-align: center;
}

.order-card p {
	font-size: 17px;
	color: #333;
}

.order-card strong {
	color: #111;
}

.status {
	color: #008000;
	font-weight: bold;
}

.btn {
	display: block;
	width: 200px;
	margin: 30px auto 0;
	text-align: center;
	text-decoration: none;
	background: #ff9900;
	color: #111;
	padding: 12px;
	font-weight: bold;
	border-radius: 8px;
	border: 1px solid #f08804;
	transition: .3s;
}

.btn:hover {
	background: #f08804;
}

.footer {
	background: #232f3e;
	color: #ddd;
	text-align: center;
	padding: 12px;
	font-size: 13px;
}
</style>

</head>
<body>

	<div class="container">

		<div class="header">
			<h1>
				amazon<span>.in</span>
			</h1>
		</div>

		<div class="content">

			<div class="order-card">

				<h2>📦 Order Details</h2>

				<p>
					<strong>Order ID :</strong> 1001
				</p>

				<p>
					<strong>Product :</strong> Apple AirPods Pro
				</p>

				<p>
					<strong>Price :</strong> ₹24,999
				</p>

				<p>
					<strong>Quantity :</strong> 1
				</p>

				<p>
					<strong>Delivery Date :</strong> 10-Aug-2026
				</p>

				<p>
					<strong>Status :</strong> <span class="status">Delivered ✓</span>
				</p>

			</div>

			<a href="index.jsp" class="btn"> ← Back to Orders </a>

		</div>

		<div class="footer">© 2026 Amazon Clone | Spring MVC Demo</div>

	</div>

</body>
</html>