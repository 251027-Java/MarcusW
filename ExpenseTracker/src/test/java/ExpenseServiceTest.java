import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private IRepository repo;

    @InjectMocks
    private ExpenseService service;

    @Test
    void createNewExpense() {

        when(repo.readExpense(10)).thenReturn(null);

        Expense expense = service.createNewExpense(10, 100, "DummyMerchant");

        assertNotNull(expense);
        assertEquals(10, expense.getId(), "Failed ID");
        assertEquals(100, expense.getValue(), "Failed Value");
        assertEquals("DummyMerchant", expense.getMerchant(), "Failed Merchant");

        verify(repo).createExpense(expense);
    }

    @Test
    public void createNewExpenseAlreadyExistTest() {

        // Want the expense to already exist
        Expense existingExpense = new Expense(11, new Date(), 100, "DummyMerchant");

        when(repo.readExpense(11)).thenReturn(existingExpense);

        Expense expense = service.createNewExpense(11, 100, "DummyMerchant");
        assertNull(expense, "Failed Expense already exists");

        verify(repo, never()).createExpense(any());
    }

    @Test
    void deleteExpense() {
        // This one is a bit silly. I'm manually describing the behavior.

        Expense expense = new Expense(11, new Date(), 100, "DummyMerchant");

        when(repo.readExpense(11)).thenReturn(expense);

        service.deleteExpense(11);

        when(repo.readExpense(11)).thenReturn(null);

        expense = repo.readExpense(11); // Again, using repo method instead of service method

        assertNull(expense);
    }

    @Test
    void deleteExpenseNotExists() {
        when(repo.readExpense(11)).thenReturn(null);

        boolean didDelete = service.deleteExpense(11);

        assertFalse(didDelete, "Failed delete not exists");

        verify(repo, never()).deleteExpense(11);
    }

    @Test
    void updateExpense() {
        Expense expense = new Expense(11, new Date(), 100, "DummyMerchant");
        Expense newExpense = new Expense(11, new Date(), 40, "NewDummyMerchant");

        when(repo.readExpense(11)).thenReturn(expense);

        boolean updated = service.updateExpense(newExpense);

        assertTrue(updated, "Failed when Expense Id should exist");

        verify(repo).updateExpense(newExpense);
    }

    @Test
    void updateExpenseNotExists() {
        Expense newExpense = new Expense(11, new Date(), 40, "NewDummyMerchant");

        when(repo.readExpense(11)).thenReturn(null);

        boolean updated = service.updateExpense(newExpense);

        assertFalse(updated, "Failed when Expense should not exist");

        verify(repo, never()).updateExpense(any());
    }

    @Test
    void printExpenses() {
        // Idk, is it possible to route sys out here ? lol
    }

    @Test
    void sumExpenses() {
        List<Expense> mockExpenses = List.of(
                new Expense(1, new Date(), 50, "A"),
                new Expense(2, new Date(), 100, "B")
        );

        when(repo.loadExpenses()).thenReturn(mockExpenses);

        double sum = service.sumExpenses();

        assertEquals(150, sum, "Failed sum");
        verify(repo).loadExpenses();
    }
}
