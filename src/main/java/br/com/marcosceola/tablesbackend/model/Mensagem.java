package br.com.marcosceola.tablesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mensagem {
    private String usuario;
    private String mensagem;
    private String mesa;
}
