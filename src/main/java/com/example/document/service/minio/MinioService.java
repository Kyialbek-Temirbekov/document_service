package com.example.document.service.minio;

import com.example.document.exception.MinioException;
import com.example.document.property.MinioProperties;
import com.example.document.util.FileUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public void putObject(String objectName, byte[] data) {
        try (var stream = new ByteArrayInputStream(data)) {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName)
                            .stream(stream, data.length, -1)
                            .contentType(FileUtil.getMimeType(stream)).build());
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }
    public byte[] getObject(String objectName) {
        try (var stream = minioClient.getObject(
                GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build())) {
            return stream.readAllBytes();
        }
        catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }
    public void removeObject(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }
}
