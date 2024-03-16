package az.practice.bookstore.service;

import az.practice.bookstore.exception.BookNotFoundException;
import az.practice.bookstore.exception.ExistsSerialNumberException;
import az.practice.bookstore.model.dto.request.AuthorsDto;
import az.practice.bookstore.model.dto.request.BookDto;
import az.practice.bookstore.model.dto.request.PublisherDto;
import az.practice.bookstore.model.entity.Authors;
import az.practice.bookstore.model.entity.Book;
import az.practice.bookstore.model.entity.Publisher;
import az.practice.bookstore.model.mapper.AuthorMapper;
import az.practice.bookstore.model.mapper.BookMapper;
import az.practice.bookstore.model.mapper.PublisherMapper;
import az.practice.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;

    public BookDto addBook(BookDto book) {
        if (bookRepository.existsBySerialNumber(book.getSerialNumber())) {
            throw new ExistsSerialNumberException("this serial number already has used");
        }
        Book bookEntity = bookMapper.mapToBookEntity(book);
        Authors authorsEntity = authorMapper.mapToAuthorEntity(book.getAuthorsDto());
        Publisher publisherEntity = publisherMapper.mapToEntity(book.getPublisherDto());
        bookEntity.setPublishers(publisherEntity);
        bookEntity.setAuthors(authorsEntity);
        bookRepository.save(bookEntity);
        return book;

    }

    public List<BookDto> getAllBook() {
        List<Book> list = bookRepository.findAll();
//        List<BookResponseDto> dtoList = new ArrayList<>();
//
//        for (Book book : list) {
//            BookResponseDto bookResponseDto = bookMapper.mapToResponseDto(book);
//            AuthorsResponseDto authorsResponseDto = authorMapper.mapToResponse(book.getAuthors());
//            bookResponseDto.setAuthorsResponseDto(authorsResponseDto);
//            dtoList.add(bookResponseDto);
//        }
//        return dtoList;

        List<BookDto> bookResponseDtoList = list.stream().map(book -> {
                    BookDto bookDto = bookMapper.mapToBookDto(book);
                    AuthorsDto authorsDto = authorMapper.mapToAuthorDto(book.getAuthors());
                    PublisherDto publisherDto = publisherMapper.mapToPublisherDto(book.getPublishers());
                    bookDto.setPublisherDto(publisherDto);
                    bookDto.setAuthorsDto(authorsDto);
                    return bookDto;
                })
                .collect(Collectors.toList());
        return bookResponseDtoList;
    }

    public BookDto getBookId(Long id) {
        Book bookEntity = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("This id not found :" + id));
        BookDto bookDto = bookMapper.mapToBookDto(bookEntity);
        PublisherDto publisherDto = publisherMapper.mapToPublisherDto(bookEntity.getPublishers());
        bookDto.setPublisherDto(publisherDto);
        AuthorsDto authorsDto = authorMapper.mapToAuthorDto(bookEntity.getAuthors());
        bookDto.setAuthorsDto(authorsDto);
        return bookDto;

    }

    public String deleteById(Long id) {
//        Book bookEntity = bookRepository.findById(id)
//        .orElseThrow(() -> new BookNotFoundException("This id not found :" + id));
//        bookRepository.deleteById(bookEntity.getId());
//        bu commit hissesi de ishleyir sadece ashagida yeni bir yazilish sinayiram
        bookRepository.findById(id).ifPresentOrElse(book -> {
                    bookRepository.deleteById(book.getId());
                }, () -> {
                    throw new BookNotFoundException("This id not found :" + id);
                }
        );
        return "Success";
    }


    public BookDto updateBook(Long id, BookDto bookDto) {
        Book bookOldEntity = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("This id not found :" + id));
        if (bookOldEntity != null) {
            Book updateBook = bookMapper.mapToBookEntity(bookDto);
            Authors updateAuthors = authorMapper.mapToAuthorEntity(bookDto.getAuthorsDto());
            Publisher updatePublisher = publisherMapper.mapToEntity(bookDto.getPublisherDto());
            updateBook.setId(bookOldEntity.getId());
            updateAuthors.setId(bookOldEntity.getAuthors().getId());
            updatePublisher.setId(bookOldEntity.getPublishers().getId());

            updateBook.setAuthors(updateAuthors);
            updateBook.setPublishers(updatePublisher);
            bookRepository.save(updateBook);

            return bookDto;
        }
        return null;
    }
}
