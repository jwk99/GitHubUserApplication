package io.jwk99.allegro.project.service;

import io.jwk99.allegro.project.model.Repo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GitHubService {

    public HttpEntity<Repo[]> getGitHubUserRepos(String githubusername, Integer pageNumber)
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("name", "stargazers_count");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String URL = "https://api.github.com/users/" + githubusername + "/repos?per_page=100&page="+pageNumber;
        HttpEntity<Repo[]> repos = restTemplate.exchange(URL, HttpMethod.GET, entity, Repo[].class);
        return repos;
    }

    public Map<String, Integer> getGitHubRepoLangs(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Map.class);
    }

}
