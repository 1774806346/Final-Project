package com.datealive.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: LoginDto
 * @Description: TODO
 * @author: zt
 * @date: 2021/1/31  19:09
 */
@Data
public class LoginDto implements Serializable {

    private String username;

    private String password;

}
