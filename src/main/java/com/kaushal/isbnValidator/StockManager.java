package com.kaushal.isbnValidator;

public class StockManager {
    private ISBNDataService webService;
    private ISBNDataService databaseService;

    public void setWebService(ISBNDataService isbnDataService) {
        this.webService = isbnDataService;
    }

    public void setDatabaseService(ISBNDataService service) {
        this.databaseService = service;
    }

    public String getLocatorCode(String isbn) {
        Book book = databaseService.lookup(isbn);

        if (book == null) {
            book = webService.lookup(isbn);
        }

        return isbn.substring(isbn.length()-4) + Character.toUpperCase(book.getAuthor().charAt(0)) + book.getNumberOfWordsInTitle();
    }
}
