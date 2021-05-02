package com.twitter.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twitter.demo.model.Hashtag;



@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>{

	@Query(value = "SELECT  TOP 10 HASHTAG, COUNT(HASHTAG) FROM HASHTAG GROUP BY HASHTAG ORDER BY count(HASHTAG) DESC", nativeQuery = true)
    String[] findHashtagsMoreUsed();

}
