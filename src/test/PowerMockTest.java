import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.nure.repository.jpa.JpaEmployeeRepository;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JpaEmployeeRepository.class)
public class PowerMockTest {

    @Test
    public void tryStaticTest() {
        PowerMockito.mockStatic(JpaEmployeeRepository.class);
        when(JpaEmployeeRepository.staticMethodTest()).thenReturn("Stubbed method!");
        System.out.println(JpaEmployeeRepository.staticMethodTest());
    }

}
