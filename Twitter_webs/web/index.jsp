<%@page import="com.cwk2.jaxrs.client.Client"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actor Properties</title>
</head>
<body>
<h1>Please select your actor's properties: </h1>
<form action="Servlet">
    <p>Gender:</p>
    <input type="radio" id="male" name="gender" value="male">
    <label for="male">Male</label><br>
    <input type="radio" id="female" name="gender" value="female">
    <label for="female">Female</label><br>

    <br>

    <p>Type:</p>
    <input type="radio" id="Young" name="type" value="Young">
    <label for="Young">Young</label><br>
    <input type="radio" id="Old-School" name="type" value="Old-School">
    <label for="Old-School">Old-School</label><br>

    <input href="/Servlet" type="submit" value="Submit">

</form>
</body>
</html>