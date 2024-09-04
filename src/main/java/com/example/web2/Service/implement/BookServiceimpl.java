package com.example.web2.Service.implement;
import com.example.web2.DTO.request.BookRequest;
import com.example.web2.DTO.response.BookResponse;
import com.example.web2.Entity.book;
import com.example.web2.Repository.bookRepository;
import com.example.web2.Service.BookService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;
import com.example.web2.Entity.preview;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.file.Files;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class BookServiceimpl implements BookService{
    @Autowired
    private bookRepository bookRepository;
    private final String UPLOAD_DIR = "D:\\web2\\src\\main\\java\\asset";
    @Override
    public List<BookResponse> getALLBook() throws IOException {
        List<book> listbook=bookRepository.findAll();

        List<BookResponse> bookResponses=new ArrayList<>();
        for(book books:listbook){
            Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", books.getImage());
            byte[] imageBytes = Files.readAllBytes(imagePath);
            BookResponse bookResponseSet=new BookResponse();
            bookResponseSet.setAuthor(books.getAuthor());
            bookResponseSet.setCategory(books.getCategory());
            bookResponseSet.setDescription(books.getDescription());
            bookResponseSet.setPublisher(books.getPublisher());
            bookResponseSet.setTotalpages(books.getTotalpages());
            bookResponseSet.setTitle(books.getTitle());
            bookResponseSet.setImage(imageBytes);
            bookResponses.add(bookResponseSet);
        }
        return  bookResponses;

    }
    @Override
    public BookResponse findBookById(int id) throws IOException{
        book book=bookRepository.findById(id).orElseThrow(()->new RuntimeException("book not found"));
        Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", book.getImage());
        byte[] imageBytes = Files.readAllBytes(imagePath);
        BookResponse bookResponseSet=new BookResponse();
        bookResponseSet.setAuthor(book.getAuthor());
        bookResponseSet.setCategory(book.getCategory());
        bookResponseSet.setDescription(book.getDescription());
        bookResponseSet.setPublisher(book.getPublisher());
        bookResponseSet.setTotalpages(book.getTotalpages());
        bookResponseSet.setTitle(book.getTitle());
        bookResponseSet.setImage(imageBytes);
        return  bookResponseSet;
    }
    private boolean CheckDateBook(book books,String dateStart,String dateEnd){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dateStart, formatter);
        LocalDate endDate = LocalDate.parse(dateEnd, formatter);
        LocalDate createAt=LocalDate.parse(books.getCreatedAt(),formatter);
        if(createAt.isEqual(startDate)||createAt.isAfter(startDate)&&createAt.isEqual(endDate)||createAt.isBefore(endDate)){
            return true;
        }else {
            return false;
        }

    }
    private boolean CheckLike(book books){
        List<preview> listpreview=books.getPreviews();
        int resultLike=0;
        int resultDisLike=0;
        for (preview previews:listpreview){
            if (previews.getRate()==1){
                resultLike++;
            }
            if(previews.getRate()==0){
                resultDisLike++;
            }
        }
        if(resultLike>resultDisLike){
            return true;
        }
        return false;
    }

    @Override
    public List<BookResponse> findBookByCodition(String nameAuthor ,int totalPages,String dateStart,String dateEnd,String bookCoditionRequest,String category)throws IOException{
        List<book> listbook=bookRepository.findAll();
        List<BookResponse> result=new ArrayList<>();
        if (bookCoditionRequest.equals("string")){
            for (book books: listbook){
                boolean matchesAuthor = nameAuthor==null || books.getAuthor().equals(nameAuthor);
                boolean matchesTotalPages = totalPages <= 0 || books.getTotalpages()== totalPages;
                boolean matchesDate = CheckDateBook(books, dateStart, dateEnd);
                boolean matchesCategory = category==null || books.getCategory().equals(category);
                if (matchesAuthor && matchesTotalPages && matchesDate  && matchesCategory) {
                    Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", books.getImage());
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    BookResponse bookResponse=new BookResponse().builder()
                            .title(books.getTitle())
                            .image(imageBytes)
                            .author(books.getAuthor())
                            .category(books.getCategory())
                            .description(books.getDescription())
                            .totalpages(books.getTotalpages())
                            .publisher(books.getPublisher())
                            .build();
                    result.add(bookResponse);
                }
            }
        }
        if(bookCoditionRequest.equals("Đánh giá cao")){
            for (book books: listbook){
                boolean matchesAuthor = nameAuthor==null || books.getAuthor().equals(nameAuthor);
                boolean matchesTotalPages = totalPages <= 0 || books.getTotalpages()== totalPages;
                boolean matchesDate = CheckDateBook(books, dateStart, dateEnd);
                boolean matchesCategory = category==null || books.getCategory().equals(category);
                boolean matchesLikeCondition = CheckLike(books);
                if (matchesAuthor && matchesTotalPages && matchesDate && matchesLikeCondition && matchesCategory) {
                    Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", books.getImage());
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    BookResponse bookResponse=new BookResponse().builder()
                            .title(books.getTitle())
                            .image(imageBytes)
                            .author(books.getAuthor())
                            .category(books.getCategory())
                            .description(books.getDescription())
                            .totalpages(books.getTotalpages())
                            .publisher(books.getPublisher())
                            .build();
                    result.add(bookResponse);
                }
            }
        }
        if(bookCoditionRequest.equals("Đánh giá Thấp")){
            for (book books: listbook){
                boolean matchesAuthor = nameAuthor==null || books.getAuthor().equals(nameAuthor);
                boolean matchesTotalPages = totalPages <= 0 || books.getTotalpages()== totalPages;
                boolean matchesDate = CheckDateBook(books, dateStart, dateEnd);
                boolean matchesCategory = category==null || books.getCategory().equals(category);
                boolean matchesLikeCondition =  !CheckLike(books);
                if (matchesAuthor && matchesTotalPages && matchesDate && matchesLikeCondition && matchesCategory) {
                    Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", books.getImage());
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    BookResponse bookResponse=new BookResponse().builder()
                            .title(books.getTitle())
                            .image(imageBytes)
                            .author(books.getAuthor())
                            .category(books.getCategory())
                            .description(books.getDescription())
                            .totalpages(books.getTotalpages())
                            .publisher(books.getPublisher())
                            .build();
                    result.add(bookResponse);
                }
            }
        }
        return result;
    }
    @Override
    public List<BookResponse> sortBookByDate()throws IOException{
        List<book> listbooks=bookRepository.findAll();
        List<BookResponse> result=new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Collections.sort(listbooks, new Comparator<book>() {
            @Override
            public int compare(book book1  , book book2) {
                LocalDate createAt1 = LocalDate.parse(book1.getCreatedAt(), formatter);
                LocalDate createAt2 = LocalDate.parse(book2.getCreatedAt(), formatter);
                if( createAt1.isBefore(createAt2)){
                    return 1;
                }else {
                    return -1;
                }

            }
        });
        for(book books:listbooks){
            Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", books.getImage());
            byte[] imageBytes = Files.readAllBytes(imagePath);
            BookResponse bookResponse=new BookResponse().builder()
                    .title(books.getTitle())
                    .image(imageBytes)
                    .author(books.getAuthor())
                    .category(books.getCategory())
                    .description(books.getDescription())
                    .totalpages(books.getTotalpages())
                    .publisher(books.getPublisher())
                    .build();
            result.add(bookResponse);
        }
        return result;
    }
    @Override
    @Transactional
    public BookResponse createBook(BookRequest bookRequest)throws IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.parse(LocalDate.now().toString(),formatter);
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Xử lý tên tệp để tránh trùng lặp
        String fileName = System.currentTimeMillis() + "_" + bookRequest.getImage().getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Lưu tệp vào hệ thống tập tin
        bookRequest.getImage().transferTo(filePath.toFile());

        book books=new book().builder()
                .createdAt(today.toString())
                .image(fileName)
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .title(bookRequest.getTitle())
                .updatedAt("")
                .description(bookRequest.getDescription())
                .category(bookRequest.getCategory())
                .totalpages(bookRequest.getTotalpages())
                .build();
        bookRepository.save(books);
        Path imagePath = Paths.get("D:\\web2\\src\\main\\java\\asset", books.getImage());
        byte[] imageBytes = Files.readAllBytes(imagePath);
        BookResponse bookResponse=new BookResponse().builder()
                .title(books.getTitle())
                .image(imageBytes)
                .author(books.getAuthor())
                .category(books.getCategory())
                .description(books.getDescription())
                .totalpages(books.getTotalpages())
                .publisher(books.getPublisher())
                .build();
        return bookResponse;
    }
    @Override
    @Transactional
    public String DeleteBookById(int id){
        bookRepository.deleteById(id);
        return "ok";
    }
}