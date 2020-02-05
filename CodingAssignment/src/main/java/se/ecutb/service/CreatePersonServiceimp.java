package se.ecutb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ecutb.data.IdSequencers;
import se.ecutb.model.AbstractPersonFactory;
import se.ecutb.model.Address;
import se.ecutb.model.Person;

@Component
public class CreatePersonServiceimp extends AbstractPersonFactory implements CreatePersonService {

    @Autowired
    private IdSequencers sequencers;

    @Override
    public Person create(String firstName, String lastName, String email) throws IllegalArgumentException {
        Person p = createNewPerson(sequencers.nextPersonId(), firstName, lastName, email, null);
        return p;
    }

    @Override
    public Person create(String firstName, String lastName, String email, Address address) throws IllegalArgumentException {
        Person p = createNewPerson(sequencers.nextPersonId(), firstName, lastName, email, address);
        return p;
    }
}
