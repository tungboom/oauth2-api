package com.authentication.repository.demo;

import java.security.Principal;

import org.springframework.stereotype.Repository;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.HistorysDto;

/**
 * @author TungBoom
 *
 */
@Repository
public interface HistorysRepository {
	
	Datatable search(Principal principal, HistorysDto historysDto);
    ResultDto delete(Principal principal, Long historyId);
    HistorysDto getDetail(Principal principal, Long historyId);
    ResultDto add(Principal principal, HistorysDto historysDto);
    ResultDto edit(Principal principal, HistorysDto historysDto);
}
