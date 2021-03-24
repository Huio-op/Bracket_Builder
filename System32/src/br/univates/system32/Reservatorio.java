package br.univates.system32;

public class Reservatorio {
	private int capacidadeMaxima;
	private double nivelAtual;
	private double totalConsumido;

	public Reservatorio(int capacidadeMax) {
		capacidadeMaxima = capacidadeMax;
		nivelAtual = 0;
		totalConsumido = 0;
	}

	public int getCapacidadeMaxima() {
		return capacidadeMaxima;
	}

	public double getNivelAtual() {
		return nivelAtual;
	}

	public double getTotalConsumido() {
		return totalConsumido;
	}

	public void encher() {
		nivelAtual = capacidadeMaxima;
	}

	public boolean encher(double quant) {
		boolean ok = false;
		if (nivelAtual + quant <= capacidadeMaxima) {
			nivelAtual += quant;
			ok = true;
		}
		return ok;
	}

	public boolean consumir(double quant) {
		boolean ok = false;
		if (nivelAtual >= quant) {
			nivelAtual -= quant;
			totalConsumido += quant;
			ok = true;
		}
		return ok;
	}

	public boolean temSuficiente(double quant) {
		boolean ok = false;
		if (nivelAtual >= quant) {
			ok = true;
		}
		return ok;
	}

	public void reinicializarConsumo() {
		totalConsumido = 0;
	}
}
