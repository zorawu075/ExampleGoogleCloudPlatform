package idv.zora.gcp.example2;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.io.InputStream;

public class GoogleCloudPlatformStorageUtil {

    private GoogleCloudPlatformStorageUtil(){}

    static Storage storage;

    static {
        InputStream inputStream = GoogleCloudPlatformStorageUtil.class.getClassLoader().getResourceAsStream("GcpStorageKey.json");
        Credentials credentials = null;
        try {
            credentials = GoogleCredentials.fromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    /**
     * @param filePath   ex: TEST/announcement/apple.png
     */
    public static void upload(String bucketName, String filePath, byte[] fileBlob) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath).build();
        storage.create(blobInfo, fileBlob);
    }

    /**
     * @param oriFileName   ex: TEST/announcement/apple.png
     * @param newFileName   ex: TEST/announcement/apple-copy.png
     */
    public static void copy(String sourceBucketName, String oriFileName, String newFileName){
        Blob blob = storage.get(sourceBucketName , oriFileName);
        blob.copyTo(sourceBucketName, newFileName);
    }

    /**
     * @param filePath   ex: TEST/announcement/apple.png
     */
    public static void delete(String bucketName, String filePath){
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath).build();
        storage.delete(blobInfo.getBlobId());
    }

}
