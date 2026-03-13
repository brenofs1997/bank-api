package com.api.bank

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

        @Autowired lateinit var mockMvc: MockMvc
        @Autowired lateinit var accountRepository: AccountRepository
        @Test
        fun `test find all`(){
                accountRepository.save(Account(name = "test", document = "123", phone = "99887878"))

                mockMvc.perform (
                        MockMvcRequestBuilders.get("/accounts"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect (MockMvcResultMatchers.jsonPath("\$").isArray)
                        .andExpect (MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
                        .andExpect (MockMvcResultMatchers.jsonPath("\$[0].name").isString)
                        .andExpect (MockMvcResultMatchers.jsonPath("\$[0].document").isString)
                        .andExpect (MockMvcResultMatchers.jsonPath("\$[0].phone").isString)
                        .andDo{ MockMvcResultHandlers.print() }

        }

        @Test
        fun `test find by id`(){
            val account = accountRepository.save(Account(name = "test", document = "123", phone = "99887878"))

                mockMvc.perform (
                        MockMvcRequestBuilders.get("/accounts/${account.id}"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect (MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
                        .andExpect (MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                        .andExpect (MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                        .andExpect (MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                        .andDo{ MockMvcResultHandlers.print() }

        }
}