package io.jwk99.allegro.project.controller;

import io.jwk99.allegro.project.model.Repo;
import io.jwk99.allegro.project.service.GitHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/githubtest")
public class GitHubUserController {

    private final GitHubService gHService;

    public GitHubUserController(GitHubService gHService) {
        this.gHService = gHService;
    }

    Logger alert = LoggerFactory.getLogger(GitHubUserController.class);

    //Repos and stars per repo

    @GetMapping("/{githubusername}/repos")
    public @ResponseBody ArrayList<Object> getRepos(@PathVariable String githubusername)
    {
        Integer starsSum = 0;
        Integer pageNumber = 1;
        Integer endPage = 0;


        ArrayList<Object> output = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> stargazersNumber = new ArrayList<>();

        //repos
        do {

            HttpEntity<Repo[]> repos = gHService.getGitHubUserRepos(githubusername, pageNumber);

            Repo[] arr = repos.getBody();
            for (Repo repo : arr) {
                names.add(repo.getName());
            }

            endPage=arr.length;

            pageNumber++;

        }while(endPage==100);

        output.add(names);

        pageNumber = 1;
        endPage = 0;

        //stars
        do {

            HttpEntity<Repo[]> repos = gHService.getGitHubUserRepos(githubusername, pageNumber);

            Repo[] arr = repos.getBody();
            for (Repo repo : arr) {
                stargazersNumber.add(repo.getStargazers_count());
            }

            endPage=arr.length;

            pageNumber++;

        }while(endPage==100);

        output.add(stargazersNumber);
        return output;
    }


    //sum stars per user
    @GetMapping("/{githubusername}/totalstars")
    public @ResponseBody Integer starSum(@PathVariable String githubusername)
    {
        Integer starsSum = 0;
        Integer pageNumber = 1;
        Integer endPage = 0;

        ArrayList<Integer> stargazers_Counts = new ArrayList<>();

        do {

            HttpEntity<Repo[]> repos = gHService.getGitHubUserRepos(githubusername, pageNumber);

            Repo[] arr = repos.getBody();
            for (Repo repo : arr) {
                stargazers_Counts.add(repo.getStargazers_count());
            }

            endPage=arr.length;



            pageNumber++;

        }while(endPage==100);

        for(int i = 0; i < stargazers_Counts.size(); i++)
        {
            starsSum+=stargazers_Counts.get(i);
        }

        alert.info("Summary number of stars: "+starsSum);

        return starsSum;
    }

    //languages and bytes
    @GetMapping("/{githubusername}/languages")
    @ResponseBody Map<String, Integer> languagesAndBytes(@PathVariable String githubusername)
    {
        Map<String, Integer> output=new HashMap<>();

        Integer pageNumber = 1;
        Integer endPage = 0;

        do{

            HttpEntity<Repo[]> repos = gHService.getGitHubUserRepos(githubusername, pageNumber);
            Repo[] arr = repos.getBody();

            for (Repo repo : arr) {
                Map<String, Integer> repoLangs = gHService.getGitHubRepoLangs(repo.getLanguages_url());
                for ( Map.Entry<String, Integer> lang : repoLangs.entrySet() ) {
                    String langName = lang.getKey();
                    if ( output.containsKey(output) ) {
                        output.put(langName, output.get(langName) + lang.getValue());
                    }
                    else {
                        output.put(langName, lang.getValue());
                    }
                }
            }

            endPage=arr.length;
            pageNumber++;

        }while(endPage==100);

        return output;
    }






}
