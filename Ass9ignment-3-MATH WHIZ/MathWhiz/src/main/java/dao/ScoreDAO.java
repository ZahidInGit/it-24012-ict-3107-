package dao;

import model.GameScore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ScoreDAO — Data Access Object for game_scores table.
 * Uses JDBC with PreparedStatement for secure database operations.
 */
public class ScoreDAO {

    // ----------------------------------------------------------------
    // JDBC connection parameters — update these for your environment
    // ----------------------------------------------------------------
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";   // <-- set your MySQL password

    // ----------------------------------------------------------------
    // Load MySQL JDBC driver once
    // ----------------------------------------------------------------
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    /**
     * Opens a new database connection.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // ================================================================
    // INSERT — Save a player's game result
    // ================================================================

    /**
     * Saves a game score record to the database.
     *
     * @param score the GameScore object to persist
     * @return true if the insert was successful
     */
    public boolean saveScore(GameScore score) {
        String sql = "INSERT INTO game_scores (player_name, score, difficulty) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, score.getPlayerName());
            ps.setInt(2, score.getScore());
            ps.setString(3, score.getDifficulty());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================================================================
    // SELECT — Retrieve the top 10 scores (leaderboard)
    // ================================================================

    /**
     * Returns the top 10 game scores ordered by score descending,
     * with played_at ascending as a tiebreaker.
     */
    public List<GameScore> getTopScores() {
        List<GameScore> scores = new ArrayList<>();
        String sql = "SELECT * FROM game_scores ORDER BY score DESC, played_at ASC LIMIT 10";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GameScore gs = new GameScore();
                gs.setId(rs.getInt("id"));
                gs.setPlayerName(rs.getString("player_name"));
                gs.setScore(rs.getInt("score"));
                gs.setDifficulty(rs.getString("difficulty"));
                gs.setPlayedAt(rs.getTimestamp("played_at"));
                scores.add(gs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }
}

