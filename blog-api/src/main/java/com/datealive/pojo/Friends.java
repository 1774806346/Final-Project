package com.datealive.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Friends
 * @Description: TODO
 * @author: zt
 * @date: 2021/1/31  20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends {

    private Integer link_id;
    private String link_name;
    private String link_url;
    private String link_info;
    private String avatar;
    private Integer status;
}
