/**
 * @author admin
 * @date 28-10-2019
 */

package com.tuanpq.myaskfm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import com.tuanpq.myaskfm.dao.QuestionRepository;
import com.tuanpq.myaskfm.entity.Question;
import com.tuanpq.myaskfm.entity.User;
import com.tuanpq.myaskfm.service.QuestionService;
import com.tuanpq.myaskfm.utility.DateTimeHandler;

@Service
public class QuestionServiceImpl implements QuestionService {

	private static final Sort ANSWERDATE_TIME_SORT = Sort.by("answerDate").descending();
	private static final Sort ASKDATE_TIME_SORT = Sort.by("askDate").descending();
	
	@Value("${com.tuanpq.myaskfm.qperpage}")
	private int numQuestionPerPage;

	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public void save(Question question) {
		questionRepository.save(question);
	}

	@Override
	public void addNew(Question question, User userAnswer, User userAsk) {
		question.setUserAnswer(userAnswer);
		question.setUserAsk(userAsk);
		question.setAskDate(DateTimeHandler.convertToSaveToSQL(new Date()));

		save(question);
	}

	@Override
	public Slice<Question> findAnsweredQuestionsByUserId(int userId, int page) {
		Pageable pageable = PageRequest.of(page, numQuestionPerPage, ANSWERDATE_TIME_SORT);
		return questionRepository
				.findByUserAnswer_IdAndAnswerBodyNotNull(userId, pageable);
	}

	@Override
	public int countQuestionsReceivedOfUser(int userId) {
		return (int) questionRepository.countByUserAnswer_Id(userId);
	}
	
	@Override
	public int countQuestionsReceivedOfUser(String username) {
		return (int) questionRepository.countByUserAnswer_Username(username);
	}
	
	@Override
	public int countQuestionsSentOfUser(String username) {
		return (int) questionRepository.countByUserAsk_Username(username);
	}

	@Override
	public Slice<Question> findAllQuestionsReceivedByUser(String username, int page) {
		Pageable pageable = PageRequest.of(page, numQuestionPerPage, ASKDATE_TIME_SORT);
		return questionRepository.findByUserAnswer_Username(username, pageable);
	}
	
	@Override
	public Slice<Question> findAllQuestionsSentByUser(String username, int page) {
		Pageable pageable = PageRequest.of(page, numQuestionPerPage, ANSWERDATE_TIME_SORT);
		return questionRepository.findByUserAsk_Username(username, pageable);
	}

	@Override
	public boolean isUsernameOwnerUnansweredQuestion(String username, int questionId) {
		return questionRepository.findByIdAndUserAnswer_UsernameAndAnswerBodyNull(questionId, 
				username) != null;
	}
	
	@Override
	public Question findById(int id) {
		Optional<Question> result = questionRepository.findById(id);
		Question question = null;
		if (result.isPresent())
			question = result.get();
		else throw new RuntimeException("Did not find course id - " + id);
		return question;
	}

	@Override
	public void updateAnswerForQuestion(int questionId, String answer) {
		String date = DateTimeHandler.convertToSaveToSQL(new Date());
		questionRepository.updateAnswerForQuestion(answer, date, questionId);
	}

	@Override
	public void setAllOwnQuestionsSeen(List<Question> listQuestions) {
		for (Question question : listQuestions) {
			question.setQuestionSeen(true);
		}
		questionRepository.saveAll(listQuestions);
	}
	
	@Override
	public void setAllOwnAnswersSeen(List<Question> listAnswers) {
		for (Question question : listAnswers) {
			question.setAnswerSeen(true);
		}
		questionRepository.saveAll(listAnswers);
	}

	@Override
	public int countAnsweredQuestionsByUserId(int userId) {
		return (int) questionRepository.countByUserAnswer_IdAndAnswerBodyNotNull(userId);
	}

	@Override
	public List<Integer> getListIdUnseenQ(List<Question> listQuestions) {
		List<Integer> list = new ArrayList<>();
		for (Question question : listQuestions) 
			if (!question.isQuestionSeen()) list.add(question.getId());
		return list;
 	}
	
	@Override
	public List<Integer> getListIdUnseenA(List<Question> listAnswers) {
		List<Integer> list = new ArrayList<>();
		for (Question question : listAnswers) 
			if (!question.isAnswerSeen()) list.add(question.getId());
		return list;
	}

	@Override
	public int countUnseenQuestionsOfUser(String username) {
		return (int) questionRepository.countByUserAnswer_UsernameAndQuestionSeenFalse(username);
	}

	@Override
	public int countUnseenAnswersOfUser(String username) {
		return (int) questionRepository.countByUserAsk_UsernameAndAnswerSeenFalseAndAndAnswerBodyNotNullAndUserAnswer_UsernameNot(username, username);
	}

}
