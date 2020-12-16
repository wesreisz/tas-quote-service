package com.wesleyreisz.patterns.quoteservice.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Quote")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Quote {
    @Id
    @Indexed
    private Long id;
    private String quoteText;
    private String author;
    public Quote(Long id, String quoteText, String author){
        this.id=id;
        this.quoteText=quoteText;
        this.author=author;
    }
}
