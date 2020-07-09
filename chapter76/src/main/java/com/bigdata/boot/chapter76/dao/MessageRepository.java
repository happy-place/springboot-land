package com.bigdata.boot.chapter76.dao;

import com.bigdata.boot.chapter76.domain.Message;

public interface MessageRepository {

	Iterable<Message> findAll();

	Message save(Message message);

	Message findMessage(Long id);

	void deleteMessage(Long id);

}