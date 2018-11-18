package kata;

import java.util.HashSet;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class JMockTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void worksWithJMock() {
        final Subscriber subscriber = context.mock(Subscriber.class);

        Publisher publisher = new Publisher();
        publisher.add(subscriber);

        final String message = "message";

        // expectations
        context.checking(new Expectations() {
            {
                oneOf(subscriber).receive(message);
            }
        });

        // execute
        publisher.publish(message);
    }

    class Publisher {

        private Set<Subscriber> subscribers = new HashSet<>();

        public void add(Subscriber subscriber) {
            subscribers.add(subscriber);
        }

        public void publish(String message) {
            subscribers.forEach(subscriber -> subscriber.receive(message));
        }

    }

    interface Subscriber {

        public void receive(String message);

    }

}
