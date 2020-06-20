package org.liverpool.movie.managment.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/test-sql/insert.sql")
@Transactional
public class MovieRepositoryTest {
	
	@Autowired
	MovieRepository movieRepository;

    @Test
    public void testHello() throws Exception {
    }
}
