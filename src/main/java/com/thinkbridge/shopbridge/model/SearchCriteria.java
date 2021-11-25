package com.thinkbridge.shopbridge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchCriteria {

    private String column;
    private String operation;
    private String value;

}
