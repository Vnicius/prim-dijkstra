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
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(name));
			int tam = Integer.parseInt(br.readLine().split("[ ,\t]")[0]);		//Quantidade de v�rtices
			int[][] matriz = new int[tam][tam];		//Matriz de pesos
			Vertex[] v = new Vertex[tam];
			String nums = "";
			int k = 0;
			
			for(int i = 0; i < tam; i++){		//Inicializa os v�rtices
				v[i]= new Vertex(i,Integer.MAX_VALUE,null);
			}
			
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
						if(valor != 0){
							v[i].adj.add(v[j]);
							v[j].adj.add(v[i]);
						}
						k++;
					}else{
						j--;
						k++;
					}
				}
			}
			
			System.out.print("V�rtice inicial(0 - "+(tam-1)+"): ");
			int ini = ler.nextInt();
			
			mstPrim(tam,v,matriz,ini);
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void mstPrim(int numV, Vertex[] v,int[][] matrizPesos, int inicial){
		Vertex[] res = new Vertex[numV];	//Array final com os resultados
		Vertex u = null;		//Vari�vel auxiliar
		int size = 0;
		
		v[inicial].valor = 0;		//Atribui zero ao v�rtice escolhido como inicial

		ArrayList<Vertex> q = buildMinHeap(v.clone());		//Controi a primeira �rvore heap
		
		while ((size = q.size()) != 0){
			u = q.remove(0);		//Remove o primeiro vertice da lista
			
			for(int i = 0; i < u.adj.size();i++){
				//Confere se o v�rtice adjacente est� no conjunto Q e se o peso da aresta � menor que o valor atual do v�rtice
				if((q.contains(u.adj.get(i))) && (matrizPesos[u.id][u.adj.get(i).id] < (u.adj.get(i).valor))){
					u.adj.get(i).pai = u;		//Atribui o v�rtice retirado como o novo pai
					u.adj.get(i).valor = matrizPesos[u.id][u.adj.get(i).id];	//Modifica o valor do v�rtice pelo peso da aresta correspondente
				}
			}
			
			q = buildMinHeap(q.toArray(new Vertex[q.size()]));
			
			res[numV - size] = u;		//Guarda os v�rtices j� acessados
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
	ArrayList<Vertex> adj;
	
	public Vertex(int id, int valor, Vertex pai){
		this.id = id;
		this.valor = valor;
		this.pai = pai;
		this.adj = new ArrayList<Vertex>();
	}
}
