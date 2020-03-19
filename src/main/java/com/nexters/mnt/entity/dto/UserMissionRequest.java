package com.nexters.mnt.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMissionRequest {
    private Long roomId;
    private String userId;
    private Long missionId;
    private String content;
    private Date date;
    private MultipartFile img;
}
