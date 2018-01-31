<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
  <html lang="de">
    <head>
      <title>Cloud - Textanalyse</title>
      <meta charset="utf-8" />
      <link rel="shortcut icon" href="img/fav.ico" type="image/x-icon">
      <link rel="stylesheet" href="css/main.css">
      <script src="js/main.js" charset="utf-8"></script>

      <!-- CLOUD -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
      <script src="js/cloud/cloud.js"></script>
      <style type="text/css">
        .wordcloud {
          border: 1px solid #036;
          height: 7in;
          margin: 0.5in auto;
          padding: 0;
          page-break-after: always;
          page-break-inside: avoid;
          width: 7in;
        }
      </style>
      <script type="text/javascript">

      $(document).ready(function(){
      $("#wordcloud").awesomeCloud({
        "size" : {
          "grid" : 9, // word spacing, smaller is more tightly packed
          "factor" : 1 // font resize factor, 0 means automatic
        },
        "options" : {
          "color" : "random-dark", // random-light, random-dark, gradient
          "rotationRatio" : 0.35 // 0 is all horizontal, 1 is all vertical
        },
        "font" : "'Times New Roman', Times, serif", // the CSS font-family string
        "shape" : "circle" // circle, square, star or a theta function describing a shape
      });
    });
      </script>

    </head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <body>
    <header class="header">
    </header>
    <nav class="navigation">
        <ul class="navpoints">
          <li><a href="<c:url value='/Analyse' />">Suche</a></li>
          <li><a href="<c:url value='/Analyse?action=statistics' />">Auswertung</a></li>
          <li><a href="<c:url value='/Analyse?action=cloud' />">Cloud</a></li>
          <li><a href="about.jsp">About</a></li>
        </ul>
    </nav>
    <main class="main">
      <div class="content">
      <!-- CLOUD -->
      <div id="wordcloud" class="wordcloud">
      
      <c:forEach var="listItem" items="${topList}">
        <span data-weight="<c:out value="${listItem.key / 300}" />"><c:out value="${listItem.value}" /></span>
      </c:forEach>
      
       </div>
      </div>
    </main>
  </body>
</html>
