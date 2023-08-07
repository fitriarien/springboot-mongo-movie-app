package com.fitriarien.movies.service;

import com.fitriarien.movies.domain.Movie;
import com.fitriarien.movies.dto.CreateMovieRequest;
import com.fitriarien.movies.dto.MovieResponse;
import com.fitriarien.movies.dto.UpdateMovieRequest;
import com.fitriarien.movies.repository.MovieRepository;
import com.fitriarien.movies.util.ValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    public static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ValidationHandler validationHandler;

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getAllMovies() {
        logger.info("Get all movies");

        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Data is empty");
        }

        return movies.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovie(String imdbId) {
        logger.info("Get a movie with id: {}", imdbId);

        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")
        );

        return toResponse(movie);
    }

    @Override
    @Transactional
    public MovieResponse createMovie(CreateMovieRequest request) {
        logger.info("Create a movie");
        validationHandler.validate(request);

        Movie movie = new Movie();
        movie.setImdbId(request.getImdbId());
        movie.setTitle(request.getTitle());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setTrailerLink(request.getTrailerLink());
        movie.setPoster(request.getPoster());
        movie.setGenres(request.getGenres());
        movie.setBackdrops(request.getBackdrops());
        movie.setReviewIds(null);

        movieRepository.save(movie);
        return toResponse(movie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(String imdbId, UpdateMovieRequest request) {
        logger.info("Update a movie with id: {}", imdbId);
        validationHandler.validate(request);

        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.")
        );

        if (request.getTitle() != null) {
            movie.setTitle(request.getTitle());
        }
        if (movie.getReleaseDate() != null) {
            movie.setReleaseDate(request.getReleaseDate());
        }
        if (movie.getTrailerLink() != null) {
            movie.setTrailerLink(request.getTrailerLink());
        }
        if (movie.getPoster() != null) {
            movie.setPoster(request.getPoster());
        }
        if (movie.getGenres() != null) {
            movie.setGenres(request.getGenres());
        }
        if (movie.getBackdrops() != null) {
            movie.setBackdrops(request.getBackdrops());
        }

        movieRepository.save(movie);
        return toResponse(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(String imdbId) {
        logger.info("Delete a movie with id: {}", imdbId);

        Movie movie = movieRepository.findMovieByImdbId(imdbId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")
        );

        movieRepository.delete(movie);
    }

    public MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .imdbId(movie.getImdbId())
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .trailerLink(movie.getTrailerLink())
                .genres(movie.getGenres())
                .poster(movie.getPoster())
                .backdrops(movie.getBackdrops())
                .reviewIds(movie.getReviewIds())
                .build();
    }
}
