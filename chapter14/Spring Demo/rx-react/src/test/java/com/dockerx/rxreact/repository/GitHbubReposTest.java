package com.dockerx.rxreact.repository;

import com.dockerx.rxreact.domain.Commit;
import com.dockerx.rxreact.domain.CommittedFile;
import com.dockerx.rxreact.domain.Repository;
import com.dockerx.rxreact.domain.SingleCommit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/26 1:19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GitHbubReposTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GitHbubRepos gitHbubRepos;

    private static final String user = "kkTranslation";
    private static final String repo = "Java-9-Spring-Webflux";
    private static final String sha = "fee4488e595422aacbf02e67aa70e5e46781392c";
    @Test
    public void getRepos() {
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(String.format(GitHbubRepos.REPOS+"?direction=desc", user)))
                  .andRespond(withSuccess(
                          "[{\"name\":\"zheng\",\"pushed_at\":[2017,6,3,10,56,26]}," +
                                  "{\"name\":\"ui-for-docker\",\"pushed_at\":[2016,12,3,22,23,52]}," +
                                  "{\"name\":\"micro-service-practice\",\"pushed_at\":[2016,6,17,1,51,13]}," +
                                  "{\"name\":\"Java-9-Spring-Webflux\",\"pushed_at\":[2018,4,24,16,5,47]}," +
                                  "{\"name\":\"guava\",\"pushed_at\":[2017,5,7,15,17,46]}]",
                          /*pushed_at 可能会有变化，具体请自行修改*/
                          MediaType.APPLICATION_JSON)
                  );

        List<Repository> repos = gitHbubRepos.getRepos(user);

        assertThat(repos.size()).isEqualTo(5);
        assertThat(repos.get(0).getPushed().getHour()).isEqualTo(10);
        mockServer.verify();
    }

    @Test
    public void getCommitsInWeek() {
        String aWeekAgo = ZonedDateTime.now(ZoneOffset.UTC).minusWeeks(1).minusDays(1)
                                       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00'Z'"));
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(String.format(GitHbubRepos.COMMITS, user, repo, aWeekAgo)))
                  .andRespond(withSuccess( /*此处请自行添加结果测试，或者找自己的仓库设置*/
                                  "[{\"sha\":\"fee4488e595422aacbf02e67aa70e5e46781392c\",\"committer\":{\"login\":\"muyinchen\",\"html_url\":\"https://github.com/muyinchen\",\"organizations_url\":\"https://api.github.com/users/muyinchen/orgs\"},\"author\":{\"login\":\"muyinchen\",\"html_url\":\"https://github.com/muyinchen\",\"site_admin\":false,\"organizations_url\":\"https://api.github.com/users/muyinchen/orgs\"},\"commit\":{\"message\":\"Flowable 和Spring web的结合使用\",\"url\":\"https://api.github.com/repos/kkTranslation/Java-9-Spring-Webflux/git/commits/fee4488e595422aacbf02e67aa70e5e46781392c\",\"comment_count\":0}}]",
                          MediaType.APPLICATION_JSON));

        List<Commit> commits = gitHbubRepos.getCommitsInWeek(user, repo);

        assertThat(commits.size()).isEqualTo(1);
        assertThat(commits.get(0).getCommitter().getLogin()).isEqualTo("muyinchen");
        mockServer.verify();
    }

    @Test
    public void getSingleCommit() {
       /* MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(String.format(GitHbubRepos.SINGLE_COMMIT, user, repo, sha)))
                  .andRespond(withSuccess(
                          *//*内容有点多，就省略了,结果比较少的话，可以复制进来打开测试*//*
                          "",
                          MediaType.APPLICATION_JSON));*/

        SingleCommit singleCommit = gitHbubRepos.getSingleCommit(user, repo, sha);

        assertThat(singleCommit.getFiles())
                .extracting(CommittedFile::getFilename, CommittedFile::getChanges)
                .contains(tuple("chapter14/Spring Demo/rx-react/pom.xml", 124L));
    }


}