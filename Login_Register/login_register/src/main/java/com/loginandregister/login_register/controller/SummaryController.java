package com.loginandregister.login_register.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/summary")
public class SummaryController {
    

    @PostMapping
    public String summarizeContent(String content) {
    String apiKey = "";
    String endpoint = "https://api.openai.com/v1/engines/davinci/completions";

    // Tạo payload
    String payload = """
        {
          "model": "text-davinci-002",
          "messages": [
            {"role": "system", "content": "Bạn là một AI hỗ trợ tóm tắt nội dung."},
            {"role": "user", "content": "%s"}
          ]
        }
    """.formatted(content);

    // Cấu hình header
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    // Gửi yêu cầu
    HttpEntity<String> request = new HttpEntity<>(payload, headers);
    RestTemplate restTemplate = new RestTemplate();

    try {
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);
        return response.getBody(); // Trả về nội dung tóm tắt
    } catch (Exception e) {
        e.printStackTrace();
        return "Không thể tóm tắt nội dung: " + e.getMessage();
    }
}
}
