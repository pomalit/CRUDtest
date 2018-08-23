package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<Message> findByUser(String username){
		String sql = "SELECT * FROM MESSAGES WHERE user = ? ORDER BY ID DESC";
		List<Message> messages  = jdbcTemplate.query(sql, new Object[] {username},
				new BeanPropertyRowMapper<Message>(Message.class));
		return messages;
	}
	
	public void addNewMessage(Message message) {
		String sql = "INSERT INTO MESSAGES(user, content) VALUES(?,?)";
		jdbcTemplate.update(sql, message.getUser(), message.getContent());
		
	}
	
	public void deleteMessageById(int id) {
		String sql = "DELETE FROM messages WHERE id = ?";
		jdbcTemplate.update(sql,id);
		
	}
	
	

}
