package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {

    Card getById(long id);

    void saveCard (Card card);

    Card getByNumber(String number);



}
