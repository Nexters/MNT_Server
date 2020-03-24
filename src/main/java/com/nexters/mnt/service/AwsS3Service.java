package com.nexters.mnt.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class AwsS3Service {

    private AmazonS3 s3Client;
    @Value("${aws.bucket.name}")
    private String bucketName;

    public AwsS3Service( @Value("${cloud.aws.credentials.access-key}") String accessKey, @Value("${cloud.aws.credentials.secret-key}") String secretKey ){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .enableForceGlobalBucketAccess()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public void uploadObject(MultipartFile multipartFile, String storedFileName) throws IOException{
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentDisposition("inline");
        Date date = new Date();

//        objectMetadata.setHeader("filename", multipartFile.getOriginalFilename());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName+"/mission",
                                                             storedFileName, multipartFile.getInputStream(), objectMetadata);
        s3Client.putObject(putObjectRequest);
    }

    public void deleteObject(String date, String storedFileName) throws AmazonServiceException{
        s3Client.deleteObject(new DeleteObjectRequest(bucketName+"/"+date, storedFileName));
    }

    public Resource getObject(String date, String storedFileName) throws IOException{
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName+"/"+date, storedFileName));
        S3ObjectInputStream objectInputStream = object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        Resource resource = new ByteArrayResource(bytes);
        return resource;
    }



}
