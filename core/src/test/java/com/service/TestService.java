package com.service;

import com.config.WebConfig;
import com.exception.NotSuchElementException;
import com.repository.NumberRepository;
import com.wire.GameResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("com.config")
@ContextConfiguration(classes = WebConfig.class)
public class TestService {

    @Autowired
    NumberRepository numberRepository;

    @Test
    public void TestSetNumberToDB() {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        int id = numberServiceImpl.setNumber(5);
        int secretNumber = numberServiceImpl.getNumberById(id).getSecretNumber();
        assertEquals(5, secretNumber);
    }

    @Test
    public void TestUpdateNumberInDB() {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        numberServiceImpl.setNumber(1);
        numberServiceImpl.updateNumberById(1,6);
        int secretNumber = numberServiceImpl.getNumberById(1).getSecretNumber();
        assertEquals(6, secretNumber);
    }

    @Test
    public void TestGame_WhenGuessEqualsNumber_ExpectedGameResultWinner() {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        numberServiceImpl.setNumber(5);
        numberServiceImpl.updateNumberById(1,1);
        GameResult gameResult = numberServiceImpl.game(1, 1);
        assertEquals("Winner!", gameResult.getResult());
    }

    @Test
    public void TestGame_WhenGuessNotEqualNumber_ExpectedGameResultLooser() {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        numberServiceImpl.setNumber(4);
        numberServiceImpl.updateNumberById(1,5);
        GameResult game = numberServiceImpl.game(1, 1);
        assertEquals("Looser!",game.getResult());
    }

    @Test(expected = NotSuchElementException.class)
    public void TestGame_WhenUserIdNotExist_ExpectedException(){
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        int id = numberServiceImpl.setNumber(9);
        numberServiceImpl.game(id+1, 8);
    }

    @Test(expected = NotSuchElementException.class)
    public void TestUpdate_WhenUserIdNotExist_ExpectedException(){
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        numberServiceImpl.updateNumberById(10,7);
    }

}
