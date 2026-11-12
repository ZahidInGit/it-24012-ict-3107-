<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Whiz — Leaderboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1>&#127942; Leaderboard</h1>
    <h2>Top 10 Scores</h2>

    <c:choose>
        <c:when test="${not empty topScores}">
            <table class="leaderboard-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Player Name</th>
                        <th>Score</th>
                        <th>Difficulty</th>
                        <th>Date &amp; Time</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="gs" items="${topScores}" varStatus="loop">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${loop.index == 0}">
                                        <span class="rank-gold">&#129351; 1</span>
                                    </c:when>
                                    <c:when test="${loop.index == 1}">
                                        <span class="rank-silver">&#129352; 2</span>
                                    </c:when>
                                    <c:when test="${loop.index == 2}">
                                        <span class="rank-bronze">&#129353; 3</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${loop.index + 1}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${gs.playerName}</td>
                            <td><strong>${gs.score}</strong> / 10</td>
                            <td>${gs.difficulty}</td>
                            <td>
                                <fmt:formatDate value="${gs.playedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p style="text-align:center; color:#aaa; margin:30px 0;">
                No scores yet. Be the first to play!
            </p>
        </c:otherwise>
    </c:choose>

    <div class="nav-links">
        <a href="index.jsp" class="btn btn-primary">&#9654; Play Game</a>
    </div>
</div>

<div class="footer">&copy; 2026 Math Whiz &mdash; Java Servlet &amp; JDBC Lab Project</div>

</body>
</html>

