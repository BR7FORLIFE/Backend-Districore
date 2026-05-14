package com.tecno_comfenalco.pa.application.auth.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.application.auth.draft.UserDraft;

public record ListAllUsersCommandResult(String message, List<UserDraft> users) {

}
