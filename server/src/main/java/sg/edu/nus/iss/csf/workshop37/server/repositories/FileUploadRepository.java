package sg.edu.nus.iss.csf.workshop37.server.repositories;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.csf.workshop37.server.models.Post;

@Repository
public class FileUploadRepository {
    private static final String INSERT_POST 
        = "INSERT INTO posts (post_id, comments, picture) VALUES (?, ?, ?)";

    private static final String SELECT_POST_BY_ID 
        = "SELECT post_id, comments, picture FROM posts WHERE post_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String upload(MultipartFile file, String comments) {
        // Generate a unique postId
        String postId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        System.out.println(postId);
    
        try {
            // Attempt to read the file bytes
            byte[] fileBytes = file.getBytes();
    
            // Use JdbcTemplate to perform the update
            jdbcTemplate.update(INSERT_POST, ps -> {
                ps.setString(1, postId);
                ps.setString(2, comments);
                ps.setBytes(3, fileBytes);
            });
        } catch (IOException e) {
            // Handle the IOException here (e.g., log it or rethrow a custom exception)
            // For example:
            throw new RuntimeException("Failed to upload file content", e);
        }
    
        return postId;
    }

    public Optional<Post> getPostById(String postId) {
        return jdbcTemplate.query(
            SELECT_POST_BY_ID, 
            (ResultSet rs) -> {
                if(rs.next()) {
                    return Optional.of(Post.populate(rs));
                } else {
                    return Optional.empty();
                }
            }
        , postId);
    }   
}
