/**
 * @author admin
 * @date 28-10-2019
 */

package com.tuanpq.myaskfm.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
