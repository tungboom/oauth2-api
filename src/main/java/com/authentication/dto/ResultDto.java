package com.authentication.dto;

import java.io.File;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author TungBoom
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ResultDto {
    String id;
    String key;
    String message;
    Timestamp systemDate;
    Object object;
    File file;
    String authToken;
}
