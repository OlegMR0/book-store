package com.example.bookstore.util;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@AllArgsConstructor
public class TestUtilities {
    private final ObjectMapper objectMapper;

    public <T> T getObjectFromMvcResult(MvcResult mvcResult, Class<T> clazz) throws IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }
}
