package app.taxifinderapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService {

    @Autowired
    private TripService tripService;
    private final Path rootLocation = Paths.get("signs");
    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    public void init(){
        try {
            Files.createDirectories(rootLocation);
            logger.info("directory created named : ".formatted(Files.createDirectories(rootLocation)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String store(MultipartFile file) {

        if(file.isEmpty()) {
            throw new RuntimeException("failed to store empty file");
        }
        String fileName = file.getOriginalFilename();
        logger.info("get the original name for my input " +fileName);
        String extension = tripService.getFileExtension(file);
        String uniqueName = UUID.randomUUID() + extension;
        logger.info(uniqueName + " unique name");
        try {

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(uniqueName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }

        return uniqueName;

    }

    public  Path load (String fileName) {
        return rootLocation.resolve(fileName);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
//           logger.info(file.toString());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public void delete(String filename) {
        try {
            Path file = load(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file.", e);
        }
    }


}
