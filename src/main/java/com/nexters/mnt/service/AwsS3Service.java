package com.nexters.mnt.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AwsS3Service {

    private AmazonS3 s3Client;

    public AwsS3Service(){
        this.s3Client = AmazonS3ClientBuilder.standard().build();
    }

    @Value("${aws.bucket.name}")
    private String bucketName;

    public void uploadObject(MultipartFile multipartFile, String storedFileName) throws IOException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setHeader("filename", multipartFile.getOriginalFilename());

        s3Client.putObject(new PutObjectRequest(bucketName+"/"+ simpleDateFormat.format(new Date()),
                storedFileName, multipartFile.getInputStream(), objectMetadata));
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
