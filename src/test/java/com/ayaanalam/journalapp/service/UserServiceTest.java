package com.ayaanalam.journalapp.service;

import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @Test
    public void testFindByUserName() {
        assertEquals(4,2+2);
        assertNotNull(userService.findByUserName("ayaan"));
    }


    @Disabled
    @ParameterizedTest
    @CsvSource(
            {
                    "1, 2, 3",
                    "2, 6, 10",
                    "4, 3, 7"
            }
    )
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }
}
