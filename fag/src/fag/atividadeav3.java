package fag;


	import java.util.*;

	
	enum Tamanho {
	    PEQUENO, MEDIO, GRANDE;
	}

	class Vaga {
	    private int numero;
	    private Tamanho tamanho;
	    private boolean disponivel;

	    public Vaga(int numero, Tamanho tamanho) {
	        this.numero = numero;
	        this.tamanho = tamanho;
	        this.disponivel = true;
	    }

	    public int getNumero() {
	        return numero;
	    }

	    public Tamanho getTamanho() {
	        return tamanho;
	    }

	    public boolean isDisponivel() {
	        return disponivel;
	    }

	    public void ocupar() {
	        this.disponivel = false;
	    }

	    public void liberar() {
	        this.disponivel = true;
	    }
	}

	class Veiculo {
	    private String placa;
	    private String modelo;
	    private Tamanho tamanho;
	    private long horaEntrada;
	    private long horaSaida;

	    public Veiculo(String placa, String modelo, Tamanho tamanho) {
	        this.placa = placa;
	        this.modelo = modelo;
	        this.tamanho = tamanho;
	    }

	    public String getPlaca() {
	        return placa;
	    }

	    public Tamanho getTamanho() {
	        return tamanho;
	    }

	    public void setHoraEntrada(long horaEntrada) {
	        this.horaEntrada = horaEntrada;
	    }

	    public void setHoraSaida(long horaSaida) {
	        this.horaSaida = horaSaida;
	    }

	    public long getTempoPermanencia() {
	        return horaSaida - horaEntrada;
	    }

	  
	    public double calcularValor() {
	        long tempoEmMilissegundos = getTempoPermanencia();
	        long tempoEmHoras = (tempoEmMilissegundos + (1000 * 60 * 60 - 1)) / (1000 * 60 * 60); 

	        if (tempoEmHoras <= 1) return 5.0;
	        else if (tempoEmHoras <= 3) return 10.0;
	        else return 15.0;
	    }
	}

	class Estacionamento {
	    private List<Vaga> vagas;
	    private List<Veiculo> veiculos;

	    public Estacionamento() {
	        vagas = new ArrayList<>();
	        veiculos = new ArrayList<>();
	    }

	    public void adicionarVaga(Vaga vaga) {
	        vagas.add(vaga);
	    }

	    public boolean entradaVeiculo(Veiculo veiculo, long horaEntrada) {
	        for (Vaga vaga : vagas) {
	            if (vaga.isDisponivel() && vaga.getTamanho().ordinal() >= veiculo.getTamanho().ordinal()) {
	                vaga.ocupar();
	                veiculo.setHoraEntrada(horaEntrada);
	                veiculos.add(veiculo);
	                return true;
	            }
	        }
	        return false;
	    }

	    public double saidaVeiculo(String placa, long horaSaida) {
	        Veiculo veiculoSaida = null;
	        for (Veiculo veiculo : veiculos) {
	            if (veiculo.getPlaca().equals(placa)) {
	                veiculoSaida = veiculo;
	                break;
	            }
	        }

	        if (veiculoSaida != null) {
	            veiculoSaida.setHoraSaida(horaSaida);
	            double valor = veiculoSaida.calcularValor();
	            veiculos.remove(veiculoSaida);
	            liberarVaga(veiculoSaida);
	            return valor;
	        }
	        return 0;
	    }

	    private void liberarVaga(Veiculo veiculo) {
	        for (Vaga vaga : vagas) {
	            if (!vaga.isDisponivel() && vaga.getTamanho() == veiculo.getTamanho()) {
	                vaga.liberar();
	                break;
	            }
	        }
	    }

	    public List<String> relatorioVagasOcupadas() {
	        List<String> relatorio = new ArrayList<>();
	        for (Vaga vaga : vagas) {
	            if (!vaga.isDisponivel()) {
	                relatorio.add("Vaga " + vaga.getNumero() + " - " + vaga.getTamanho());
	            }
	        }
	        return relatorio;
	    }

	    public void relatorioHistorico() {
	        for (Veiculo veiculo : veiculos) {
	            System.out.println("Placa: " + veiculo.getPlaca() + 
	                               ", Tempo de permanência: " + (veiculo.getTempoPermanencia() / (1000 * 60 * 60)) + " horas" + 
	                               ", Valor pago: R$ " + veiculo.calcularValor());
	        }
	    }
	}

	public class atividadeav3 {
	    public static void main(String[] args) {
	        Estacionamento estacionamento = new Estacionamento();
	        estacionamento.adicionarVaga(new Vaga(1, Tamanho.PEQUENO));
	        estacionamento.adicionarVaga(new Vaga(2, Tamanho.MEDIO));
	        estacionamento.adicionarVaga(new Vaga(3, Tamanho.GRANDE));

	        try (Scanner scanner = new Scanner(System.in)) {
	            while (true) {
	                System.out.println("1. Entrada de veículo");
	                System.out.println("2. Saída de veículo");
	                System.out.println("3. Relatório de vagas ocupadas");
	                System.out.println("4. Histórico de veículos");
	                System.out.println("0. Sair");
	                int opcao = scanner.nextInt();
	                scanner.nextLine(); 

	                switch (opcao) {
	                    case 1:
	                        System.out.print("Placa do veículo: ");
	                        String placa = scanner.nextLine();
	                        System.out.print("Modelo do veículo: ");
	                        String modelo = scanner.nextLine();
	                        System.out.print("Tamanho do veículo (PEQUENO, MEDIO, GRANDE): ");
	                        Tamanho tamanho = Tamanho.valueOf(scanner.nextLine().toUpperCase());

	                        Veiculo veiculo = new Veiculo(placa, modelo, tamanho);

	                 
	                        System.out.print("Informe a hora de entrada (HH:mm): ");
	                        String horaEntradaStr = scanner.nextLine();
	                        String[] horaEntradaParts = horaEntradaStr.split(":");
	                        int horaEntrada = Integer.parseInt(horaEntradaParts[0]);
	                        int minutoEntrada = Integer.parseInt(horaEntradaParts[1]);

	                        Calendar calendarEntrada = Calendar.getInstance();
	                        calendarEntrada.set(Calendar.HOUR_OF_DAY, horaEntrada);
	                        calendarEntrada.set(Calendar.MINUTE, minutoEntrada);
	                        long horaEntradaMillis = calendarEntrada.getTimeInMillis();

	                        if (estacionamento.entradaVeiculo(veiculo, horaEntradaMillis)) {
	                            System.out.println("Veículo estacionado com sucesso!");
	                        } else {
	                            System.out.println("Não há vagas disponíveis para este veículo.");
	                        }
	                        break;

	                    case 2:
	                        System.out.print("Placa do veículo: ");
	                        String placaSaida = scanner.nextLine();

	                        
	                        System.out.print("Informe a hora de saída (HH:mm): ");
	                        String horaSaidaStr = scanner.nextLine();
	                        String[] horaSaidaParts = horaSaidaStr.split(":");
	                        int horaSaida = Integer.parseInt(horaSaidaParts[0]);
	                        int minutoSaida = Integer.parseInt(horaSaidaParts[1]);

	                        Calendar calendarSaida = Calendar.getInstance();
	                        calendarSaida.set(Calendar.HOUR_OF_DAY, horaSaida);
	                       
	                        long horaSaidaMillis = calendarSaida.getTimeInMillis();

	                        double valorPago = estacionamento.saidaVeiculo(placaSaida, horaSaidaMillis);
	                        if (valorPago > 0) {
	                            System.out.println("Saída registrada. Valor a pagar: R$ " + valorPago);
	                        } else {
	                            System.out.println("Veículo não encontrado.");
	                        }
	                        break;

	                    case 3:
	                        List<String> relatorioVagas = estacionamento.relatorioVagasOcupadas();
	                        System.out.println("Vagas ocupadas:");
	                        for (String s : relatorioVagas) {
	                            System.out.println(s);
	                        }
	                        break;

	                    case 4:
	                        estacionamento.relatorioHistorico();
	                        break;

	                    case 0:
	                        System.out.println("Saindo...");
	                        return;

	                    default:
	                        System.out.println("Opção inválida. Tente novamente.");
	                }
	            }
	        }
	    }
	}
