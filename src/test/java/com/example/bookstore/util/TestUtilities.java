package com.example.bookstore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
@AllArgsConstructor
public class TestUtilities {
    private final ObjectMapper objectMapper;

    public <T> T getObjectFromMvcResult(MvcResult mvcResult, Class<T> clazz) throws IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }
}
