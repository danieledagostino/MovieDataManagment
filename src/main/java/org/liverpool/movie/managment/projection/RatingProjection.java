package org.liverpool.movie.managment.projection;

import org.liverpool.movie.managment.model.Movie;
import org.springframework.beans.factory.annotation.Value;

public interface RatingProjection {
	
	//@Value("#{target.movie.id}")
	Movie getMovie();
	
}
