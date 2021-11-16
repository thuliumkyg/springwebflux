package com.example.springwebflux.service.impl;

import com.example.springwebflux.entity.Article;
import com.example.springwebflux.service.ArticleReactiveRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleReactiveRedisRepositoryImpl implements ArticleReactiveRedisRepository {

    @Autowired
    private ReactiveRedisTemplate<String, Object > reactiveRedisTemplate;

    private static final String HASH_NAME = "Artivle:";

    @Override
    public Mono<Boolean> saveArticle(Article article) {
        return reactiveRedisTemplate.opsForValue().set(HASH_NAME + article.getId(), article);

    }

    @Override
    public Mono<Boolean> updateArticel(Article article) {
        return reactiveRedisTemplate.opsForValue().set(HASH_NAME + article.getId(), article);
    }

    @Override
    public Mono<Object> findArticleById(String articleId) {
        return reactiveRedisTemplate.opsForValue().get(HASH_NAME + articleId);
    }

    @Override
    public Flux<Object> findAllArticles() {
        return reactiveRedisTemplate.keys(HASH_NAME + "*").flatMap((String key) -> {
            Mono<Object> mono = reactiveRedisTemplate.opsForValue().get(key);
            return mono;
        });
    }
}
