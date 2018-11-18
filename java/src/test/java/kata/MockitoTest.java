package kata;

import java.time.Clock;

import org.junit.Test;

import info.solidsoft.mockito.java8.api.WithMockito;

public class MockitoTest implements WithMockito {

    @Test
    public void worksWithMockito() throws Exception {
        Clock collaborator = mock(Clock.class);
        when(collaborator.millis()).thenReturn(0L);

        collaborator.millis();

        verify(collaborator).millis();
    }

}
