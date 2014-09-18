/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fb.movielibrary.beans;

import com.fb.movielibrary.entities.Movie;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Francesco
 */
@Stateless
public class MoviesBean
{
    @PersistenceContext
    private EntityManager em;
    
    public List<Movie> getMovies()
    {
        return em.createQuery("select m from Movie m order by m.title", Movie.class).getResultList();
    }

    public Movie save(final Movie movie)
    {
        em.persist(movie);
        em.flush();
        em.refresh(movie);
        return movie;
    }
    
    public Movie find(final Long id)
    {
        return em.find(Movie.class, id);
    }
    
    public void delete(final Long id)
    {
        Movie movie = em.getReference(Movie.class, id);
        em.remove(movie);
    }
    
    public boolean isFavorite(final Movie movie)
    {
        return find(movie.getId()) != null;
    }
    
}
