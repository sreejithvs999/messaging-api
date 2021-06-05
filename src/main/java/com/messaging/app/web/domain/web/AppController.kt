package com.messaging.app.web.domain.web

import com.messaging.app.repository.data.User
import com.messaging.app.services.MessageDTO
import com.messaging.app.services.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class AppController {

    @Autowired
    private lateinit var messageService: MessageService

    @RequestMapping(method = [RequestMethod.POST], value = ["/user"])
    fun createUser(@RequestParam("user_id") userId: String): User {
       return messageService.createUser(userId);
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/message"])
    fun createMessage(@RequestBody message: MessageDTO): MessageDTO {
        return messageService.createMessage(message);
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/message"], params = ["receiver_id"])
    fun getUserReceivedMessages(@RequestParam("receiver_id") receiverId: String): List<MessageDTO> {
        return messageService.getUserReceivedMessages(receiverId);
    }


    @RequestMapping(method = [RequestMethod.GET], value = ["/message"], params = ["sender_id"])
    fun getUserSentMessages(@RequestParam("sender_id") senderId: String): List<MessageDTO> {
        return messageService.getUserSentMessages(senderId);
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/message"], params = ["receiver_id", "sender_id"])
    fun getUserReceivedMessagesFromSender(@RequestParam("receiver_id") receiverId: String,
                                          @RequestParam("sender_id") senderId: String): List<MessageDTO> {

        return messageService.getUserReceivedMessagesFromSender(receiverId, senderId);
    }

}