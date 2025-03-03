package sg.edu.nus.iss.csf.workshop36.server.services;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.csf.workshop36.server.models.Post;
import sg.edu.nus.iss.csf.workshop36.server.repositories.FileUploadRepository;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;
    

    public String upload(MultipartFile file, String comments) 
        throws SQLException, IOException{
        return fileUploadRepository.upload(file, comments);
    }

    public Optional<Post> getPostById(String postId){
        return fileUploadRepository.getPostById(postId);
    }
    
}
