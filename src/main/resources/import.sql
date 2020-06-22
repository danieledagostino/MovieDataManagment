insert into director(id, name) values(1, 'Steven Spielberg');
insert into director(id, name) values(2, 'Martin Scorzese');
insert into director(id, name) values(3, 'Quentin Tarantino');

insert into movie(id, name, director_id) values(1, 'Duel', 1);
insert into movie(id, name, director_id) values(2, 'Jaws', 1);
insert into movie(id, name, director_id) values(3, 'Close Encounters of the Third Kind', 1);
insert into movie(id, name, director_id) values(4, 'Reservoir Dogs', 3);
insert into movie(id, name, director_id) values(5, 'Pulp Fiction', 3);
insert into movie(id, name, director_id) values(6, 'Kill Bill: Volume 1', 3);
insert into movie(id, name, director_id) values(7, 'Taxi Driver', 2);
insert into movie(id, name, director_id) values(8, 'New York, New York', 2);
insert into movie(id, name, director_id) values(9, 'Raging Bull', 2);
insert into movie(id, name, director_id) values(10, 'Indiana Jones and the Last Crusade', 1);

insert into rating(id, score, insert_date, movie_id) values(1, 2.0, current_timestamp(), 1);
insert into rating(id, score, insert_date, movie_id) values(2, 5.5, current_timestamp(), 1);
insert into rating(id, score, insert_date, movie_id) values(3, 4.5, current_timestamp(), 1);
insert into rating(id, score, insert_date, movie_id) values(4, 2.0, current_timestamp(), 5);
insert into rating(id, score, insert_date, movie_id) values(5, 2.0, current_timestamp(), 9);
insert into rating(id, score, insert_date, movie_id) values(6, 5.5, current_timestamp(), 9);

insert into movie_ratings(movie_id, ratings_id) values(1, 1);
insert into movie_ratings(movie_id, ratings_id) values(1, 2);
insert into movie_ratings(movie_id, ratings_id) values(1, 3);
insert into movie_ratings(movie_id, ratings_id) values(5, 4);
insert into movie_ratings(movie_id, ratings_id) values(9, 5);
insert into movie_ratings(movie_id, ratings_id) values(9, 6);