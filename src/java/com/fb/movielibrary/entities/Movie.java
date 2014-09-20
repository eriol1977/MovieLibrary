/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.movielibrary.entities;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Francesco
 */
@Entity
@Table(name = "MOVIE")
public class Movie implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String title;

    private Integer movieYear;

    private Integer runtime;

    private String synopsis;

    @Transient
    private String formattedRuntime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getMovieYear()
    {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear)
    {
        this.movieYear = movieYear;
    }

    public Integer getRuntime()
    {
        return runtime;
    }

    public void setRuntime(Integer runtime)
    {
        this.runtime = runtime;
        this.formattedRuntime = null;
    }

    public String getFormattedRuntime()
    {
        if (formattedRuntime == null) {
            this.formattedRuntime = String.format("%02d:%02d", TimeUnit.MINUTES.toHours(runtime), runtime
                    - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(runtime)));
        }
        return formattedRuntime;
    }

    public String getSynopsis()
    {
        if(synopsis == null || synopsis.isEmpty())
        {
            return "This movie has no description.";
        }
        return synopsis;
    }

    public void setSynopsis(String synopsis)
    {
        this.synopsis = synopsis;
        if (this.synopsis.length() > 3000) {
            this.synopsis = this.synopsis.substring(0, 3000);
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString()
    {
        return this.title;
    }

}
