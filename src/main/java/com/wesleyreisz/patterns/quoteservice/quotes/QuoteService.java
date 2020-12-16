package com.wesleyreisz.patterns.quoteservice.quotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuoteService{
    @Autowired
    QuoteRepository quoteRepository;

    public ResponseEntity<Quote> getQuote(){
        List<Quote> quotes = new ArrayList<>();
        quoteRepository.findAll().forEach(quotes::add);

        if (quotes.size()>0) {
            return new ResponseEntity<>(quotes.get(new Random().nextInt(quotes.size())), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Quote(Long.parseLong("0"),"No Data Available", "N/A"), HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Quote>>  getAllQuotes() {
        List<Quote> quotes = new ArrayList<>();
        quoteRepository.findAll().forEach(quotes::add);
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    public ResponseEntity<Quote> saveQuote(final Quote quote) {
        Quote savedQuote = quoteRepository.save(quote);
        return new ResponseEntity<>(savedQuote, HttpStatus.CREATED);
    }
}
