package kata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;

import org.junit.Test;

public class MockitoTest {

    @Test
    public void worksWithMockito() throws Exception {
        Clock collaborator = mock(Clock.class);
        when(collaborator.millis()).thenReturn(0L);

        collaborator.millis();

        verify(collaborator).millis();
    }

}
