package com.praveen.springai_openai.service;

import com.praveen.springai_openai.entity.HelpDeskTicket;
import com.praveen.springai_openai.model.TicketRequest;
import com.praveen.springai_openai.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;


    public HelpDeskTicket createTicket(
            TicketRequest ticketRequest,
            String username
    ){
        HelpDeskTicket helpDeskTicket= HelpDeskTicket
                .builder()
                .issue(ticketRequest.issue())
                .username(username)
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return helpDeskTicketRepository.save(helpDeskTicket);
    }

    public List<HelpDeskTicket> getAllTicketsByUsername(
            String username
    ){
        return helpDeskTicketRepository.findByUsername(username);
    }

}
