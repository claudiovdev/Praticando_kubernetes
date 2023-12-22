package com.projeto.kubernetes.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("kubernet")
public class KuberController {

    private final ResourceLoader resourceLoader;

    public KuberController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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
        String caminhoDoArquivo = "/home/app/src/main/resources/arquivo/mensagem.txt";

        try {
            // Crie um objeto File com o caminho do arquivo
            File arquivo = new File(caminhoDoArquivo);

            // Use FileReader para ler caracteres do arquivo
            FileReader leitorArquivo = new FileReader(arquivo);

            // Ou use BufferedReader para leitura eficiente de linhas
            BufferedReader leitorBuffer = new BufferedReader(leitorArquivo);

            StringBuilder conteudo = new StringBuilder();
            String linha;

            // Leia e acumule cada linha do arquivo
            while ((linha = leitorBuffer.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }

            // Feche os recursos após a leitura
            leitorBuffer.close();
            leitorArquivo.close();

            // Retorna o conteúdo como resposta
            return ResponseEntity.ok(conteudo.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao ler o arquivo.");
        }
    }



}
