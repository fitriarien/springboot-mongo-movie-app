package com.fitriarien.movies.controller;

import com.fitriarien.movies.domain.Movie;
import com.fitriarien.movies.dto.CreateMovieRequest;
import com.fitriarien.movies.dto.MovieResponse;
import com.fitriarien.movies.dto.UpdateMovieRequest;
import com.fitriarien.movies.dto.WebResponse;
import com.fitriarien.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> movies = movieService.getAllMovies();
        return WebResponse.<List<MovieResponse>>builder().data(movies).build();
    }

    @GetMapping(
            path = "/{movieId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MovieResponse> get(@PathVariable("movieId") String imdbId) {
        MovieResponse movieResponse = movieService.getMovie(imdbId);
        return WebResponse.<MovieResponse>builder().data(movieResponse).build();
    }

    @PostMapping(
            path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MovieResponse> create(@RequestBody CreateMovieRequest request) {
        MovieResponse movieResponse = movieService.createMovie(request);
        return WebResponse.<MovieResponse>builder().data(movieResponse).build();
    }

    @PutMapping(
            path = "/{movieId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MovieResponse> update(@PathVariable("movieId") String imdbId,
                                             @RequestBody UpdateMovieRequest request) {
        MovieResponse movieResponse = movieService.updateMovie(imdbId, request);
        return WebResponse.<MovieResponse>builder().data(movieResponse).build();
    }

    @DeleteMapping(
            path = "/{movieId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("movieId") String imdbId) {
        movieService.deleteMovie(imdbId);
        return WebResponse.<String>builder().data(null).build();
    }
}
