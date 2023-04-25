package com.server.notesapp;

import com.server.notesapp.model.Note;
import com.server.notesapp.model.Role;
import com.server.notesapp.model.User;
import com.server.notesapp.service.ListService;
import com.server.notesapp.service.NoteService;
import com.server.notesapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NotesApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private ListService listService;

	@Autowired
	private NoteService noteService;

	@Test
	void addNewUserTest() {
		User user = new User();
		user.setEmail("hola@gmail.com");
		user.setPwd("123456");
		user.setConfirmPwd("123456");
		user.setFullName("hi");
		user.setRole(Role.USER);
		userService.saveUser(user);
	}

	@Test
	void getUsersTest(){
		List<User> users = userService.getAllUsers();
		System.out.println(users);
	}

	@Test
	void addListTest(){
		com.server.notesapp.model.List list = new com.server.notesapp.model.List();
		list.setOwner(userService.getAllUsers().get(0));
		list.setTitle("Songs");
		listService.saveList(list);
	}

	@Test
	void getListsTest(){
		List<com.server.notesapp.model.List> lists = listService.getLists(1);
		System.out.println(lists);
	}

	@Test
	void addNoteTest(){
//		Note note = new Note();
//		note.setTitle("lalala");
//		note.setDescription("By someone");
//		noteService.saveNote(note, note.getOwnerId());
	}

	@Test
	void getNotesTest(){
		List<Note> notes = noteService.getNotes(3);
		System.out.println(notes);
	}
}