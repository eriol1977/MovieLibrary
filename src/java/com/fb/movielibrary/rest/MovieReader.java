/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.movielibrary.rest;

import com.fb.movielibrary.entities.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Francesco
 */
@Provider
@Consumes("text/javascript;charset=ISO-8859-1")
public class MovieReader implements MessageBodyReader<List<Movie>>
{

    @Override
    public List<Movie> readFrom(Class<List<Movie>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException
    {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        JsonParser parser = Json.createParser(in);
        String key;
        String value;
        int arrayLevel = 0;
        while (parser.hasNext()) {
            switch (parser.next()) {
                case START_ARRAY:
                    arrayLevel++;
                    break;
                case END_ARRAY:
                    arrayLevel--;
                    break;
                case KEY_NAME:
                    if (arrayLevel == 1) {
                        key = parser.getString();
                        switch (key) {
                            case "id":
                                movie = new Movie();
                                parser.next();
                                movie.setId(Long.valueOf(parser.getString()));
                                break;
                            case "title":
                                parser.next();
                                movie.setTitle(parser.getString());
                                break;
                            case "year":
                                parser.next();
                                value = parser.getString();
                                if (!value.isEmpty()) {
                                    movie.setMovieYear(Integer.valueOf(value));
                                } else {
                                    movie.setMovieYear(0);
                                }
                                break;
                            case "runtime":
                                parser.next();
                                value = parser.getString();
                                if (!value.isEmpty()) {
                                    movie.setRuntime(Integer.valueOf(value));
                                } else {
                                    movie.setRuntime(0);
                                }
                                movies.add(movie);
                                break;
                        }
                    }
                default:
                    break;
            }
        }
        return movies;
    }

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt)
    {
        return true;
    }
}
