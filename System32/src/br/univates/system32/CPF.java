package br.univates.system32;

import java.io.Serializable;
import java.util.ArrayList;

public class CPF implements Serializable{

	private String cpf;

	public boolean setCPF(String cpf) {
		boolean ok = false;

		if (cpf != null) {

			if (CPF.validaCPF(cpf)) {
				ok = true;
				cpf = cpf.replace(".", "");
				cpf = cpf.replace("-", "");
				this.cpf = cpf;
			}

		}

		return ok;

	}

	public boolean setCPF(long cpf) {

		boolean ok = false;

		String cpfString = String.valueOf(cpf);

		if (CPF.validaCPF(cpfString)) {
			ok = true;
			this.cpf = cpfString;
		}

		return ok;

	}

	public long getCPFNumbers() {
		return Long.parseLong(this.cpf);
	}

	public String getCPFString() {
		String cpfFull = "";
		for (int i = 0; i < cpf.length(); i++) {
			cpfFull += cpf.charAt(i);
			if (i == 2 || i == 5) {
				cpfFull += ".";
			} else if (i == 8) {
				cpfFull += "-";
			}
		}
		return cpfFull;
	}

	public static boolean validaCPF(String cpf) {

		boolean ok = true;
		if (cpf.isBlank() || cpf == null) {
			ok = false;
		} else {
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace(" ", "");

			try {
				long d = Long.parseLong(cpf);
			} catch (NumberFormatException nfe) {
				ok = false;
				return ok;
			}

			if (cpf.length() != 11) {
				ok = false;
			} else {

				ArrayList<Integer> aux = new ArrayList<Integer>();
				for (int i = 0; i < 11; i++) {
					aux.add(Integer.parseInt((cpf.charAt(i)) + ""));
				}

				int primDig = 0;
				for (int i = 10; i >= 2; i--) {
					primDig += (aux.get(10 - i) * i);
				}

				if (aux.get(9) != ((primDig * 10) % 11) || primDig == 0) {
					ok = false;
				} else {

					int segDig = 0;
					for (int i = 11; i >= 2; i--) {
						segDig += (aux.get(11 - i) * i);
					}
					if (aux.get(10) != ((segDig * 10) % 11)) {
						ok = false;
					}
				}
			}
		}

		return ok;

	}

}
