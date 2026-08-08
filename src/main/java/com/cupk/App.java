package com.cupk;

import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        OpenAiChatModel model  =OpenAiChatModel.builder()
                .baseUrl("https://llm-abae8dt66wywisdn.cn-beijing.maas.aliyuncs.com/compatible-mode/v1")
                .apiKey(System.getenv("QW_API_KEY"))
                .modelName("qwen-plus")
                .logRequests(true)
                .logResponses(true)
                .build();

        String response = model.chat("Hello, how are you?");
        System.out.println(response);
    }
}
