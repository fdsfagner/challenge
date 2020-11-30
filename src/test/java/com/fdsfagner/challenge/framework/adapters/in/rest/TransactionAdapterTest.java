package com.fdsfagner.challenge.framework.adapters.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdsfagner.challenge.application.dtos.EventDto;
import com.fdsfagner.challenge.application.dtos.TransactionDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get balance for non-existing account")
    @Order(1)
    void getBalanceForNonExistingAccount() throws Exception {
        MvcResult result = mockMvc.perform(get("/balance?account_id=100")
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        Assertions.assertEquals(contentAsString, "0");
    }

    @Test
    @DisplayName("Create account with initial balance")
    @Order(2)
    void createAccountWithInitialBalance() throws Exception{
        EventDto eventDto = new EventDto();
        eventDto.setType("deposit");
        eventDto.setDestination("100");
        eventDto.setAmount(BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        TransactionDto response = objectMapper.readValue(contentAsString, TransactionDto.class);

        Assertions.assertEquals(response.getDestination().getId(), "100");
        Assertions.assertEquals(response.getDestination().getBalance(), BigDecimal.TEN);
    }

    @Test
    @DisplayName("Deposit into existing account")
    @Order(3)
    void depositIntoExistingAccount() throws Exception {
        EventDto eventDto = new EventDto();
        eventDto.setType("deposit");
        eventDto.setDestination("100");
        eventDto.setAmount(BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        TransactionDto response = objectMapper.readValue(contentAsString, TransactionDto.class);

        Assertions.assertEquals(response.getDestination().getId(), "100");
        Assertions.assertEquals(response.getDestination().getBalance(), BigDecimal.valueOf(20).setScale(0));
    }

    @Test
    @DisplayName("Get balance for existing account")
    @Order(4)
    void getBalanceForExistingAccount() throws Exception {

        MvcResult result = mockMvc.perform(get("/balance?account_id=100")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        Assertions.assertEquals(contentAsString, "20");
    }

    @Test
    @DisplayName("Withdraw from non-existing account")
    @Order(5)
    void withdrawFromNonExistingAccount() throws Exception{
        EventDto eventDto = new EventDto();
        eventDto.setType("withdraw");
        eventDto.setDestination("200");
        eventDto.setAmount(BigDecimal.TEN);

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isNotFound())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        Assertions.assertEquals(contentAsString, "0");
    }

    @Test
    @DisplayName("Withdraw from existing account")
    @Order(6)
    void withdrawFromExistingAccount() throws Exception {
        EventDto eventDto = new EventDto();
        eventDto.setType("withdraw");
        eventDto.setOrigin("100");
        eventDto.setAmount(BigDecimal.valueOf(5));

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        TransactionDto response = objectMapper.readValue(contentAsString, TransactionDto.class);

        Assertions.assertEquals(response.getOrigin().getId(), "100");
        Assertions.assertEquals(response.getOrigin().getBalance(), BigDecimal.valueOf(15).setScale(0));
    }

    @Test
    @DisplayName("Withdraw from existing account and non-existing balance")
    @Order(7)
    void withdrawFromExistingAccountAndNonExistingBalance() throws Exception {
        EventDto eventDto = new EventDto();
        eventDto.setType("withdraw");
        eventDto.setOrigin("100");
        eventDto.setAmount(BigDecimal.valueOf(20));

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isNotFound())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        Assertions.assertEquals(contentAsString, "0");
    }

    @Test
    @DisplayName("Transfer from existing account")
    @Order(8)
    void transferFromExistingAccount () throws Exception {
        EventDto eventDto = new EventDto();
        eventDto.setType("transfer");
        eventDto.setOrigin("100");
        eventDto.setDestination("300");
        eventDto.setAmount(BigDecimal.valueOf(15));

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        TransactionDto response = objectMapper.readValue(contentAsString, TransactionDto.class);

        Assertions.assertEquals(response.getOrigin().getId(), "100");
        Assertions.assertEquals(response.getOrigin().getBalance(), BigDecimal.ZERO);

        Assertions.assertEquals(response.getDestination().getId(), "300");
        Assertions.assertEquals(response.getDestination().getBalance(), BigDecimal.valueOf(15).setScale(0));
    }

    @Test
    @DisplayName("Transfer from non-existing account")
    @Order(9)
    void transferFromNonExistingAccount() throws Exception {
        EventDto eventDto = new EventDto();
        eventDto.setType("transfer");
        eventDto.setOrigin("200");
        eventDto.setDestination("300");
        eventDto.setAmount(BigDecimal.valueOf(15));

        MvcResult result = mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isNotFound())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertEquals(contentAsString, "0");
    }

}