package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public final class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cvvIsCreated(){
        int cvv = CardUtils.getCvv();

        assertThat(cvv, is(notNullValue()));
    }

    @Test
    public void cardNumberIsString(){
        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber,isA(String.class));
    }

    @Test
    public void cardNumberSize(){
        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber,hasLength(19));
    }

    @Test
    public void cvvIsInt(){
        int cvv = CardUtils.getCvv();

        assertThat(cvv,is(lessThan(999)));
    }

}
