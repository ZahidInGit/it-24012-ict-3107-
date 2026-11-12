package controller;

import dao.ScoreDAO;
import model.GameScore;
import service.MathService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

/**
 * GameServlet — Handles all game flow:
 *   action=start   → generate questions and show the game page
 *   action=submit  → evaluate answers, save score, show the result page
 */
public class GameServlet extends HttpServlet {

    private static final Random random = new Random();

    // ================================================================
    // GET — redirect to the welcome page
    // ================================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    // ================================================================
    // POST — start game or submit answers
    // ================================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("start".equals(action)) {
            handleStartGame(req, resp);
        } else if ("submit".equals(action)) {
            handleSubmitAnswers(req, resp);
        } else {
            resp.sendRedirect("index.jsp");
        }
    }

    // ================================================================
    // Start Game — Generate four random questions
    // ================================================================
    private void handleStartGame(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String playerName = req.getParameter("playerName");
        if (playerName == null || playerName.trim().isEmpty()) {
            req.setAttribute("error", "Please enter your name to start the game.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("playerName", playerName.trim());

        // --- Q1: Prime Number ---
        int primeNumber = generatePrimeCandidate();
        session.setAttribute("primeNumber", primeNumber);

        // --- Q2: Modulus ---
        int modA = random.nextInt(95) + 5;   // 5 – 99
        int modB = random.nextInt(9) + 2;    // 2 – 10  (never 0)
        session.setAttribute("modA", modA);
        session.setAttribute("modB", modB);

        // --- Q3: Trig function #1 ---
        String trigFunc1 = randomTrigFunction();
        int trigAngle1   = randomAngle();
        session.setAttribute("trigFunc1", trigFunc1);
        session.setAttribute("trigAngle1", trigAngle1);

        // --- Q4: Trig function #2 ---
        String trigFunc2 = randomTrigFunction();
        int trigAngle2   = randomAngle();
        session.setAttribute("trigFunc2", trigFunc2);
        session.setAttribute("trigAngle2", trigAngle2);

        req.getRequestDispatcher("game.jsp").forward(req, resp);
    }

    // ================================================================
    // Submit Answers — Evaluate and score
    // ================================================================
    private void handleSubmitAnswers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String playerName = (String) session.getAttribute("playerName");

        if (playerName == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        int totalScore = 0;

        // ---------- Q1: Prime ----------
        int primeNumber     = (int) session.getAttribute("primeNumber");
        int expectedPrime   = MathService.isPrime(primeNumber);
        boolean primeCorrect = false;
        try {
            int playerPrime = Integer.parseInt(req.getParameter("answer1"));
            primeCorrect = (playerPrime == expectedPrime);
        } catch (NumberFormatException ignored) { }
        if (primeCorrect) totalScore += 2;

        // ---------- Q2: Modulus ----------
        int modA = (int) session.getAttribute("modA");
        int modB = (int) session.getAttribute("modB");
        int expectedMod = MathService.calculateMod(modA, modB);
        boolean modCorrect = false;
        try {
            int playerMod = Integer.parseInt(req.getParameter("answer2"));
            modCorrect = (playerMod == expectedMod);
        } catch (NumberFormatException ignored) { }
        if (modCorrect) totalScore += 2;

        // ---------- Q3: Trig #1 ----------
        String trigFunc1 = (String) session.getAttribute("trigFunc1");
        int trigAngle1   = (int) session.getAttribute("trigAngle1");
        double expectedTrig1 = computeTrig(trigFunc1, trigAngle1);
        boolean trig1Correct = false;
        try {
            double playerTrig1 = Double.parseDouble(req.getParameter("answer3"));
            trig1Correct = MathService.isCloseEnough(expectedTrig1, playerTrig1);
        } catch (NumberFormatException ignored) { }
        if (trig1Correct) totalScore += 3;

        // ---------- Q4: Trig #2 ----------
        String trigFunc2 = (String) session.getAttribute("trigFunc2");
        int trigAngle2   = (int) session.getAttribute("trigAngle2");
        double expectedTrig2 = computeTrig(trigFunc2, trigAngle2);
        boolean trig2Correct = false;
        try {
            double playerTrig2 = Double.parseDouble(req.getParameter("answer4"));
            trig2Correct = MathService.isCloseEnough(expectedTrig2, playerTrig2);
        } catch (NumberFormatException ignored) { }
        if (trig2Correct) totalScore += 3;

        // ---------- Save to database ----------
        GameScore gameScore = new GameScore(playerName, totalScore, "NORMAL");
        ScoreDAO dao = new ScoreDAO();
        dao.saveScore(gameScore);

        // ---------- Set result attributes ----------
        req.setAttribute("playerName", playerName);
        req.setAttribute("totalScore", totalScore);
        req.setAttribute("primeCorrect", primeCorrect);
        req.setAttribute("modCorrect", modCorrect);
        req.setAttribute("trig1Correct", trig1Correct);
        req.setAttribute("trig2Correct", trig2Correct);

        // Correct answers for display
        req.setAttribute("expectedPrime", expectedPrime);
        req.setAttribute("expectedMod", expectedMod);
        req.setAttribute("expectedTrig1", String.format("%.4f", expectedTrig1));
        req.setAttribute("expectedTrig2", String.format("%.4f", expectedTrig2));

        // Question text for display
        req.setAttribute("primeNumber", primeNumber);
        req.setAttribute("modA", modA);
        req.setAttribute("modB", modB);
        req.setAttribute("trigFunc1", trigFunc1);
        req.setAttribute("trigAngle1", trigAngle1);
        req.setAttribute("trigFunc2", trigFunc2);
        req.setAttribute("trigAngle2", trigAngle2);

        // Clean session game data
        session.removeAttribute("primeNumber");
        session.removeAttribute("modA");
        session.removeAttribute("modB");
        session.removeAttribute("trigFunc1");
        session.removeAttribute("trigAngle1");
        session.removeAttribute("trigFunc2");
        session.removeAttribute("trigAngle2");

        req.getRequestDispatcher("result.jsp").forward(req, resp);
    }

    // ================================================================
    // Helper methods
    // ================================================================

    /** Generate a random candidate for the prime question (2 – 100). */
    private int generatePrimeCandidate() {
        return random.nextInt(99) + 2;
    }

    /** Pick a random trig function: Sin, Cos or Tan. */
    private String randomTrigFunction() {
        String[] funcs = {"Sin", "Cos", "Tan"};
        return funcs[random.nextInt(funcs.length)];
    }

    /** Pick a random "nice" angle in degrees. */
    private int randomAngle() {
        int[] angles = {0, 30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};
        int angle;
        do {
            angle = angles[random.nextInt(angles.length)];
        } while (angle == 90 || angle == 270);   // avoid undefined tan
        return angle;
    }

    /** Compute the trig value using MathService. */
    private double computeTrig(String func, int angleDeg) {
        switch (func) {
            case "Sin": return MathService.calculateSin(angleDeg);
            case "Cos": return MathService.calculateCos(angleDeg);
            case "Tan": return MathService.calculateTan(angleDeg);
            default:    return 0;
        }
    }
}

