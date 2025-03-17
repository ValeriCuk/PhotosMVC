<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Photos</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1><a href="/" class="home-button">Home</a></h1>
<br/>
<h2>Photos</h2>

<div class="image-container">
  <c:forEach var="entry" items="${photos}">
    <div class="image-item">
      <img src="/photo/${entry.key}" alt="NoImg"/>
      <div class="id">ID: ${entry.key}</div>
    </div>
  </c:forEach>
</div>
</body>
</html>
