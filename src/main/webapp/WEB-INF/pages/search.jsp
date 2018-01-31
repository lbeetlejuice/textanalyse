<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
  <html lang="de">
    <head>
      <title>Suche - Textanalyse</title>
      <meta charset="utf-8" />
      <link rel="shortcut icon" href="img/fav.ico" type="image/x-icon">
      <link rel="stylesheet" href="css/main.css">
      <script src="js/main.js" charset="utf-8"></script>
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
      	<form action="<c:url value='/Analyse' />" method="get">
	        <input type="text" class="searchbar" name="name" placeholder="Suche..." value="<c:out value="${word}" />" autofocus required>
	        <div class="options">
            <input type="hidden" name="action" value="search">
          <input type="submit" class="button" name="suche" value="Suche">
        </form>
        </div>
        <p>Anzahl gefundene Dateien: <span style="color:red;"><c:out value="${elements.size()}" /></span></p>
        <hr class="line">
        <table>

<c:forEach var="element" items="${elements}">
          <tr>
            <td class="user"><c:out value="${element.key.getAuthor()}" /></td>
            <td class="title"><c:out value="${element.key.getFileName()}" /></td>
          </tr>
          <tr>
            <td colspan="2" class="tablecontent"><c:out value="${element.key.getContent(element.value)}" escapeXml="false" /><br><br></td>
          </tr>
</c:forEach>

        </table>
      </div>
    </main>
  </body>
</html>
