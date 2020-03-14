package de.harald.testcontainers;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped 
public class GreetingGenerator {

    @Outgoing("greetings")                        
    public Flowable<KafkaRecord<String, String>> generate() {           
        return Flowable.interval(1, TimeUnit.SECONDS)
                .map(tick -> KafkaRecord.of("hello", "world"));
    }
    
}