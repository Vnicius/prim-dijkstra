import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class dijkstra{
	public static void main(String[] args){
		//System.out.print("Digite o caminho do arquivo: ");
		//Scanner ler = new Scanner(System.in);
        //String name = ler.nextLine();
        String name = "/home/maheus/Área de Trabalho/Leo/APA/dij10.txt";
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(name));
			int tam = Integer.parseInt(br.readLine().split("[ ,\t]")[0]);		//Quantidade de vértices
			int[][] matriz = new int[tam][tam];		//Matriz de pesos
			String nums = "";
			int k = 0;
			
			while(br.ready()){
				nums += br.readLine()+" ";
			}
			
			String[] numeros = nums.split("[\td+, d+, \rd+]");
								
			
			for(int i = 0; i<tam;i++){
				for(int j = i+1; j<tam; j++){
					if(numeros[k].matches("[-+]?\\d*\\.?\\d+")){
						int valor = Integer.parseInt(numeros[k]);
						matriz[i][j] = valor;
						matriz[j][i] = valor;
						k++;
					}else{
						j--;
						k++;
					}
				}
			}
			
			
			//Print da matriz
			//for(int i = 0; i<tam;i++){
			//	for(int j = 0; j<tam; j++){
			//		System.out.print(matriz[i][j]+" ");
			//	}
			//	
			//	System.out.println("");
			//}
			
						
			
		caminhoMinimo(tam, matriz);	
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



public static void caminhoMinimo(int numV, int [][] matrizpesos){	
	int[] fixo = new int[numV];
	int[] dist = new int[numV];
	

	//Inicializa todos os vertices com infinito 
	//Inicializa também o flag de visitado
	for(int i = 0; i<numV;i++ ){
		fixo[i] = 0;
		dist[i] = (Integer.MAX_VALUE);;
	}
	//inicializa o vertice zero com 0
	dist[0] = 0;

	for(int j = numV; j > 0 ;j--){
		int node = -1;
		//Verifica se é a primeira visita ao vertice, caso positivo nó sera marcado como visitado
		// apenas se a distancia for menor do que a distancia atual
		for(int i=0; i<numV; i++){
			if((fixo[i] == 0) && (node == -1 || dist[i] < dist[node])){
				node = i;
			}
		}
		//marcar nó visitado
		fixo[node] = 1;
		
		//se a distancia for igual a infito, pare. 
		if(dist[node] == (Integer.MAX_VALUE)){
			break;
		}

		
		//Verifica e calcula a distancia para os vertices
		for(int i = 0; i<numV; i++){
			if(dist[i] > dist[node] + (matrizpesos[node][i])){
				dist[i] = dist[node] + (matrizpesos[node][i]);
			}

		}
		//System.out.print(" " + node +" ");
 
	}
	
		System.out.print("\n A menor distância do O para "+ numV + " é " + dist[numV-1] + "\n");
	
	}
}

