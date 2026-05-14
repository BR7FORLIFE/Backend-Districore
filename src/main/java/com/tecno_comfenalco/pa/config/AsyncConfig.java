package com.tecno_comfenalco.pa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync // aca permitimos la ejecucion en hilos separados (asincronia)
public class AsyncConfig {

}
