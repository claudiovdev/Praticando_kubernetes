package com.projeto.kubernetes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("meu-ip")
public class KuberController {

    @GetMapping
    public String retornandoIp() {
        try {
            return obterEnderecoIPPublico();
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Erro ao obter o endereço IP", e);
        }
    }

    private static String obterEnderecoIPPublico() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream()))) {
            return "meu ip atual é: " + br.readLine().trim();
        }
    }

}
