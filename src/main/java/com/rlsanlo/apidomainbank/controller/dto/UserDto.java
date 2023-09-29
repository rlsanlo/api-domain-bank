package com.rlsanlo.apidomainbank.controller.dto;

import com.rlsanlo.apidomainbank.domain.model.User;

import java.util.List;

public record UserDto(
        Long id,
        String name,
        AccountDto account,
        CardDto card,
        List<FeatureDto> feattures,
        List<NewsDto> news
) {

    public UserDto(User model) {
        this(model.getId(), model.getName(), new AccountDto(model.getAccount()), new CardDto(model.getCard()), model.getFeattures().stream().map(FeatureDto::new).toList(), model.getNews().stream().map(NewsDto::new).toList());
    }

    public User toModel() {
        User model = new User();
        model.setId(this.id);
        model.setName(this.name);
        model.setAccount(this.account.toModel());
        model.setCard(this.card.toModel());
        model.setFeattures(this.feattures.stream().map(FeatureDto::toModel).toList());
        model.setNews(this.news.stream().map(NewsDto::toModel).toList());
        return model;
    }
}
