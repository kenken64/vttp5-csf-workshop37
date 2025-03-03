package sg.edu.nus.iss.csf.workshop36.server.repositories;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.csf.workshop36.server.models.Post;

@Repository
public class FileUploadRepository {
    private static final String INSERT_POST 
        = "INSERT INTO posts (post_id, comments, picture) VALUES (?, ?, ?)";

    private static final String SELECT_POST_BY_ID 
        = "SELECT post_id, comments, picture FROM posts WHERE post_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource; 

    public String upload(MultipartFile file, String comments) 
        throws SQLException, IOException{
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_POST)) {
            String postId = UUID.randomUUID()
                        .toString().replace("-","")
                        .substring(0,8);
            System.out.println(postId);
            ps.setString(1, postId);
            ps.setString(2, comments);
            ps.setBytes(3, file.getBytes());
            ps.executeUpdate();
            return postId;
        } 
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
