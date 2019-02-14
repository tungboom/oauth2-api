package com.authentication.api.demo;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.HistorysDto;
import com.authentication.service.demo.HistorysService;
import com.authentication.utils.Constants;

/**
 * @author TungBoom
 *
 */
@RestController
@RequestMapping(Constants.API_PATH_PREFIX + "historys")
public class HistorysAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistorysAPI.class);

    @Autowired
    HistorysService historysService;
    
    @PostMapping("/search")
    public ResponseEntity<Datatable> search(Principal principal, HistorysDto historysDto) {
        Datatable data = historysService.search(principal, historysDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/delete")
    public ResponseEntity<ResultDto> delete(Principal principal, Long userId) {
        ResultDto result = historysService.delete(principal, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getDetail")
    public ResponseEntity<HistorysDto> getDetail(Principal principal, Long userId) {
    	HistorysDto data = historysService.getDetail(principal, userId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResultDto> add(Principal principal, HistorysDto historysDto) {
    	ResultDto data = historysService.add(principal, historysDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/edit")
    public ResponseEntity<ResultDto> edit(Principal principal, HistorysDto historysDto) {
    	ResultDto data = historysService.edit(principal, historysDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
