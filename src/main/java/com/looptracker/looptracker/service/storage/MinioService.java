package com.looptracker.looptracker.service.storage;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
@Service
public class MinioService implements IMinioService{
    private static final String FORWARD_SLASH = "/";
    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(MinioClient minioClient,
                        @Value("${minio.bucket.name}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @Override
    public String uploadFile(String path, String fileName, InputStream fileStream, String contentType) throws Exception {
        String objectName = path + "/" + fileName; // Gộp path và tên file
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(fileStream, fileStream.available(), -1)
                        .contentType(contentType)
                        .build()
        );

        return objectName;
    }

    @Override
    public InputStream download(String folderSource, String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName)
                    .object(folderSource+"/"+fileName).build());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public InputStream download(String path) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName)
                    .object(path.replaceFirst(FORWARD_SLASH, "")).build());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteFile(String path, String fileName) throws Exception {
        String objectName = path + "/" + fileName;
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    @Override
    public void deleteFile(String path) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .build()
        );
    }

    private String joinPaths(String... paths) {
        StringBuilder builder = new StringBuilder();
        int pathsLength = paths.length;
        for (int i = 0; i < pathsLength; i++) {
            String path = paths[i];
            if (i != 0 && path.startsWith(FORWARD_SLASH)) {
                path = path.substring(1);
            }
            if (path.endsWith(FORWARD_SLASH)) {
                path = path.substring(0, path.length() - 1);
            }
            if (!path.isEmpty()) {
                builder.append(path);
                if (i < pathsLength - 1 && !path.endsWith(FORWARD_SLASH)) {
                    builder.append(FORWARD_SLASH);
                }
            }
        }
        return builder.toString();
    }
}
