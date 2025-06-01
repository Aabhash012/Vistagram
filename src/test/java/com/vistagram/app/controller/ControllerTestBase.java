package com.vistagram.app.controller;


import com.vistagram.app.exception.GlobalExceptionHandler;
import com.vistagram.app.service.Interface.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public abstract class ControllerTestBase {
    protected MockMvc mockMvc;

    @Mock
    protected PostService postService;

    @Mock
    protected LikeService likeService;

    @Mock
    protected ShareService shareService;

    @Mock
    protected UserService userService;

    @Mock
    protected FileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    protected abstract Object getController();
}
