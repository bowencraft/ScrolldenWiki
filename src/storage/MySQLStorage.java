package storage;

import wiki.WikiPage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLStorage implements DataStorage {
    private final String URL = "jdbc:mysql://localhost:3306/scrollden";
    private final String USER = "root";
    private final String PASSWORD = "yourpassword";

    @Override
    public void savePages(List<WikiPage> pages) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            for (WikiPage page : pages) {
                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO wiki_pages (title, content, views, likes, dislikes) VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setString(1, page.getTitle());
                stmt.setString(2, page.getContent());
                stmt.setInt(3, page.getViewCount());
                stmt.setInt(4, page.getLikeCount());
                stmt.setInt(5, page.getDislikeCount());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WikiPage> loadPages() {
        List<WikiPage> pages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM wiki_pages")) {

            while (rs.next()) {
                WikiPage page = new WikiPage(
                        rs.getString("title"),
                        rs.getString("content")
                );
                page.setViews(rs.getInt("views"));
                page.setLikes(rs.getInt("likes"));
                page.setDislikes(rs.getInt("dislikes"));
                pages.add(page);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pages;
    }
}