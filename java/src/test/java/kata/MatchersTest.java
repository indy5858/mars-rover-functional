package kata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MatchersTest {

    @Test
    public void canUseHamcrest() throws Exception {
        assertThat(true, is(true));
    }

    @Test
    public void canUseAssertJ() throws Exception {
        assertThat(true).isEqualTo(true);
    }

}
