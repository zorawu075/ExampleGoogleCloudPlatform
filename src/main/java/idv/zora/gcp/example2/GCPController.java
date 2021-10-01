package idv.zora.gcp.example2;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gcp2")
public class GCPController {

    private static final String BUCKET_NAME = "bucketName";
    private static final String FILE_PATH = "TEST/announcement/";

    @PostMapping(path = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam String fileName)
            throws IOException {
        GoogleCloudPlatformStorageUtil.upload(BUCKET_NAME, FILE_PATH + fileName, file.getBytes());
        return "upload done";
    }

    @PostMapping(path = "/copy")
    public String copy(@RequestParam(name = "file") String fileName) {
        // get extension
        int indexOfDot = fileName.indexOf(".");
        String fileOnlyName = fileName.substring(0, indexOfDot);
        String extension = fileName.substring(indexOfDot);

        String filePath = FILE_PATH;
        String oriFileAndPath = filePath + fileName;
        String newFileAndPath = filePath + fileOnlyName + "-copy" + extension;

        GoogleCloudPlatformStorageUtil.copy(BUCKET_NAME, oriFileAndPath, newFileAndPath);
        return "copy done";
    }

    @DeleteMapping(path = "/delete")
    public String delete(@RequestParam(name = "file") String fileName) {
        GoogleCloudPlatformStorageUtil.delete(BUCKET_NAME, FILE_PATH + fileName);
        return "delete done";
    }

}
