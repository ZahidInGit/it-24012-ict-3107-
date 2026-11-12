<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Whiz — Game</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%
    String playerName = (String) session.getAttribute("playerName");
    int primeNumber    = (int) session.getAttribute("primeNumber");
    int modA           = (int) session.getAttribute("modA");
    int modB           = (int) session.getAttribute("modB");
    String trigFunc1   = (String) session.getAttribute("trigFunc1");
    int trigAngle1     = (int) session.getAttribute("trigAngle1");
    String trigFunc2   = (String) session.getAttribute("trigFunc2");
    int trigAngle2     = (int) session.getAttribute("trigAngle2");
%>

<div class="container">
    <h1>Zahid Fahamid &mdash; MATH WHIZ</h1>
    <h2>Good luck, <strong><%= playerName %></strong>! Answer all four questions.</h2>

    <form action="game" method="post">
        <input type="hidden" name="action" value="submit">

        <%-- Q1: Prime Number (2 marks) --%>
        <div class="question-card">
            <span class="badge badge-prime">Prime Number</span>
            <h3>Question 1</h3>
            <p>Is <strong><%= primeNumber %></strong> a prime number?</p>
            <p style="color:#888; font-size:0.9rem;">Enter <strong>1</strong> for Prime, <strong>0</strong> for Not Prime</p>
            <input type="number" name="answer1" placeholder="1 or 0" required
                   min="0" max="1" step="1">
            <p class="marks-label">2 Marks</p>
        </div>

        <%-- Q2: Modulus (2 marks) --%>
        <div class="question-card">
            <span class="badge badge-mod">Modulus</span>
            <h3>Question 2</h3>
            <p>What is <strong><%= modA %> % <%= modB %></strong> ?</p>
            <input type="number" name="answer2" placeholder="Your answer" required>
            <p class="marks-label">2 Marks</p>
        </div>

        <%-- Q3: Trigonometry #1 (3 marks) --%>
        <div class="question-card">
            <span class="badge badge-trig">Trigonometry</span>
            <h3>Question 3</h3>
            <p>Calculate <strong><%= trigFunc1 %>(<%= trigAngle1 %>&deg;)</strong></p>
            <p style="color:#888; font-size:0.9rem;">Round to 4 decimal places (tolerance &plusmn; 0.01)</p>
            <input type="number" name="answer3" step="any" placeholder="e.g. 0.5000" required>
            <p class="marks-label">3 Marks</p>
        </div>

        <%-- Q4: Trigonometry #2 (3 marks) --%>
        <div class="question-card">
            <span class="badge badge-trig">Trigonometry</span>
            <h3>Question 4</h3>
            <p>Calculate <strong><%= trigFunc2 %>(<%= trigAngle2 %>&deg;)</strong></p>
            <p style="color:#888; font-size:0.9rem;">Round to 4 decimal places (tolerance &plusmn; 0.01)</p>
            <input type="number" name="answer4" step="any" placeholder="e.g. 1.0000" required>
            <p class="marks-label">3 Marks</p>
        </div>

        <button type="submit" class="btn btn-primary btn-block">
            &#10004; Submit Answers
        </button>
    </form>
</div>

<div class="footer">&copy; 2026 Math Whiz &mdash; Java Servlet &amp; JDBC Lab Project</div>

</body>
</html>

