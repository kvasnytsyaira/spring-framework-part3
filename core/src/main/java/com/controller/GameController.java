package com.controller;

import com.service.NumberServiceImpl;
import com.wire.GameResult;
import com.wire.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
public class GameController {


    NumberServiceImpl numberServiceImpl;

    @Autowired
    public GameController(NumberServiceImpl numberServiceImpl) {
        this.numberServiceImpl = numberServiceImpl;
    }

    @GetMapping("/")
    public ResponseEntity<Message> hello() {
        return ResponseEntity.ok().body(new Message("Hello!"));
    }


    @GetMapping("/{id}/guess/{guess}")
    public ResponseEntity<GameResult> playGame(@PathVariable int id, @PathVariable int guess) {
        GameResult game = numberServiceImpl.game(id, guess);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }


    @PostMapping("/{id}/update/{number}")
    public void update(@PathVariable int id, @PathVariable int number) {
        numberServiceImpl.updateNumberById(id, number);
    }

    @PostMapping("/{number}")
    public void setNumberToDB(
            @Valid @Positive(message = "Number must be positive !")
            @PathVariable int number) {
        numberServiceImpl.setNumber(number);
    }

    @ExceptionHandler
    public String constraintViolationHandler(ConstraintViolationException ex) {
        return ex.getConstraintViolations().iterator().next()
                .getMessage();
    }
}

