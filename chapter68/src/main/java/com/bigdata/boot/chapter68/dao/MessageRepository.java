package com.bigdata.boot.chapter68.dao;

import com.bigdata.boot.chapter68.domain.Message;

public interface MessageRepository {

	Iterable<Message> findAll();

	Message save(Message message);

	Message findMessage(Long id);

}