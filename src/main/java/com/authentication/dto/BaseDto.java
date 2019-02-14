package com.authentication.dto;

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
public class BaseDto {
    private int page;
    private int pageSize;
    private String sortName;
    private String sortType;
    private int totalRow;
    private int indexRow;
    private String langKey;
}
