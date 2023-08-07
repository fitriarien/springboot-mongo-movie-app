package com.fitriarien.movies.service;

import com.fitriarien.movies.dto.CreateMovieRequest;
import com.fitriarien.movies.dto.MovieResponse;
import com.fitriarien.movies.dto.UpdateMovieRequest;

import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();
    MovieResponse getMovie(String id);
    MovieResponse createMovie(CreateMovieRequest request);
    MovieResponse updateMovie(String imdbId, UpdateMovieRequest request);
    void deleteMovie(String imdbId);
}
