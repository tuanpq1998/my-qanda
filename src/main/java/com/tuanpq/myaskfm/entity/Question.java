/**
 * @author admin
 * @date 25-10-2019
 */

package com.tuanpq.myaskfm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.tuanpq.myaskfm.utility.DateTimeHandler;
import com.tuanpq.myaskfm.validation.ValidationGroup.OnAsk;

@Entity
@Table(name="question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="question_content")
	@NotBlank(message="Question body is required!", groups = {OnAsk.class})
	private String questionBody;
	
	@Column(name="answer_content")
	private String answerBody;
	
	@Column(name="ask_date")
	private String askDate;
	
	@Column(name="answer_date")
	private String answerDate;
	
	@Column(name="question_seen")
	private boolean questionSeen;
	
	@Column(name="answer_seen")
	private boolean answerSeen;
	
	@Column(name="incognito_ask")
	private boolean incognitoAsk;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, 
			CascadeType.REFRESH})
	@JoinColumn(name="user_id_ask")
	private User userAsk;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, 
			CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id_answer")
	private User userAnswer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionBody() {
		return questionBody;
	}

	public void setQuestionBody(String questionBody) {
		this.questionBody = questionBody;
	}

	public String getAnswerBody() {
		return answerBody;
	}

	public void setAnswerBody(String answerBody) {
		this.answerBody = answerBody;
	}

	public String getAskDate() {
		return DateTimeHandler.convertToDisplayDateTime(askDate);
	}

	public void setAskDate(String askDate) {
		this.askDate = askDate;
	}

	public String getAnswerDate() {
		return DateTimeHandler.convertToDisplayDateTime(answerDate);
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}

	public boolean isQuestionSeen() {
		return questionSeen;
	}

	public void setQuestionSeen(boolean questionSeen) {
		this.questionSeen = questionSeen;
	}

	public boolean isAnswerSeen() {
		return answerSeen;
	}

	public void setAnswerSeen(boolean answerSeen) {
		this.answerSeen = answerSeen;
	}

	public User getUserAsk() {
		return userAsk;
	}

	public void setUserAsk(User userAsk) {
		this.userAsk = userAsk;
	}

	public User getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(User userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	public boolean isIncognitoAsk() {
		return incognitoAsk;
	}

	public void setIncognitoAsk(boolean incognitoAsk) {
		this.incognitoAsk = incognitoAsk;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", questionBody=" + questionBody
				+ ", answerBody=" + answerBody + ", askDate=" + askDate
				+ ", answerDate=" + answerDate + ", questionSeen="
				+ questionSeen + ", answerSeen=" + answerSeen 
				+ ", incognitoAsk=" + incognitoAsk;
				
	}

	public Question(int id, String questionBody, String answerBody,
			String askDate, String answerDate, boolean questionSeen,
			boolean answerSeen, boolean incognitoAsk) {
		super();
		this.id = id;
		this.questionBody = questionBody;
		this.answerBody = answerBody;
		this.askDate = askDate;
		this.answerDate = answerDate;
		this.questionSeen = questionSeen;
		this.answerSeen = answerSeen;
		this.incognitoAsk = incognitoAsk;
	}

	public Question() {
		super();
	}
	
}
