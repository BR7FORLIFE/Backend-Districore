package com.tecno_comfenalco.pa.application.auth.command.response;

import com.tecno_comfenalco.pa.application.auth.draft.UserDraft;

public record EditUserCommandResult(String message, UserDraft userDraft) {

}
