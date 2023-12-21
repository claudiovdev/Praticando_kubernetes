package com.projeto.kubernetes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("meu-ip")
public class KuberController {

    @GetMapping
    public String retornandoIp() {
        try {
            return obterEnderecoIPInstancia();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Erro ao obter o endereço IP da instância", e);
        }
    }

    private static String obterEnderecoIPInstancia() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return "O endereço IP da instância é: " + ip;
    }

}
