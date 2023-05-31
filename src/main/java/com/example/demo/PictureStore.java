package com.example.demo;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Component;

@Component
public interface PictureStore extends ContentStore<User, String> {

}
