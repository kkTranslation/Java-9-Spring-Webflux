package com.dockerx.rxreact.services;

import com.dockerx.rxreact.domain.*;
import com.dockerx.rxreact.repository.GitHbubRepos;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/26 18:02.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class GitHubServiceImplTest {

    @Mock
    private GitHbubRepos gitHbubRepos;
    private GitHubService gitHubService;

    @Before
    public void setup() {
        initMocks(this);
        gitHubService = new GitHubServiceImpl(gitHbubRepos);
        when(gitHbubRepos.getRepos(anyString())).thenAnswer(m -> getReposForTest());

        when(gitHbubRepos.getCommitsInWeek(anyString(), anyString())).thenAnswer(m -> getCommitsInWeekForTest());
        when(gitHbubRepos.getSingleCommit(anyString(),
                anyString(),
                anyString())).thenAnswer(m -> getSingleCommitForTest());
        when(gitHbubRepos.getSingleCommitByUrl(anyString())).thenAnswer(m -> getSingleCommitForTest());
    }

    private List<Repository> getReposForTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        return Arrays.asList(
                new Repository("repo1", LocalDateTime.now().minusMonths(1)),
                new Repository("repo2", LocalDateTime.now()),
                new Repository("repo3", LocalDateTime.now().minusDays(3))
        );
    }

    @Test
    public void getRepos() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();


        gitHubService.getRepos("test-user")
                     .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.values())
                .as("getRepos returns all user code repository ")
                .containsExactly("repo1  ", "repo2  ", "repo3  ");
    }

    @Test
    public void getReposInWeek() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        gitHubService.getReposInWeek("test-user")
                     .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.values())
                .as("getReposInWeek returns updated repos within one week")
                .containsExactly("repo2 ", "repo3 ");
    }

    @Test
    public void getCommitsInWeek() {
        TestSubscriber<Commit> testSubscriber = new TestSubscriber<>();

        gitHubService.getCommitsInWeek("test-user", "test-repo")
                     .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.values())
                .as("getCommitsInWeek returns commits updated by `user` within one week")
                .extracting(Commit::getSha)
                .containsExactly("sha1");
    }


    @Test
    public void getCommittedFilesByUser() {
        TestSubscriber<CommittedFile> testSubscriber = new TestSubscriber<>();

        gitHubService.getCommittedFilesByUser("test-user")
                     .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.values())
                .as("getCommittedFilesByUser returns CommittedFiles by `user`")
                .extracting(CommittedFile::getFilename)
                .containsOnly("filename1", "filename1", "filename2", "filename2");
    }

    private List<Commit> getCommitsInWeekForTest() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ignore) {
        }
        return Arrays.asList(
                Commit.builder()
                      .sha("sha1")
                      .committer(new Committer("test-user",
                              "https://github.com/muyinchen",
                              "https://api.github.com/users/muyinchen/orgs"))
                      .author(new Author("url2",
                              "https://github.com/muyinchen",
                              true,
                              "https://api.github.com/users/muyinchen/orgs"))
                      .build(),
                Commit.builder()
                      .sha("sha2")
                      .committer(new Committer("no-test-user",
                              "https://github.com/muyinchen/no-test-user",
                              "https://api.github.com/users/muyinchen/orgs/no-test-user"))
                      .author(new Author("url2",
                              "https://github.com/muyinchen/no-test-user",
                              true,
                              "https://api.github.com/users/muyinchen/orgs/no-test-user"))
                      .build()
        );
    }

    private SingleCommit getSingleCommitForTest() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ignore) {
        }
        return new SingleCommit(
                Arrays.asList(
                        new CommittedFile("filename1", 10),
                        new CommittedFile("filename2", 20)
                )
        );
    }

    @Test
    public void testTestSubscriber() {

        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        //In order to emit "1", "2", "3"
        Flowable.just("1", "2", "3").subscribe(testSubscriber);
        //Assert whether values are equal
        testSubscriber.assertValues("1", "2", "3");
        //Assert value does not exist
        testSubscriber.assertNever("4");
        //Is the number of asserted values equal?
        testSubscriber.assertValueCount(3);
        //Assertion terminated
        testSubscriber.assertTerminated();
    }
}