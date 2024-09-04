package com.example.web2.Service;

import com.example.web2.Entity.BookContent;

import java.util.List;

public interface BookContentService {
    public List<BookContent> GetBookContentByBookId(int BookId);
    public String DeleteBookContentByBookId(int BookId);
}
