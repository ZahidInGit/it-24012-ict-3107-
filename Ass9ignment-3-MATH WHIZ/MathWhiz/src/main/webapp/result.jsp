<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Whiz — Results</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%
    String playerName   = (String)  request.getAttribute("playerName");
    int    totalScore    = (int)     request.getAttribute("totalScore");
    boolean primeCorrect = (boolean) request.getAttribute("primeCorrect");
    boolean modCorrect   = (boolean) request.getAttribute("modCorrect");
    boolean trig1Correct = (boolean) request.getAttribute("trig1Correct");
    boolean trig2Correct = (boolean) request.getAttribute("trig2Correct");

    int    expectedPrime = (int)     request.getAttribute("expectedPrime");
    int    expectedMod   = (int)     request.getAttribute("expectedMod");
    String expectedTrig1 = (String)  request.getAttribute("expectedTrig1");
    String expectedTrig2 = (String)  request.getAttribute("expectedTrig2");

    int    primeNumber   = (int)     request.getAttribute("primeNumber");
    int    modA          = (int)     request.getAttribute("modA");
    int    modB          = (int)     request.getAttribute("modB");
    String trigFunc1     = (String)  request.getAttribute("trigFunc1");
    int    trigAngle1    = (int)     request.getAttribute("trigAngle1");
    String trigFunc2     = (String)  request.getAttribute("trigFunc2");
    int    trigAngle2    = (int)     request.getAttribute("trigAngle2");
%>

<div class="container">
    <h1>Zahid Fahamid &mdash; MATH WHIZ</h1>
    <h2>Results for <strong><%= playerName %></strong></h2>

    <div class="score-display">
        <div class="score-value"><%= totalScore %> / 10</div>
        <div class="score-label">Your Final Score</div>
    </div>

    <%-- Detailed breakdown --%>
    <div class="question-card">
        <div class="result-row">
            <span>Q1 &mdash; Is <%= primeNumber %> prime? (Answer: <%= expectedPrime %>)</span>
            <span class="<%= primeCorrect ? "correct" : "incorrect" %>">
                <%= primeCorrect ? "✔ +2" : "✘  0" %>
            </span>
        </div>
        <div class="result-row">
            <span>Q2 &mdash; <%= modA %> %% <%= modB %> = ? (Answer: <%= expectedMod %>)</span>
            <span class="<%= modCorrect ? "correct" : "incorrect" %>">
                <%= modCorrect ? "✔ +2" : "✘  0" %>
            </span>
        </div>
        <div class="result-row">
            <span>Q3 &mdash; <%= trigFunc1 %>(<%= trigAngle1 %>&deg;) = ? (Answer: <%= expectedTrig1 %>)</span>
            <span class="<%= trig1Correct ? "correct" : "incorrect" %>">
                <%= trig1Correct ? "✔ +3" : "✘  0" %>
            </span>
        </div>
        <div class="result-row">
            <span>Q4 &mdash; <%= trigFunc2 %>(<%= trigAngle2 %>&deg;) = ? (Answer: <%= expectedTrig2 %>)</span>
            <span class="<%= trig2Correct ? "correct" : "incorrect" %>">
                <%= trig2Correct ? "✔ +3" : "✘  0" %>
            </span>
        </div>
    </div>

    <div class="nav-links">
        <a href="index.jsp"    class="btn btn-primary">&#9654; Play Again</a>
        <a href="leaderboard"  class="btn btn-secondary">&#127942; Leaderboard</a>
    </div>
</div>

<div class="footer">&copy; 2026 Math Whiz &mdash; Java Servlet &amp; JDBC Lab Project</div>

</body>
</html>

