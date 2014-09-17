/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.movielibrary.rest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Francesco
 */
@Named(value = "restClient")
@RequestScoped
public class RottenTomatoesRESTClient
{

    private final static String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0";

    private final static String API_KEY = "e8c2p9rhjdrptpm9kyqndgha";

    private Client client;
    private WebTarget target;

    private String title;

    /**
     * Creates a new instance of ImdbRESTClient
     */
    public RottenTomatoesRESTClient()
    {
    }

    public String getMovie()
    {
        if (this.title != null && !title.isEmpty()) {
            Response response = target.path("movies.json")
                    .queryParam("apikey", API_KEY)
                    .queryParam("q", this.title)
                    .request().get();
            return response.readEntity(String.class);
        }
        return "";
    }

    public void refresh()
    {
        
    }
    
    @PostConstruct
    public void init()
    {
        client = ClientBuilder.newClient();
        target = client.target(BASE_URL);
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
