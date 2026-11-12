package controller;

import dao.ScoreDAO;
import model.GameScore;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * LeaderboardServlet — Retrieves the top 10 scores and forwards to leaderboard.jsp.
 */
public class LeaderboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ScoreDAO dao = new ScoreDAO();
        List<GameScore> topScores = dao.getTopScores();
        req.setAttribute("topScores", topScores);
        req.getRequestDispatcher("leaderboard.jsp").forward(req, resp);
    }
}

