package com.vistagram.app.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModelMapperConfigTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void modelMapperBean_ShouldExist() {
        assertNotNull(modelMapper);
    }

    @Test
    void modelMapper_ShouldHaveStrictMatchingStrategy() {
        assertEquals(
                org.modelmapper.convention.MatchingStrategies.STRICT,
                modelMapper.getConfiguration().getMatchingStrategy()
        );
    }
}
