/**
 * @author admin
 * @date 28-10-2019
 */

package com.tuanpq.myaskfm.service;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.tuanpq.myaskfm.entity.Question;
import com.tuanpq.myaskfm.entity.User;

public interface QuestionService {
	
	public Question findById(int id);

	public void save(Question question);
	
	public void addNew(Question question, User userAnswer, User userAsk);

	public Slice<Question> findAnsweredQuestionsByUserId(int userId, int page);
	
	public int countQuestionsReceivedOfUser(int userId);

	public int countQuestionsReceivedOfUser(String username);
	
	public int countQuestionsSentOfUser(String username);
	
	public Slice<Question> findAllQuestionsReceivedByUser(String username, int page);

	public Slice<Question> findAllQuestionsSentByUser(String name, int page);
	
	public boolean isUsernameOwnerUnansweredQuestion(String username, int questionId);

	public void updateAnswerForQuestion(int questionId, String answer);

	public void setAllOwnQuestionsSeen(List<Question> list);

	public void setAllOwnAnswersSeen(List<Question> listAnswers);

	public int countAnsweredQuestionsByUserId(int userId);

	public List<Integer> getListIdUnseenQ(List<Question> list);

	public List<Integer> getListIdUnseenA(List<Question> listAnswers);

	public int countUnseenQuestionsOfUser(String username);

	public int countUnseenAnswersOfUser(String username);
	
}
