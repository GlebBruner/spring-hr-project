import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import ua.nure.repository.EmployeeRepository;
import ua.nure.service.EmployeeService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles({"prod", "jpa"})
public class MockTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void employeeRaiseTest() throws IllegalArgumentException {
        employeeService.getCandidatesforRaise(-1);
    }

    @Test
    public void isMethodInvoked() {
        employeeService.getCandidatesforRaise(1);
        verify(repository, times(1)).findAll();
    }

}
