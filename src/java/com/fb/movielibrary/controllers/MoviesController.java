/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fb.movielibrary.controllers;

import com.fb.movielibrary.beans.MoviesBean;
import com.fb.movielibrary.entities.Movie;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Francesco
 */
@Named(value = "moviesController")
@RequestScoped
public class MoviesController
{
    @Inject
    MoviesBean moviesBean;
    
    /**
     * Creates a new instance of MoviesController
     */
    public MoviesController()
    {
    }
    
    public List<Movie> getMovies()
    {
        return moviesBean.getMovies();
    }
    
    public void delete(final Movie movie)
    {
        moviesBean.delete(movie.getId());
    }
}
