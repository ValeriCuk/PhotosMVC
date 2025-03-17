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
  <form action="/deletePhotos" method="POST">
    <c:forEach var="entry" items="${photos}">
      <div class="image-item">
        <img src="/photo/${entry.key}" style="width:200px;height:200px; object-fit:cover; border-radius:10px;" alt="NoImg"/>
        <div class="id">ID: ${entry.key}</div>
        <br>
        Mark for deletion
        <input type="checkbox" name="photoIds" value="${entry.key}" id="photo_${entry.key}">
        <br>
        <br>
        <br>
      </div>
    </c:forEach>
    <br>
    <br>
    <input type="submit" value="Delete">
  </form>
  <br>
  <br>
  <a href="/all_photos/downloadZip" class="download-button">Download All Photos as ZIP</a>
</div>
</body>
</html>
