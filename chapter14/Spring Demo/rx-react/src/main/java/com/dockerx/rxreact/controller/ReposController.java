package com.dockerx.rxreact.controller;

import com.dockerx.rxreact.domain.Commit;
import com.dockerx.rxreact.domain.Repository;
import com.dockerx.rxreact.services.GitHubService;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/23 1:41.
 */
@RestController
@RequestMapping("/api/repos")
public class ReposController {
    private final GitHubService gitHubService;

    @Autowired
    public ReposController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }
    @ApiOperation(value="github用户所有仓库查询", notes="根据用户名称来查找其所有的仓库")
    @ApiImplicitParam(name = "user", value = "所要查询的用户名称", required = true)
    @GetMapping(value = "/0/{user}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flowable<Repository> list0(@PathVariable String user){
        return gitHubService.getRepos0(user).observeOn(Schedulers.io());
    }

    @ApiOperation(value="github用户所有仓库查询", notes="根据用户名称来查找其所有的仓库")
    @ApiImplicitParam(name = "user", value = "所要查询的用户名称", required = true)
    @GetMapping(value = "{user}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flowable<String> list(@PathVariable String user){
        return gitHubService.getRepos(user).observeOn(Schedulers.io());
    }
    @ApiOperation(value="github用户一周内有推送的仓库查询", notes="根据用户名称来查找其一周内有推送的仓库")
    @ApiImplicitParam(name = "user", value = "所要查询的用户名称", required = true)
    @GetMapping(value = "/inweek/{user}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flowable<String> getReposInWeek(@PathVariable String user){
        return gitHubService.getReposInWeek(user).observeOn(Schedulers.io());
    }
    @ApiOperation(value="github用户指定仓库一周内的提交信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "所要查询的用户名称", required = true),
            @ApiImplicitParam(name = "repo", value = "所要查询的用户的仓库名称", required = true)
    })
    @GetMapping(value = "/inweek/{user}/{repo}")
    public Flowable<Commit> getCommitsInWeek(@PathVariable String user, @PathVariable String repo){
        return gitHubService.getCommitsInWeek(user,repo).observeOn(Schedulers.io());
    }
}
