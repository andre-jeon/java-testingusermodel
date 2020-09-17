package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

// use the database

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        // mocks -> fake data
        // stubs -> fake methods
        // Java -> mocks
        MockitoAnnotations.initMocks(this);

        List<User> myList = userService.findAll();
        for (User u : myList) {
            System.out.println(u.getUserid() + " " + u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findUserById() {
        assertEquals("barnbarntest", userService.findUserById(11).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void b_findUserByIdNotFound() {
        assertEquals("", userService.findUserById(9000).getUsername());
    }

    @Test
    public void c_findByNameContaining() {
        assertEquals(2, userService.findByNameContaining("tt").size());
    }

    @Test
    public void d_findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void e_delete() {
        userService.delete(4);
        assertEquals(4, userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ee_deleteUserNotFound() {
        userService.delete(9000);
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void f_findByName() {
        assertEquals("barnbarntest", userService.findByName("barnbarntest").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void g_findByNameNotFound() {
        assertEquals("", userService.findByName("barn").getUsername());
    }

    @Test
    public void h_save() {
        // create an user to save
        String u4Name = "barnbarn2.0";
        User u4 = new User("barnbarn2.0",
                "password",
                "barnbarnTEST@school.lambda");
        Role role = new Role("turtle");
        role.setRoleid(3);
        u4.getRoles()
                .add(new UserRoles(u4, role));
        User addUser = userService.save(u4);
        assertNotNull(addUser);
        assertEquals(u4Name, addUser.getUsername());
    }

    @Test
    public void hh_saveput() {
        String u4Name = "barnbarn2.0";
        User u4 = new User("barnbarn2.0",
                "password",
                "barnbarnTEST@school.lambda");
        Role role = new Role("turtle");
        role.setRoleid(3);
        u4.getRoles()
                .add(new UserRoles(u4, role));
        User addUser = userService.save(u4);
        assertNotNull(addUser);
        assertEquals(u4Name, addUser.getUsername());
    }

    @Test
    public void i_update() {
        String updateName = "bugstest";
        User updateUser = new User();
        updateUser.setUsername("bugstest");
        updateUser.setUserid(13);
        updateUser.setPrimaryemail("bugstest@lambda.com");
        updateUser.setPassword("pwd1234$7&");
        updateUser.getUseremails().add(new Useremail(updateUser, "mrbunnytest@aol.com"));

        Role role = new Role("sandwich");
        role.setRoleid(2);
        updateUser.getRoles().add(new UserRoles(updateUser, role));

        User userDetails = userService.update(updateUser, 13);
        assertNotNull(userDetails);
        assertEquals(updateName, userService.findUserById(13).getUsername());
    }

    @Test
    public void j_deleteAll() {
    }
}