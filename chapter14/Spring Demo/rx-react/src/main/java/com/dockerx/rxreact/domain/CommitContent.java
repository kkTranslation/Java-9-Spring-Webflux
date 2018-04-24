package com.dockerx.rxreact.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/23 18:55.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitContent {
    private String message;
    private String url;
    private int comment_count;

}
