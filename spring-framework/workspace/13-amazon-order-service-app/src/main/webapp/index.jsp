<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Amazon Orders</title>

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
	width: 450px;
	background: #fff;
	border-radius: 12px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, .2);
	overflow: hidden;
}

.header {
	background: #131921;
	color: #fff;
	text-align: center;
	padding: 25px;
}

.header h1 {
	font-size: 32px;
	font-weight: bold;
}

.header span {
	color: #ff9900;
}

.header p {
	margin-top: 8px;
	color: #ddd;
	font-size: 14px;
}

.content {
	padding: 35px;
	text-align: center;
}

.content h2 {
	color: #232f3e;
	margin-bottom: 15px;
}

.content p {
	color: #555;
	margin-bottom: 30px;
}

.btn {
	display: inline-block;
	background: #ff9900;
	color: #111;
	text-decoration: none;
	padding: 14px 35px;
	font-size: 18px;
	font-weight: bold;
	border-radius: 8px;
	transition: .3s;
	border: 1px solid #f08804;
}

.btn:hover {
	background: #f08804;
	transform: translateY(-2px);
}

.footer {
	background: #232f3e;
	color: #ddd;
	text-align: center;
	padding: 15px;
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
			<p>Track Your Orders</p>
		</div>

		<div class="content">
			<h2>📦 Order Management</h2>

			<p>Click the button below to view your order details.</p>

			<a href="orders/1001" class="btn"> View Order #1001 </a>
		</div>

		<div class="footer">© 2026 Amazon Clone | Spring MVC Demo</div>

	</div>

</body>
</html>