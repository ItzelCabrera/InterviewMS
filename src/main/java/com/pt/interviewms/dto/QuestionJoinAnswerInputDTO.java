package com.pt.interviewms.dto;

    public class QuestionJoinAnswerInputDTO {
        private Long questionId;
        private String bodyQuestion;

        private String answerUser;
        private String answerLLM;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getBodyQuestion() {
        return bodyQuestion;
    }

    public void setBodyQuestion(String bodyQuestion) {
        this.bodyQuestion = bodyQuestion;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

        public String getAnswerLLM() {
            return answerLLM;
        }

        public void setAnswerLLM(String answerLLM) {
            this.answerLLM = answerLLM;
        }
    }
