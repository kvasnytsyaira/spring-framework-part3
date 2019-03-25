package com.service;

import com.domain.Number;
import com.wire.GameResult;

public interface NumberService {

    int setNumber(int number);

    Number getNumberById(int id);

    GameResult game(int guess, int number);

    void updateNumberById(int id, int secretNumber);

    boolean checkIfIdExist(int id);
}
