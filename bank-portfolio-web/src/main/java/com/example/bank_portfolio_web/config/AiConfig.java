package com.example.bank_portfolio_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

@Configuration
public class AiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder, SyncMcpToolCallbackProvider mcpTools) {
        return builder.defaultTools(mcpTools).build();
    }
}