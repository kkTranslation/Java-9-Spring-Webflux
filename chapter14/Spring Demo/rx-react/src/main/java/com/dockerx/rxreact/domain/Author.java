package com.dockerx.rxreact.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/22 22:29.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private String login;
    private String html_url;
    private boolean site_admin;
    private String organizations_url;
}
