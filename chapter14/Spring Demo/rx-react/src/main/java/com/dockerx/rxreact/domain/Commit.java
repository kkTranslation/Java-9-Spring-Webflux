package com.dockerx.rxreact.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/22 22:25.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    private String sha;
    @JsonProperty("committer")
    private Committer committer;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("commit")
    private CommitContent commitContent;
}
