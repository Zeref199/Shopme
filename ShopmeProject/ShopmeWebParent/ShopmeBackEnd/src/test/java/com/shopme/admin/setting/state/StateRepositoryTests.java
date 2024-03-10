package com.shopme.admin.setting.state;

import com.shopme.admin.setting.state.StateRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {

    @Autowired
    private StateRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateStatesInIndia(){
        Integer countryId = 4;
        Country country = entityManager.find(Country.class, countryId);

        //State state = repo.save(new State("California", country));
        //State state = repo.save(new State("Texas", country));
        State state = repo.save(new State("New York", country));

        assertThat(state).isNotNull();
        assertThat(state.getId()).isGreaterThan(0);
    }
}
