package com.api.bookstoremanager.users.utils;

import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.entity.User;

public class MessageDTOUtils {

  public static MessageDTO creationMessage(User createdUser) {
    return returnMessage(createdUser, "created");
  }

  public static MessageDTO updatedMessage(User updatedUser) {
    return returnMessage(updatedUser, "updated");
  }

  public static MessageDTO returnMessage(User user, String action) {
    var updatedUsername = user.getUsername();
    var updatedUserId = user.getId();
    var createdUserMessage = String.format("User %s with ID %s successfully %s", updatedUsername, updatedUserId, action);
    return MessageDTO.builder()
            .message(createdUserMessage)
            .build();
  }
}
