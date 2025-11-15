package com.praveen.springai_openai.tools;

import com.praveen.springai_openai.entity.HelpDeskTicket;
import com.praveen.springai_openai.model.TicketRequest;
import com.praveen.springai_openai.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

    private final Logger logger = LoggerFactory.getLogger(HelpDeskTools.class);

    private final HelpDeskTicketService helpDeskTicketService;

    @Tool(
            name="createTicket",
            description = "Create a support ticket"
    )
    public String createTicket(
            @ToolParam(description = "Details to create a support ticket")
            TicketRequest ticketRequest,
            ToolContext toolContext
    ) {
        logger.info("Creating a support ticket");
        String username = (String) toolContext.getContext().get("username");
        HelpDeskTicket ticket = helpDeskTicketService.createTicket(
                ticketRequest,
                username
        );
        return "Ticket #" + ticket.getId() + " created successfully for user "+ ticket.getUsername();
    }

    @Tool(
            name="getTickets",
            description = "Get all tickets for a user based on a given username"
            //returnDirect = true
    )
    public List<HelpDeskTicket> getAllTickets(
            ToolContext toolContext
    ) {
        String username = (String) toolContext.getContext().get("username");
        logger.info("Getting all tickets for user {}",username);
        //throw new RuntimeException("Not yet implemented");
        return helpDeskTicketService.getAllTicketsByUsername(username);
    }

}
