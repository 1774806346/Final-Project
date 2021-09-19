package com.datealive.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description: TODO
 * @author: zt
 * @date: 2021/1/31  12:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer user_id;

    private String username;

    private String password;
    private String nick_name;
    private String avatar;
    private String introduce;
    private String roles;
    private String email;
}
