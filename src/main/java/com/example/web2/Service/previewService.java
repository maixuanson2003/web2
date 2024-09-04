package com.example.web2.Service;

public interface previewService {
    public void PostPreViewBook(int BookId,String ContentPreview,String rate);
    public String DeletePreViewById(int id);
    public void  UpdatePreview(int BookId,String ContentPreview,String rate);
}
