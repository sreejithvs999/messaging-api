package com.messaging.app.web.domain.web

import com.messaging.app.repository.data.User
import com.messaging.app.services.MessageDTO
import com.messaging.app.services.MessageService
import com.messaging.app.services.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class AppController {

    @Autowired
    private lateinit var messageService: MessageService

    @PostMapping(value = ["/user"], produces = ["application/json; enc"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: UserDTO): UserDTO {
        return messageService.createUser(user);
    }

    @GetMapping(value = ["/user"], params = ["nickName"])
    fun resolveUser(@RequestParam nickName: String): User {
        return messageService.resolveUser(nickName);
    }

    @PostMapping(value = ["/message"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createMessage(@RequestBody message: MessageDTO): MessageDTO {
        return messageService.createMessage(message);
    }

    @GetMapping(value = ["/message"], params = ["receiverId"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserReceivedMessages(@RequestParam receiverId: Int): List<MessageDTO> {
        return messageService.getUserReceivedMessages(receiverId);
    }


    @GetMapping(value = ["/message"], params = ["senderId"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserSentMessages(@RequestParam senderId: Int): List<MessageDTO> {
        return messageService.getUserSentMessages(senderId);
    }

    @GetMapping(value = ["/message"], params = ["receiverId", "senderId"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserReceivedMessagesFromSender(@RequestParam receiverId: Int, @RequestParam senderId: Int): List<MessageDTO> {
        return messageService.getUserReceivedMessagesFromSender(receiverId, senderId);
    }

}