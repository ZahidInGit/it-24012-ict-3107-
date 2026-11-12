<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Whiz — Welcome</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1>Zahid Fahamid &mdash; MATH WHIZ</h1>
    <h2>Web-Based Mathematical Game</h2>

    <%-- Error message (if any) --%>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div class="error-message"><%= error %></div>
    <% } %>

    <form action="game" method="post">
        <input type="hidden" name="action" value="start">

        <div class="form-group">
            <label for="playerName">Enter Your Name</label>
            <input type="text" id="playerName" name="playerName"
                   placeholder="e.g. John Doe" required>
        </div>

        <button type="submit" class="btn btn-primary btn-block">
            &#9654; Start Game
        </button>
    </form>

    <div class="nav-links">
        <a href="leaderboard" class="btn btn-secondary">&#127942; View Leaderboard</a>
    </div>
</div>

<div class="footer">&copy; 2026 Math Whiz &mdash; Java Servlet &amp; JDBC Lab Project</div>

</body>
</html>

