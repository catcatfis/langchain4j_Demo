package com.cupk.consultant.config;

import com.cupk.consultant.aiservice.ConsultantService;
import com.cupk.consultant.respository.RedisChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.ContentHandler;
import java.util.List;

@Configuration
public class CommonConfig {
    @Autowired
    private OpenAiChatModel model;

    @Autowired
    private RedisChatMemoryStore redisChatMemoryStore;

    /*@Bean
    public ConsultantService consultantService() {
        ConsultantService consultantService = AiServices.builder(ConsultantService.class)
                .chatModel(model)
                .build();
        return consultantService;
    }
    */
    @Bean
    public ChatMemory chatMemory() {
        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
        return memory;
    }
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
    }
    //构建向量数据库
    @Bean
    public EmbeddingStore store() {
        //List<Document> documents =ClassPathDocumentLoader.loadDocuments("content");
        //List<Document> documents  = FileSystemDocumentLoader.loadDocuments("D:\\WorkSpace2025\\consultant\\src\\main\\resources\\content");
        List<Document> documents =ClassPathDocumentLoader.loadDocuments("content",new ApachePdfBoxDocumentParser());
        //构建向量数据库操作对象
        InMemoryEmbeddingStore store=new InMemoryEmbeddingStore();

        //构建文本分割器对象
        DocumentSplitter ds = DocumentSplitters.recursive(500, 100);
        //构建一个embeddingstoreIngestor对象，将文本数据切割向量化存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .documentSplitter(ds)
                .build();
        ingestor.ingest(documents);
        return store;
    }

    //构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .minScore(0.5)
                .maxResults(3)
                .build();
    }
}
