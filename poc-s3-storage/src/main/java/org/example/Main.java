package org.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.SequencedCollection;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();

        try {
            String configFilePath = "config.properties";
            FileInputStream configStream = new FileInputStream(configFilePath);
            props.load(configStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(props.getProperty("accessKey"));

        AwsCredentials credentials = AwsBasicCredentials.create(props.getProperty("accessKey"), props.getProperty("secretKey"));

        String region = "us-west-2";
        S3Client s3 = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        System.out.println(listBuckets(s3));
    }

    private static SequencedCollection<Bucket> listBuckets(S3Client s3Client) {
        List<Bucket> allBuckets = new ArrayList<>();
        String nextToken = null;

        do {
            String continuationToken = nextToken;
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets(
                    request -> request.continuationToken(continuationToken)
            );

            allBuckets.addAll(listBucketsResponse.buckets());
            nextToken = listBucketsResponse.continuationToken();
        } while (nextToken != null);
        return allBuckets;
    }
}