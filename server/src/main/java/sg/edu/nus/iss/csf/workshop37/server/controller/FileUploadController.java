package sg.edu.nus.iss.csf.workshop37.server.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.csf.workshop37.server.models.Post;
import sg.edu.nus.iss.csf.workshop37.server.services.FileUploadService;

@Controller
public class FileUploadController {
    private static final String BASE64_PREFIX = "data:image/png;base64,";

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping(path="/api/upload", 
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("comments") String comments) {

        String postId = "";
        try{
            postId = this.fileUploadService.upload(file, comments);
            System.out.println("Post ID: " + postId);
        }catch(SQLException | IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
        JsonObject payload=  Json.createObjectBuilder()
            .add("postId", postId)
            .build();
        return ResponseEntity.ok(payload.toString());
    }

    @GetMapping(path="/api/get-image/{postId}")
    public ResponseEntity<String> getImage(@PathVariable String postId) {
        Optional<Post> r = this.fileUploadService.getPostById(postId);
        Post p = r.get();
        String encodingString = Base64.getEncoder().encodeToString(p.getImage());
        JsonObject payload = Json.createObjectBuilder()
            .add("image", BASE64_PREFIX + encodingString)
            .build();
        return ResponseEntity.ok(payload.toString());
    }
}
