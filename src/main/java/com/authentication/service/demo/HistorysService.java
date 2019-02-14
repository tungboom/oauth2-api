package com.authentication.service.demo;

import java.security.Principal;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.HistorysDto;

/**
 * @author TungBoom
 *
 */
public interface HistorysService {

	Datatable search(Principal principal, HistorysDto historysDto);
    ResultDto delete(Principal principal, Long historyId);
    HistorysDto getDetail(Principal principal, Long historyId);
    ResultDto add(Principal principal, HistorysDto historysDto);
    ResultDto edit(Principal principal, HistorysDto historysDto);
}
