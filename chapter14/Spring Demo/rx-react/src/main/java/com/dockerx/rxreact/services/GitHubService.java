package com.dockerx.rxreact.services;

import com.dockerx.rxreact.domain.Commit;
import com.dockerx.rxreact.domain.CommittedFile;
import com.dockerx.rxreact.domain.Repository;
import io.reactivex.Flowable;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/22 23:31.
 */
public interface GitHubService {
    Flowable<Repository> getRepos0(String user);

    Flowable<String> getRepos(String user);

    Flowable<String> getReposInWeek(String user);

    Flowable<Commit> getCommitsInWeek(String user, String repo);

    Flowable<CommittedFile> getCommittedFiles(String user, String repo, String sha);

    Flowable<CommittedFile> getCommittedFilesByUrl(String url);

    Flowable<CommittedFile> getCommittedFilesByUser(String user);
}
