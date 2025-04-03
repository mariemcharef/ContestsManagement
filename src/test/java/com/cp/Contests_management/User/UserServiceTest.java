package com.cp.Contests_management.User;

import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Participant.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private ParticipantService participantService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  ParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void should_save_user() {
        User user = new User();
        user.setPassword("password");
        user.setName("name");
        user.setEmail("email@email.com");
        User saveduser = userRepository.save(user);
        assertNotNull(saveduser);
    }
}