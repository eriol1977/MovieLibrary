/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.movielibrary.rest;

import com.fb.movielibrary.beans.MoviesBean;
import com.fb.movielibrary.entities.Movie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Francesco
 */
@Named(value = "restClient")
@SessionScoped
public class RottenTomatoesRESTClient implements Serializable
{

    private final static String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0";

    private final static String API_KEY = "e8c2p9rhjdrptpm9kyqndgha";

    private Client client;
    private WebTarget target;

    private String title;

    private Map<String, List<Movie>> moviesByTitle;

    private Comparator<Movie> reverseYearComparator;

    @Inject
    MoviesBean moviesBean;
    
    /**
     * Creates a new instance of ImdbRESTClient
     */
    public RottenTomatoesRESTClient()
    {
    }

    public List<Movie> getMovies()
    {
        if (this.title != null && !title.isEmpty()) {
            List<Movie> storedMovies = moviesByTitle.get(title);
            if (storedMovies == null) {
                storedMovies = target.path("movies.json")
                        .queryParam("apikey", API_KEY)
                        .queryParam("q", this.title)
                        .request().get(new GenericType<List<Movie>>()
                                {
                        });
                storedMovies = storedMovies.stream().sorted(reverseYearComparator).collect(Collectors.toList());
                moviesByTitle.put(this.title, storedMovies);
                //storedMovies.stream().forEach(m -> moviesBean.save(m));
            }
            return storedMovies;
        }
        return new ArrayList<>();
    }

    public boolean isFavorite(final Movie movie)
    {
        return moviesBean.isFavorite(movie);
    }
    
    @PostConstruct
    public void init()
    {
        client = ClientBuilder.newClient();
        client
                .register(MovieReader.class
                );
        target = client.target(BASE_URL);
        moviesByTitle = new HashMap<>();
        reverseYearComparator = Collections.reverseOrder(Comparator.comparing(Movie::getMovieYear));
    }

    public String save(final Movie movie)
    {
        moviesBean.save(movie);
        return "favorites";
    }
    
    @PreDestroy
    public void destroy()
    {
        client.close();
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
