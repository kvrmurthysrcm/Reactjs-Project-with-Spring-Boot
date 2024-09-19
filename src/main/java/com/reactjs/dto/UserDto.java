package com.reactjs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {

    private Integer idusers;
    private String userId;

    private String password;

    private String firstName;
    private String lastName;

    private Boolean active;
    private String userscol;

}
