<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
  <html lang="de">
    <head>
      <title>Auswertung - Textanalyse</title>
      <meta charset="utf-8" />
      <link rel="shortcut icon" href="img/fav.ico" type="image/x-icon">
      <link rel="stylesheet" href="css/main.css">
      <script src="js/main.js" charset="utf-8"></script>

      <!-- CHART -->
      <link href="js/chart/build/css/style.css" media="all" rel="stylesheet" type="text/css" />
      <link href="js/chart/build/css/horizBarChart.css" media="all" rel="stylesheet" type="text/css" />
      <script src="js/chart/build/js/jquery.min.js"></script>
      <script src="js/chart/build/js/jquery.horizBarChart.min.js"></script>
      <script type="text/javascript">
        $(document).ready(function(){
          $('.chart').horizBarChart({
            selector: '.bar',
            speed: 1000
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
        <!-- CHART -->
        <div class="chart-horiz">
          <h2>WORTHÄUFIGKEIT</h2>
          <ul class="chart">
          <c:forEach var="listItem" items="${topList}">
        	<li class="current" title="<c:out value="${listItem.value}" />"><span class="bar" data-number="<c:out value="${listItem.key}" />"></span><span class="number"><c:out value="${listItem.key}" /></span></li>
          </c:forEach>
          </ul>
        </div>
        <br><h2>WEITERE DATEN</h2>
        <p>Anzahl Dokumente: <c:out value="${numberOfDocuments}" />
        <br>Gesamtgrösse der Dokumente: <c:out value="${totalSizeOfDocuments}" />
        <br>Anzahl Wörter: <c:out value="${numberOfWords}" />
        <br>Anzahl unterschiedliche Wörter: <c:out value="${numberOfDifferentWords}" />
        <br>Durchschnittliche Textlänge (Zeichen pro Dokument): <c:out value="${averageDocumentSize}" /></p>
      </div>
    </main>
  </body>
</html>
