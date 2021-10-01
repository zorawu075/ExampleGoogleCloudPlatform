package idv.zora.gcp.example1;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/gcp1")
public class GCPExample {

    /*
        google doc: https://cloud.google.com/storage/docs/copying-renaming-moving-objects#code-samples
     */

    @PostMapping(path = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        // 1
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("GcpStorageKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        // 2
        BlobInfo blobInfo = BlobInfo.newBuilder("bucketName", "TEST/announcement/apple.png").build();
        // 3
        storage.create(blobInfo, file.getBytes());
        return "upload done";
    }

    @PostMapping(path = "/copy")
    public String copy() throws IOException {
        // 1
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("GcpStorageKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        // 2
        Blob blob = storage.get("bucketName" , "TEST/announcement/apple.png");
        // 3
        blob.copyTo("bucketName", "TEST/announcement/apple-copy.png");
        return "copy done";
    }

    @DeleteMapping(path = "/delete")
    public String delete() throws IOException {
        // 1
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("GcpStorageKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        // 2
        BlobInfo blobInfo = BlobInfo.newBuilder("bucketName", "TEST/announcement/apple.png").build();
        // 3
        storage.delete(blobInfo.getBlobId());
        return "delete done";
    }

}
