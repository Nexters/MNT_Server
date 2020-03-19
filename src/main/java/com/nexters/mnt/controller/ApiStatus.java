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

    Fail("Fail", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    Ok("Success", HttpStatus.OK.value()),
    NotEstablishedRoom("유효하지 않은 방 입니다.", HttpStatus.BAD_REQUEST.value()),
    FULL("방의 인원이 최대 입니다.", HttpStatus.FORBIDDEN.value()),
    DataNotFound("데이터가 없습니다.", HttpStatus.NOT_FOUND.value()),
    DuplicateRoom("이미 참여하는 방 입니다.", HttpStatus.BAD_REQUEST.value()),
    NotEnoughToStart("방 인원이 충분하지 않습니다", HttpStatus.BAD_REQUEST.value());
    private String label;
    private int httpStatus;
}
