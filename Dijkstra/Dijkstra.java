import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Dijkstra {
	
	public static void dijkstra(int[][] matrixAdj, Vertex[] vertices){
		Vertex u = null;
		Vertex[] res = new Vertex[matrixAdj.length]; 
		
		vertices[0].setValor(0); //Atribui zero ao vertice inicial
		
		ArrayList<Vertex> q = buildMinHeap(vertices.clone());

		while (q.size() != 0){ //executa até não ter mais elementos
			u = q.remove(0); //tira o menor elemento e armazena em u

			//percorre os demais 
			for (Vertex v : u.getListAdj()) {
				/*relaxamento*/
				
				if (q.contains(v) && v.getValor() > u.getValor() + matrixAdj[u.getId()][v.getId()]){
					
					v.setValor(u.getValor() + matrixAdj[u.getId()][v.getId()]);
					v.setPai(u);
				}
			}

			q = buildMinHeap(q.toArray(new Vertex[q.size()])); 
			res[matrixAdj.length - (q.size()+1)] = u; 
		}

		

		
		System.out.println("Menor caminho do 0 para "+(res.length-1)+" é: ");
		String resposta = "";
		
		for (Vertex v : res) {
			if (v.getId() == res.length-1){
				u = v;
				break;
			}
		}

		resposta = u.getId()+"";
		while (u.getPai() != null){			
			resposta = u.getPai().getId()+" - "+resposta;
			u = u.getPai();
		}		
		System.out.println(resposta);
	}

	

	private static ArrayList<Vertex> buildMinHeap(Vertex[] vet){
		
		for (int i = (vet.length/2)-1; i >= 0; i--)
			minHeapfy(vet,vet.length,i);
		
		ArrayList<Vertex> ret = new ArrayList<Vertex>();
		
		for (int i = 0; i < vet.length; i++)
			ret.add(vet[i]);
		
		return ret;
	}
	
	private static Vertex[] minHeapfy(Vertex[] vet, int n, int index){
		int min = index, left = 2 * index, right = 2 * index + 1;
		
		if ((left <= n - 1) && (vet[left].getValor() < vet[min].getValor()))
			min = left;
		
		if((right <= n-1) && (vet[right].getValor() < vet[min].getValor()))
			min = right;
		
		if(min != index){
			Vertex aux = vet[index];
			vet[index] = vet[min];
			vet[min] = aux;
			
			return minHeapfy(vet,n,min);
		}
		
		return vet;
	}

	
	public static void main(String[] args) {
		
	    String name = "/home/maheus/Área de Trabalho/Leo/APA/dij10.txt";        
		int n = 0;
		String line;
		try {
			BufferedReader ler = new BufferedReader(new FileReader(name));
			n = Integer.parseInt(ler.readLine().split("[ ,\t]")[0]);
			int matrix[][] = new int[n][n];
			Vertex vertices[] = new Vertex[n];			

			String nums = "";
			int k = 0;
			
			while (ler.ready()){
				nums += ler.readLine()+" ";
			}

			String[] numbers = nums.split("[\td+, d+, \rd+]");

			//Todos os vertices são inicializados com infinito
			for (int i = 0; i < n; i++)
				vertices[i] = new Vertex(i, Integer.MAX_VALUE, null);						

			for (int i = 0; i < n; i++){				
				for (int j = i + 1; j < n; j++){
					if (numbers[k].matches("[-+]?\\d*\\.?\\d+")){
						int valor = Integer.parseInt(numbers[k]);
						matrix[i][j] = valor;						
						matrix[j][i] = valor;

						if (valor != 0){
							vertices[i].getListAdj().add(vertices[j]);
							vertices[j].getListAdj().add(vertices[i]);							
						}
						k++;
					}
					else{
						j--;
						k++;
					}
				}
			}

			ler.close();
			dijkstra(matrix, vertices);
						
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
class Vertex {
	private int id;
	private int valor;
	private Vertex pai;
	private ArrayList<Vertex> listAdj;
	
	public Vertex(int id, int valor, Vertex pai){
		this.id = id;
		this.valor = valor;
		this.pai = pai;
		this.listAdj = new ArrayList<Vertex>();
	}

	public int getId(){
		return this.id;
	}
	public int getValor(){
		return this.valor;
	}
	public Vertex getPai(){
		return this.pai;
	}
	public ArrayList<Vertex> getListAdj(){
		return this.listAdj;
	}

	public void setId(int id){
		this.id = id;
	}
	public void setValor(int valor){
		this.valor = valor;
	}
	public void setPai(Vertex pai){
		this.pai = pai;
	}
	
}
