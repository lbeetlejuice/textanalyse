<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
  <html lang="de">
    <head>
      <title>About - Textanalyse</title>
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
      <h1>Algorithmen und Datenstrukturen - 2016</h1>
      <h2>Praktikum 11</h2>
      <p>Beschreibung:</p>
      <p>
        In diesem Praktikum „bauen“ Sie ein System zur automatischen Text-Analyse,
        mit dem Sie Produkt-Reviews analysieren können. Dabei können Sie die
        verschiedenen Algorithmen und Datenstrukturen einsetzen, die Sie bisher
        bereits in der Vorlesung kennengelernt haben, und auch neue anwenden.
      </p><br>
      <p>Aufgabe 1:</p>
      <p>
        Geben Sie grundlegende statistische Informationen über die Texte aus, z.B.
        <br>- Anzahl Dokumente
        <br>- Gesamtgrösse der Dokumente (z. B. in KB oder MB)
        <br>- Anzahl Wörter
        <br>- Anzahl unterschiedliche Wörter
        <br>- Durchschnittliche Textlänge (Zeichen pro Dokument)
        <br>Erstellen Sie einen Index, in dem alle Wörter aus allen Texten mit
        ihren Häufigkeiten aufgelistet werden.
        <br>Implementieren Sie eine rudimentäre Suchmaschine, mit der Sie nach
        einzelnen Wörtern suchen können.
      </p><br>
      <p>Aufgabe 2:</p>
      <p>
        Erweitern Sie Ihr Text-Analyse-System um neue, sinnvolle, spannende
        Funktionalität. Was Sie genau implementieren ist Ihnen überlassen.
        <br>- exakte Suche nach mehreren Wörtern, die mit UND oder ODER verknüpft
        sind
        <br>- exakte Suche nach zwei Wörter, wobei das erste Wort vorkommen muss
        und das zweite Wort NICHT vorkommen darf
        <br>- Filtern nach Dokumenten mit einer bestimmten Länge
        <br>- Approximative Suche, die auch gewisse Schreibfehler berücksichtigt
        (-> Levenshtein-Distanz)
        <br>- Visuelle Darstellung, welche Themen in einer Dokumentmenge „wichtig“
        sind (-> Topic Detecion, Tag-Cloud)
        <br>Für die Implementierung von einzelnen Features können Sie bei Bedarf
        auf existierende Software-Pakete verwenden.
      </p>
      <br><p>Lukas Buchter, Frank Holzach, Savin Niederer</p>
      </div>
    </main>
  </body>
</html>
