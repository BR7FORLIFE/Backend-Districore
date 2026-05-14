package com.tecno_comfenalco.pa.application.auth.usecases;

import java.util.UUID;

import com.tecno_comfenalco.pa.application.auth.command.actions.EditUserCommand;
import com.tecno_comfenalco.pa.application.auth.command.actions.LoginUserCommand;
import com.tecno_comfenalco.pa.application.auth.command.actions.RegisterUserCommand;
import com.tecno_comfenalco.pa.application.auth.command.response.DisabledUserCommandResult;
import com.tecno_comfenalco.pa.application.auth.command.response.EditUserCommandResult;
import com.tecno_comfenalco.pa.application.auth.command.response.ListAllUsersCommandResult;
import com.tecno_comfenalco.pa.application.auth.command.response.LoginUserCommandResult;
import com.tecno_comfenalco.pa.application.auth.command.response.RegisterUserCommandResult;

public interface AuthenticationUseCase {
    RegisterUserCommandResult registerUser(RegisterUserCommand cmd);

    LoginUserCommandResult loginUser(LoginUserCommand cmd);

    DisabledUserCommandResult disabledUser(UUID userId);

    ListAllUsersCommandResult listUsers();

    EditUserCommandResult editUser(EditUserCommand cmd);

}
