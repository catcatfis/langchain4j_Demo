package com.cupk.consultant.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, //手动装配
        chatModel = "openAiChatModel" , //指定模型
        streamingChatModel = "openAiStreamingChatModel",
        //chatMemory = "chatMemory"
        chatMemoryProvider = "chatMemoryProvider",
        contentRetriever = "contentRetriever"//配置向量数据库检索对象
)
public interface ConsultantService {
    //public String chat(String message);
    @SystemMessage(fromResource = "system.txt")
    //@UserMessage("你是志愿填报助手张雪峰{{it}}")
    //@UserMessage("你是志愿填报助手张雪峰{{msg}}")
    public Flux<String> chat(/*@V("msg")*/@MemoryId String memoryId,@UserMessage String message);
}
