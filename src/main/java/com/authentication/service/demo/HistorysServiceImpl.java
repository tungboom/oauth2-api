package com.authentication.service.demo;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.HistorysDto;
import com.authentication.repository.demo.HistorysRepository;

/**
 * @author TungBoom
 *
 */
@Service
public class HistorysServiceImpl implements HistorysService {
    private final Logger LOG = LoggerFactory.getLogger(HistorysServiceImpl.class);

    @Autowired
    protected HistorysRepository historysRepository;
    
    @Override
    public Datatable search(Principal principal, HistorysDto historysDto) {
    	LOG.debug("Request to search : {}", historysDto);
        return historysRepository.search(principal, historysDto);
    }
    
    @Override
    public ResultDto delete(Principal principal, Long historyId) {
    	LOG.debug("Request to delete : {}", historyId);
        return historysRepository.delete(principal, historyId);
    }

    @Override
    public HistorysDto getDetail(Principal principal, Long historyId) {
    	LOG.debug("Request to getDetail : {}", historyId);
        return historysRepository.getDetail(principal, historyId);
    }
    
    @Override
    public ResultDto add(Principal principal, HistorysDto historysDto) {
    	LOG.debug("Request to add : {}", historysDto);
        return historysRepository.add(principal, historysDto);
    }
    
    @Override
    public ResultDto edit(Principal principal, HistorysDto historysDto) {
    	LOG.debug("Request to edit : {}", historysDto);
        return historysRepository.edit(principal, historysDto);
    }
}
