package br.com.alura.school.course;

public class Report {
    private String email;
    private int quantidade_matriculas;

    public Report(String email, int quantidade_matriculas) {
        this.email = email;
        this.quantidade_matriculas = quantidade_matriculas;
    }

    public String getEmail() {
        return email;
    }

    public int getQuantidade_matriculas() {
        return quantidade_matriculas;
    }
}
