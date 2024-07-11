package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Produto;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Produto> lista = new ArrayList<>();
		
		System.out.println("Entre com o caminho do arquivo: ");
		String srcFileStr = sc.nextLine();
		
		File sourceFile = new File(srcFileStr);
		String srcFolderStr = sourceFile.getParent();
		
		boolean success = new File(srcFolderStr + "\\out").mkdir();
		
		String targetFileStr = srcFolderStr + "\\out\\summary.csv";
		
		// Lendo e adicionando à uma lista, as informações do arquivo csv na area de trabalho
		
		try (BufferedReader br = new BufferedReader(new FileReader(srcFileStr))) {
			
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				
				String[] fields = itemCsv.split(",");
				String nome = fields[0];
				double preco = Double.parseDouble(fields[1]);
				int quantidade = Integer.parseInt(fields[2]);
				
				lista.add(new Produto(nome, preco, quantidade));
				
				itemCsv = br.readLine();
			}
			
			//Gravando as informações dentro de um novo arquivo
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
				
				for(Produto item : lista) {
					bw.write(item.getNome() + ", " + String.format("%.2f", item.total()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " - Criado com sucesso!");
			}
			catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
				
		sc.close();
	}

}
