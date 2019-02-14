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
public class TreeNode {
    private  String key;
    private  String title;
    private  Boolean isLeaf;
    private  Boolean disabled;
    private  Boolean disableCheckbox;
}
