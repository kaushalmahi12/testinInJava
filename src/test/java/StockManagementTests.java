import com.kaushal.isbnValidator.Book;
import com.kaushal.isbnValidator.ISBNDataService;
import com.kaushal.isbnValidator.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTests {
    ISBNDataService databaseService;
    ISBNDataService webService;
    StockManager stockManager;

    @BeforeEach
    public void setupForEachTest() {
        databaseService = mock(ISBNDataService.class);
        webService = mock(ISBNDataService.class);
        stockManager = new StockManager();
    }

    @Test
    @DisplayName("should return correct locator code.")
    public void canGetACorrectLocatorCode() {
        ISBNDataService webService = new ISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book("0306406152", "Think Code Kill", "Kaushal Kumar");
            }
        };

        ISBNDataService databaseService = new ISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

        String isbn = "0306406152";
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("6152K3", locatorCode);
    }

    @Test
    @DisplayName("should use results from database if present in database.")
    public void databaseIsUsedIfDataIsPresent() {

        when(databaseService.lookup("0306406152")).thenReturn(new Book("0306406152", "Think Code Kill", "Kaushal Kumar"));

        String isbn = "0306406152";
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);
        stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup("0306406152");
        verify(webService, times(0)).lookup(anyString());
    }

    @Test
    @DisplayName("should use results from webService if not present in database.")
    public void webSericeIsUsedIfDataIsNotPresentInDatabase() {
        when(webService.lookup("0306406152")).thenReturn(new Book("0306406152", "Think Code Kill", "Kaushal Kumar"));

        String isbn = "0306406152";
        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);
        stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup(anyString());
        verify(webService, times(1)).lookup("0306406152");
    }

}
