package com.service;

import com.domain.Number;
import com.exception.NotSuchElementException;
import com.repository.NumberRepository;
import com.wire.GameResult;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@NoArgsConstructor
@Service
public class NumberServiceImpl implements NumberService {


    NumberRepository numberRepository;

    @Autowired
    public NumberServiceImpl(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @Override
    public void updateNumberById(int id, int secretNumber) {
        if (numberRepository.exists(id)) {
            Number number = new Number(id, secretNumber);
            numberRepository.save(number);
        } else {
            throw new NotSuchElementException("There is No User with id :" + id + " !");
        }
    }

    @Override
    public Number getNumberById(int id) {
        Number num = numberRepository.findOne(id);
        return num;
    }

    @Override
    public int setNumber(int intNumber) {
        Number num1 = new Number(intNumber);
        Number save = numberRepository.save(num1);
        return save.getId();
    }

    @Override
    public GameResult game(int id, int guess) {
        if (numberRepository.exists(id)) {
            int number = numberRepository.findOne(id).getSecretNumber();
            if (guess == number)
                return new GameResult("Winner!");
            else {
                return new GameResult("Looser!");
            }
        } else {
            throw new NotSuchElementException("There is No User with id :" + id + " !");
        }
    }

    @Override
    public boolean checkIfIdExist(int id) {
        return numberRepository.exists(id);
    }


}