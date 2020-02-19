package com.nexters.mnt.entity;

import com.nexters.mnt.controller.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T>{
    private T data;
    private ApiStatus apiStatus;
}
