-- ============================================================
-- Math Whiz Database Setup Script
-- ============================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS math_whiz_db;

-- Use the database
USE math_whiz_db;

-- Create the game_scores table
CREATE TABLE IF NOT EXISTS game_scores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    difficulty VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Verify the table structure
-- ============================================================
DESCRIBE game_scores;

-- ============================================================
-- Sample query: Retrieve top 10 scores (leaderboard)
-- ============================================================
-- SELECT * FROM game_scores ORDER BY score DESC, played_at ASC LIMIT 10;

