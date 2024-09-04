package com.example.web2.Service;
import com.example.web2.DTO.response.BookResponse;
import com.example.web2.DTO.request.BookRequest;

import java.io.IOException;
import java.util.List;

public interface BookService {
    public List<BookResponse> getALLBook() throws IOException;
    public BookResponse findBookById(int id) throws IOException;
    public List<BookResponse> findBookByCodition(String nameAuthor ,int totalPages,String dateStart,String dateEnd,String bookCoditionRequest,String category)throws IOException;
    public List<BookResponse> sortBookByDate()throws IOException;
    public BookResponse createBook(BookRequest bookRequest)throws IOException;
    public String DeleteBookById(int id);

}
