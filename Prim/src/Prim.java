import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Prim {
	public static void main(String[] args){
		System.out.print("Digite o caminho do arquivo: ");
		Scanner ler = new Scanner(System.in);
        String name = ler.nextLine();
        //name = "D:\\UFPB\\APA\\dij10.txt";
		
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
			
//			for(int i = 0; i < valores.length; i++){
//				if(val)
//					valores.add()
//			}
			
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
//			for(int i = 0; i<tam;i++){
//				for(int j = 0; j<tam; j++){
//					System.out.print(matriz[i][j]+" ");
//				}
//				
//				System.out.println("");
//			}
			
			System.out.print("Vértice inicial(0 - "+(tam-1)+"): ");
			int ini = ler.nextInt();
			
			mstPrim(tam,matriz,ini);
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void mstPrim(int numV, int[][] matrizPesos, int inicial){
		Vertex[] v = new Vertex[numV];		//Array com os vértices
		Vertex[] res = new Vertex[numV];	//Array final com os resultados
		Vertex u = null;		//Variável auxiliar
		int size = 0;
		
		for(int i = 0; i < numV; i++){		//Inicializa os vértices
			v[i]= new Vertex(i,Integer.MAX_VALUE,null);
		}
		
		v[inicial].valor = 0;		//Atribui zero ao vértice escolhido como inicial

		ArrayList<Vertex> q = buildMinHeap(v.clone());		//Controi a primeira árvore heap
		
		while ((size = q.size()) != 0){
			u = q.remove(0);		//Remove o primeiro vertice da lista
			
			for(int i = 0; i < q.size();i++){
				//Vê se o vértice retirado possuir ligacação com os restantes da lista e se o peso da aresta é menor que o valor atual do vértice
				if((matrizPesos[u.id][q.get(i).id] != 0) && (matrizPesos[u.id][q.get(i).id] < q.get(i).valor)){
					q.get(i).pai = u;		//Atribui o vértice retirado como o novo pai
					q.get(i).valor = matrizPesos[u.id][q.get(i).id];	//Modifica o valor do vértice pelo peso da aresta correspondente
				}
			}
			
			q = buildMinHeap(q.toArray(new Vertex[q.size()]));
			
			//q = new ArrayList<Vertex>(Arrays.asList(minHeapfy(q.toArray(new Vertex[q.size()]),q.size(),0)));	//
			
//			for(int i = 0; i < q.size(); i++){
//				
//				System.out.println(q.get(i).id+"   "+q.get(i).valor);
//			}
			
			//System.out.println("");
			
			res[numV - size] = u;		//Guarda os vértices já acessados
		}
		
		int total = 0;
		
		for(int i = 0; i < res.length; i++){
			if(res[i].pai!= null)
				System.out.println("ID: "+res[i].id+" Pai: "+res[i].pai.id+" Valor: "+res[i].valor);
			else
				System.out.println("ID: "+res[i].id+" Pai: "+res[i].pai+" Valor: "+res[i].valor);
			
			total += res[i].valor;
		}
		
		System.out.println("\nValor total: "+total);
		
	}
	
	public static ArrayList<Vertex> buildMinHeap(Vertex[] vet){
		
		for(int i = (vet.length/2)-1; i >= 0; i--){
			minHeapfy(vet,vet.length,i);
		}
		
		ArrayList<Vertex> ret = new ArrayList<Vertex>();
		
		for(int i = 0; i < vet.length; i++){
			ret.add(vet[i]);
		}
		
		return ret;
	}
	
	public static Vertex[] minHeapfy(Vertex[] vet, int tam ,int index){
		int menor =  index, left = 2*index, right = 2*index + 1;
		
		if((left <= tam-1) && (vet[left].valor < vet[menor].valor)){
			menor = left;
		}
		
		if((right <= tam-1) && (vet[right].valor < vet[menor].valor)){
			menor = right;
		}
		
		if(menor != index){
			Vertex aux = vet[index];
			vet[index] = vet[menor];
			vet[menor] = aux;
			
			return minHeapfy(vet,tam,menor);
		}
		
		return vet;
	}
}

class Vertex{
	int id;
	int valor;
	Vertex pai;
	
	public Vertex(int id, int valor, Vertex pai){
		this.id = id;
		this.valor = valor;
		this.pai = pai;
	}
}
