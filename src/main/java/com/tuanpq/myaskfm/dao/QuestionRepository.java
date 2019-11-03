/**
 * @author admin
 * @date 28-10-2019
 */

package com.tuanpq.myaskfm.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tuanpq.myaskfm.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	public Slice<Question> findByUserAnswer_IdAndAnswerBodyNotNull(int id,
			Pageable pageable);

	public long countByUserAnswer_Id(int id);

	public long countByUserAnswer_Username(String username);

	public long countByUserAsk_Username(String username);
	
	public Slice<Question> findByUserAnswer_Username(String name,
			Pageable pageable);
	
	public Slice<Question> findByUserAsk_Username(String username,
			Pageable pageable);

	public Question findByIdAndUserAnswer_UsernameAndAnswerBodyNull(
			int questionId, String username);

	public long countByUserAnswer_IdAndAnswerBodyNotNull(int userId);

	public long countByUserAnswer_UsernameAndQuestionSeenFalse(String username);

	public long countByUserAsk_UsernameAndAnswerSeenFalseAndAndAnswerBodyNotNullAndUserAnswer_UsernameNot(
			String username, String username2);
	
	@Transactional
	@Modifying
	@Query("UPDATE Question q SET q.answerBody=:answer, q.answerDate=:date WHERE q.id = :id")
	public int updateAnswerForQuestion(@Param("answer") String answer, 
			  @Param("date") String date, @Param("id") int id);

}
