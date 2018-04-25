package com.dockerx.rxreact.services;

import com.dockerx.rxreact.domain.Commit;
import com.dockerx.rxreact.domain.CommittedFile;
import com.dockerx.rxreact.domain.Repository;
import com.dockerx.rxreact.domain.SingleCommit;
import com.dockerx.rxreact.repository.GitHbubRepos;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.functions.BiConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/22 23:32.
 */
@Slf4j
@Service
public class GitHubServiceImpl implements GitHubService {

    private final GitHbubRepos gitHbubRepos;

    @Autowired
    public GitHubServiceImpl(GitHbubRepos gitHbubRepos) {
        this.gitHbubRepos = gitHbubRepos;
    }
    @Override
    public Flowable<Repository> getRepos0(String user) {
        Callable<Iterator<Repository>> initialState =
                gitHbubRepos.getRepos(user)
                            .stream()::iterator;
        BiConsumer<Iterator<Repository>, Emitter<Repository>> generator =
                (iterator, emitter) -> {
                    if (iterator.hasNext()) {
                        emitter.onNext(iterator.next());
                    } else {
                        emitter.onComplete();
                    }
                };
        return Flowable.generate(initialState, generator);
    }
    @Override
    public Flowable<String> getRepos(String user) {
        Callable<Iterator<String>> initialState =
                () -> gitHbubRepos.getRepos(user)
                                  .stream()
                                  .map(Repository::getName).iterator();
        BiConsumer<Iterator<String>, Emitter<String>> generator =
                (iterator, emitter) -> {
                    if (iterator.hasNext()) {
                        emitter.onNext(iterator.next() + "  ");
                    } else {
                        emitter.onComplete();
                    }
                };
        return Flowable.generate(initialState, generator);
    }


    @Override
    public Flowable<String> getReposInWeek(String user) {
        return Flowable.create(emitter -> {
            gitHbubRepos.getRepos(user).stream()
                        .filter(repo -> repo.getPushed().isAfter(LocalDateTime.now().minusWeeks(1)))
                        .map(item -> item.getName() + " ")
                        .forEach(emitter::onNext);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Commit> getCommitsInWeek(String user, String repo) {
        return Flowable.create(emitter -> {
            gitHbubRepos.getCommitsInWeek(user, repo)
                        .forEach(emitter::onNext);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<CommittedFile> getCommittedFiles(String user, String repo, String sha) {
        return Flowable.create(emitter -> {
            gitHbubRepos.getSingleCommit(user, repo, sha).getFiles()
                        .forEach(emitter::onNext);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<CommittedFile> getCommittedFilesByUrl(String url) {
        return Flowable.create(emitter -> {
            SingleCommit commit = gitHbubRepos.getSingleCommitByUrl(url);
            log.info(commit.toString());
            commit.getFiles().forEach(files -> {
                log.info(files.toString());
                emitter.onNext(files);
            });
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<CommittedFile> getCommittedFilesByUser(String user) {
        return getReposInWeek(user)
                .flatMap(repo -> Flowable.combineLatest(
                        Flowable.just(repo),
                        getCommitsInWeek(user, repo),
                        Pair::of))
                .flatMap(pair -> getCommittedFiles(user, pair.getLeft(), pair.getRight().getSha()));
    }
}
