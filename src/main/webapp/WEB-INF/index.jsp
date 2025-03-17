<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
  <form action="/view" method="POST">
    Photo id: <input type="text" name="photo_id" >
    <input type="submit" value="Get Photo"/>
  </form>

  <form action="/add_photo" enctype="multipart/form-data" method="POST">
    Photo: <input type="file" name="photo">
    <input type="submit" value="Upload Photo"/>
  </form>

  <form action="/all_photos" method="GET">
    <input type="submit" value="Show all">
  </form>
</div>
</body>
</html>