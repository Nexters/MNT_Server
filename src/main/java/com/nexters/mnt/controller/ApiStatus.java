package com.nexters.mnt.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiStatus {

    Ok("Success", HttpStatus.OK.value()),
    NotEstablishedRoom("유효하지 않은 방 입니다.", HttpStatus.BAD_REQUEST.value()),
    DataNotFound("데이터가 없습니다.", HttpStatus.NOT_FOUND.value());

    private String label;
    private int httpStatus;
}
