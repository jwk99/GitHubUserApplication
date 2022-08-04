package io.jwk99.allegro.project.model;

import org.springframework.stereotype.Component;

@Component
public class Repo {

    String name;

    int stargazers_count;

    String languages_url;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getLanguages_url() {
        return languages_url;
    }

    public void setLanguages_url(String languages_url) {
        this.languages_url = languages_url;
    }
}
