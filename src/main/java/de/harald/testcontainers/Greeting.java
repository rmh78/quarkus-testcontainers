package de.harald.testcontainers;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Greeting {

    @Id
    public Long id;

    public String message;
    
}