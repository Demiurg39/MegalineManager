package org.database.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.database.manager.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserManagerTest {

    private UserManager userManager;
    private User testUser;

    @BeforeEach
    public void setUp() {
        userManager = new UserManager();
        testUser = new User("Test User", "Test Address", "test@example.com");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Удаляем созданного пользователя после выполнения каждого теста
        userManager.delete(testUser);
    }

    @Test
    public void testCreateUser() throws SQLException {
        userManager.create(testUser);
        ResultSet rs = userManager.get(testUser);
        assertTrue(rs.next());
        assertEquals(testUser.getName(), rs.getString("userName"));
        assertEquals(testUser.getAddress(), rs.getString("userAddress"));
        assertEquals(testUser.getEmail(), rs.getString("userEmail"));
    }

    @Test
    public void testUpdateUserName() throws SQLException {
        userManager.create(testUser);
        User updatedUser = new User(testUser.getId(), "Updated Name", testUser.getAddress(), testUser.getEmail());
        userManager.update(updatedUser, "userName", "Updated Name");
        ResultSet rs = userManager.get(testUser);
        assertTrue(rs.next());
        assertEquals("Updated Name", rs.getString("userName"));
    }
}
