<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IRCTC PNR Status Check</title>

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
	width: 420px;
	background: white;
	border-radius: 10px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
	overflow: hidden;
}

.header {
	background: linear-gradient(90deg, #0056a6, #0b79d0);
	color: white;
	padding: 20px;
	text-align: center;
}

.header h2 {
	font-size: 28px;
}

.header p {
	margin-top: 5px;
	font-size: 14px;
}

.form-container {
	padding: 30px;
}

label {
	font-weight: bold;
	color: #333;
	display: block;
	margin-bottom: 8px;
}

input[type=text] {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
	margin-bottom: 20px;
}

input[type=text]:focus {
	outline: none;
	border-color: #0056a6;
	box-shadow: 0 0 5px rgba(0, 86, 166, .4);
}

input[type=submit] {
	width: 100%;
	padding: 12px;
	background: #ff7a00;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 18px;
	font-weight: bold;
	cursor: pointer;
	transition: .3s;
}

input[type=submit]:hover {
	background: #e86d00;
}

.footer {
	text-align: center;
	background: #f8f8f8;
	padding: 12px;
	color: #666;
	font-size: 13px;
}
</style>

</head>
<body>

	<div class="container">

		<div class="header">
			<h2>🚆 IRCTC</h2>
			<p>Passenger Reservation Status</p>
		</div>

		<div class="form-container">

			<form action="pnrCheck" method="get">

				<label>Enter 10-Digit PNR Number</label> <input type="text"
					name="pnrNumber" maxlength="10" placeholder="e.g. 1234567890"
					required> <input type="submit" value="Check PNR Status">

			</form>

		</div>

		<div class="footer">Indian Railway Catering & Tourism
			Corporation Ltd.</div>

	</div>

</body>
</html>