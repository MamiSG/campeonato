package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Time {
    private String nome;
    private int pontos;

    public Time(String nome) {
        this.nome = nome;
        this.pontos = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }
}

class Campeonato {
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private List<Time> times;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Campeonato(String nome, Date dataInicio, Date dataFim) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.times = new ArrayList<>();
    }

    public void adicionarTime(Time time) {
        times.add(time);
    }

    public void exibirClassificacao() {
        times.sort(Comparator.comparingInt(Time::getPontos).reversed());
        System.out.println("Classificação do Campeonato " + nome + " (" + sdf.format(dataInicio) + " a " + sdf.format(dataFim) + "):");
        for (int i = 0; i < times.size(); i++) {
            System.out.println((i + 1) + ". " + times.get(i).getNome() + " - " + times.get(i).getPontos() + " pontos");
        }
    }
}

public class GerenciadorCampeonatos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Time> times = new ArrayList<>();
        List<Campeonato> campeonatos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Registro de Times:");
        while (true) {
            System.out.print("Digite o nome do time (ou 'sair' para finalizar): ");
            String nomeTime = scanner.nextLine();
            if (nomeTime.equalsIgnoreCase("sair")) {
                break;
            }
            times.add(new Time(nomeTime));
        }

        System.out.println("\nRegistro de Campeonatos:");
        while (true) {
            System.out.print("Digite o nome do campeonato (ou 'sair' para finalizar): ");
            String nomeCampeonato = scanner.nextLine();
            if (nomeCampeonato.equalsIgnoreCase("sair")) {
                break;
            }

            Date dataInicio = null, dataFim = null;
            try {
                System.out.print("Digite a data de início (yyyy-MM-dd): ");
                dataInicio = sdf.parse(scanner.nextLine());

                System.out.print("Digite a data de fim (yyyy-MM-dd): ");
                dataFim = sdf.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println("Formato de data inválido! Tente novamente.");
                continue;
            }

            Campeonato campeonato = new Campeonato(nomeCampeonato, dataInicio, dataFim);
            System.out.println("Adicione times ao campeonato:");

            for (Time time : times) {
                System.out.print("O time " + time.getNome() + " está no campeonato? (s/n): ");
                String resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("s")) {
                    System.out.print("Digite os pontos do time " + time.getNome() + ": ");
                    int pontos = scanner.nextInt();
                    scanner.nextLine();
                    time.adicionarPontos(pontos);
                    campeonato.adicionarTime(time);
                }
            }
            campeonatos.add(campeonato);
        }

        System.out.println("\nClassificações Finais:");
        for (Campeonato campeonato : campeonatos) {
            campeonato.exibirClassificacao();
        }
        scanner.close();
    }
}
