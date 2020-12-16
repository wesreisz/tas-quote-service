package com.wesleyreisz.patterns.quoteservice.quotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = {"/quotes"})
public class QuoteController {
    private static final Logger LOG = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    private QuoteService quoteService;

    @RequestMapping("/")
    public ResponseEntity<Quote> getRandomQuote(){
        ResponseEntity<Quote> resp = quoteService.getQuote();
        LOG.info(resp.toString());
        return resp;
    }

    @RequestMapping("/list")
    public ResponseEntity<List<Quote>> getQuotes(){
        ResponseEntity<List<Quote>> resps =  quoteService.getAllQuotes();
        LOG.info(resps.toString());
        return resps;
    }

    @PostMapping("/add")
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote){
        LOG.info("Added: " + quote.toString());
        return quoteService.saveQuote(quote);
    }

    @RequestMapping("/err")
    public Quote throwAnException(RestTemplate restTemplate){
        LOG.info("Forcing an exception back to the client");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown exception");
    }
}
