package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userOtmane = new User("Otmane", "Arsalane", "arsalane.otmane@gmail.com", "admin");
        userOtmane.addRole(roleAdmin);

        User savedUser = repo.save(userOtmane);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateNewUserWithTwoRole(){
        User userRavi = new User("Ravi", "Kumar", "ravi@gmail.com", "ravi2020");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = repo.save(userRavi);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userOtmane = repo.findById(1).get();
        System.out.println(userOtmane);
        assertThat(userOtmane).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userOtmane = repo.findById(1).get();
        userOtmane.setEnabled(true);

        repo.save(userOtmane);
    }

    @Test
    public void testUpdateUserRoles(){
        User userRavi = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userRavi.getRoles().remove(roleEditor);
        userRavi.addRole(roleSalesperson);

        repo.save(userRavi);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "arsalane.otmane@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }
    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);

    }

    @Test
    public void testDisableUser(){
        Integer id = 1;
        repo.updateEnabledStatus(id, false);
    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 1;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(pageable);

        List<User> listUsers =  page.getContent();

        listUsers.forEach(user-> System.out.println(user));

        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

}
