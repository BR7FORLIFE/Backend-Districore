package com.tecno_comfenalco.pa.application.orders.usecases;

import com.tecno_comfenalco.pa.application.orders.command.actions.ChangeStatusOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.CreateOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetAllOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetOrderByIdCommand;
import com.tecno_comfenalco.pa.application.orders.command.response.ChangeStatusOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.CreateOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.GetAllOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.GetOrderByIdCommandResult;

public interface OrderUsecase {
    CreateOrderCommandResult createOrder(CreateOrderCommand cmd);

    GetOrderByIdCommandResult getOrderById(GetOrderByIdCommand cmd);

    GetAllOrderCommandResult getAllOrders(GetAllOrderCommand cmd);

    ChangeStatusOrderCommandResult changeStatusOrder(ChangeStatusOrderCommand cmd);
}
