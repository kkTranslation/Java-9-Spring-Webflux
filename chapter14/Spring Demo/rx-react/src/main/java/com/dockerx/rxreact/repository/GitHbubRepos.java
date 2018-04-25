package com.dockerx.rxreact.repository;

import com.dockerx.rxreact.domain.Commit;
import com.dockerx.rxreact.domain.Repository;
import com.dockerx.rxreact.domain.SingleCommit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/22 21:49.
 */
@Slf4j
@org.springframework.stereotype.Repository
public class GitHbubRepos {
    private RestTemplate restTemplate;

    private static final String BASE = "https://api.github.com";
    static final String REPOS = BASE + "/users/%s/repos";
    static final String COMMITS = BASE + "/repos/%s/%s/commits?since=%s";
    static final String SINGLE_COMMIT = BASE + "/repos/%s/%s/commits/%s";

    @Autowired
    public GitHbubRepos(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Repository> getRepos(String user){
        log.info(format("Get repos by user(%s)", user));
        String format = format(REPOS, user);
        // The results may be found null, that is, it will lead the field become null and should be judged
        Repository[] forObject = restTemplate.getForObject(UriComponentsBuilder
                .fromUriString(format)
                .queryParam("direction","desc")
                .toUriString(), Repository[].class);
        return Arrays.asList(forObject != null ? forObject : new Repository[0]);
    }

    public List<Commit> getCommitsInWeek(String user, String repo) {
        String aWeekAgo = ZonedDateTime.now(ZoneOffset.UTC)
                                     .minusWeeks(1)
                                     .minusDays(1)
                                     .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00'Z'"));

        String url = format(COMMITS, user, repo, aWeekAgo);
        Commit[] commits = restTemplate.getForObject(url, Commit[].class);
        log.info(format("Get commits by repo(%s): %d commits are found", url, commits != null ? commits.length : 0));
        return  Arrays.asList(commits != null ? commits : new Commit[0]);

    }

    public SingleCommit getSingleCommit(String user, String repo, String sha) {
        log.info(format("Get a single commit by sha(%s)", sha));
        return restTemplate.getForObject(format(SINGLE_COMMIT, user, repo, sha), SingleCommit.class);
    }

    public SingleCommit getSingleCommitByUrl(String url) {
        log.info(format("Get a single commit by url(%s)", url));
        return restTemplate.getForObject(url, SingleCommit.class);
    }

    public static void main(String[] args) {
        String aWeekAgo = ZonedDateTime.now(ZoneOffset.UTC)
                                       .minusWeeks(1)
                                       .minusDays(1)
                                       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00'Z'"));

        System.out.println(aWeekAgo);
        String url = format(COMMITS, "aaa", "bbb", aWeekAgo);
        System.out.println(url);

    }
}
