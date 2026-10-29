<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Booking</title>
</head>
<body>

	<form action="bookRide" method="post">
	 Mobile      : 	<input type="text" name="mobile"><br><br> 
	 Source      :	<input type="text" name="source"><br> <br> 
	 Destination : 	<input type="text" name="destination"><br><br> 
	 Ride Type   :	<input type="text" name="rideType"><br> <br> 
	 Amount      :	<input type="text" name="amount"><br><br> 
	 
	 <input type="submit" value="Book Ride">

	</form>
	
</body>
</html>