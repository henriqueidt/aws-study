package org.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        List<Bucket> allBuckets;

        try {
            String configFilePath = "config.properties";
            FileInputStream configStream = new FileInputStream(configFilePath);
            props.load(configStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(props.getProperty("accessKey"));

        AwsCredentials credentials = AwsBasicCredentials.create(
                props.getProperty("accessKey"),
                props.getProperty("secretKey"));

        String region = "us-east-1";
        S3Client s3 = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        allBuckets = listBuckets(s3);

        listObjects(s3, allBuckets.getFirst().name());


        // PutObjectResponse uploadResponse = uploadFileAsync(allBuckets.getFirst().name(), "src/main/resources/IMG_0661.jpg", "carInfo", s3);
        // System.out.println("Uploaded file: " + uploadResponse);
    }

    private static List<Bucket> listBuckets(S3Client s3Client) {
        
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            List<Bucket> allBuckets = response.buckets();

            allBuckets.forEach(bucket -> {
                System.out.println("Bucket: " + bucket.name()); 
            });
            return allBuckets;
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
            return null;
        }
    }

    private static PutObjectResponse uploadFileAsync(String bucketName, String objectPath, String key, S3Client s3Client) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.putObject(request, RequestBody.fromString(objectPath));
    }

    private static void listObjects(S3Client s3Client, String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        List<S3Object> objects = listObjectsV2Response.contents();

        objects.stream().forEach(System.out::println);
    }
}