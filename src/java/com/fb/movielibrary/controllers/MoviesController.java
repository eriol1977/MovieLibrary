/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fb.movielibrary.controllers;

import com.fb.movielibrary.beans.AuxiliaryBean;
import com.fb.movielibrary.entities.Movie;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Francesco
 */
@Named(value = "moviesController")
@ViewScoped
public class MoviesController implements Serializable
{
    @Inject
    private AuxiliaryBean bean;
    
    private LazyDataModel<Movie> lazyDataModel;
    
    private Movie selectedMovie;
    
    /**
     * Creates a new instance of MoviesController
     */
    public MoviesController()
    {
    }

    public LazyDataModel<Movie> getLazyDataModel()
    {
        if(lazyDataModel == null)
        {
            lazyDataModel = new QueryDataModel<>(bean, Movie.class);
        }
        return lazyDataModel;
    }

    public Movie getSelectedMovie()
    {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie)
    {
        this.selectedMovie = selectedMovie;
    }
    
    
}
