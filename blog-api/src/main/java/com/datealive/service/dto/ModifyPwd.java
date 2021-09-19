package com.datealive.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ModifyPwd
 * @Description: TODO
 * @author: zt
 * @date: 2021/2/20  13:52
 */
@Data
public class ModifyPwd implements Serializable {

    private String userName;

    private String oldPassword;

    private String newPassword;

}
