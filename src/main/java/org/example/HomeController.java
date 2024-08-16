package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeSevice;

    @GetMapping("/downloadExcelFiles")
    public HttpEntity<?> getSevice() throws IOException {
        String strFileName =  homeSevice.converCSVToExcelFile();
        try {
            System.out.println(strFileName);
            // Read file into InputStreamResource
            String filePath = strFileName;
            Path path = Paths.get(filePath);
            String fileName = path.getFileName().toString();
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            // Handle other errors
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
            pd.setType(URI.create("http://my-app-host.com/errors/misc"));
            pd.setTitle("Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
        }
    }
}
