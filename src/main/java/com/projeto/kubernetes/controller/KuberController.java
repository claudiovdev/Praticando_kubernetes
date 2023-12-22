package com.projeto.kubernetes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("kubernet")
public class KuberController {

    @GetMapping("/meu-ip")
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

    @GetMapping("/ler")
    public ResponseEntity<String> lerArquivo() {
        try {

            String caminhoArquivo = "/app/src/main/resources/arquivo/mensagem.txt";

            // Carrega o arquivo da pasta resources
            Resource resource = new ClassPathResource(caminhoArquivo);
            Path path = Path.of(resource.getURI());

            // Lê o conteúdo do arquivo
            String conteudo = Files.readString(path);

            // Retorna o conteúdo como resposta
            return ResponseEntity.ok(conteudo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao ler o arquivo.");
        }
    }



}
