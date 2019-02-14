package com.authentication.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TungBoom
 *
 */
@Getter
@Setter
public class CellConfigExport {
    private int row;
    private int column;
    private int rowMerge;
    private int columnMerge;
    private String value;
    private String align;
    private String styleFormat;

    public CellConfigExport(
            int row,
            int column,
            int rowMerge,
            int columnMerge,
            String value,
            String align,
            String styleFormat
    ){
        this.row = row;
        this.column = column;
        this.rowMerge = rowMerge;
        this.columnMerge = columnMerge;
        this.value = value;
        this.align = align;
        this.styleFormat = styleFormat;
    }
}
