package test;

//Java program for the above approach

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class QuickSortMutliThreading extends RecursiveTask<Integer> {

	int start, end;
	int[] arr;	

	public static void main(String args[]) throws InterruptedException
	{

		double tiempoInicial1, tiempoFinal1;	
		double tiempoInicial, tiempoFinal;	
		int tam = 100;

		int[] arr = new int[tam];

		int[] arr2 = new int[tam];

		Random random = new Random();
		for (int i = 0; i < tam; i++) {
			arr[i] = random.nextInt(100);
		}

		arr2 = arr.clone();

		System.out.println("Target array is :");

		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" \n");
		}        
		
		System.out.println(" ");
		
		System.out.println(" ");

		tiempoInicial = System.nanoTime();

		Quicksort(arr,0, tam - 1);

		tiempoFinal = System.nanoTime()-tiempoInicial;

		for (int i = 0; i < tam; i++)
			System.out.print(arr[i] + " \n");

		System.out.println("\nEl Quick Sort secuencial,  demoro: "+ tiempoFinal/1000 +" nanoSegundos"); 

		System.out.println(" ");
		
		
		System.out.println(" ");

		tiempoInicial = System.nanoTime();

		Quicksort(arr,0, tam - 1);

		tiempoFinal = System.nanoTime()-tiempoInicial;

		for (int i = 0; i < tam; i++)
			System.out.print(arr[i] + " \n");

		System.out.println("\nEl Quick Sort secuencial con array ordenado,  tardo: "+ tiempoFinal/1000 +" nanoSegundos"); 

		System.out.println(" ");

		
		

		ForkJoinPool pool = ForkJoinPool.commonPool();			

		tiempoInicial1 = System.nanoTime(); 

		pool.invoke(new QuickSortMutliThreading(0, tam - 1, arr));

		tiempoFinal1 = System.nanoTime()-tiempoInicial1;

		for (int i = 0; i < tam; i++) {
			System.out.print(arr2[i] + " \n");
		}

		
		System.out.println("\nEl Quick Sort concurrente,  tardo: "+ tiempoFinal1/1000 +" nanoSegundos");
		
		
				

		tiempoInicial1 = System.nanoTime(); 

		pool.invoke(new QuickSortMutliThreading(0, tam - 1, arr));

		tiempoFinal1 = System.nanoTime()-tiempoInicial1;

		for (int i = 0; i < tam; i++) {
			System.out.print(arr[i] + " \n");
		}

		
		System.out.println("\nEl Quick Sort concurrentecon array ordenado,  tardo: "+ tiempoFinal1/1000 +" nanoSegundos");
		
	}


	public static void Quicksort(int numeros[], int izq, int der) throws InterruptedException
	{
		int pivote = numeros[izq];
		int i = izq; 
		int j = der; 
		int aux;

		while(i<j)
		{
			while (numeros[i] <= pivote && i < j) 
				i++;

			while (numeros[j] > pivote) 
				j--;   

			if (i<j) 
			{                                     
				aux = numeros[i];                  
				numeros[i]= numeros[j];
				numeros[j]=aux;
			}
		}

		numeros[izq] = numeros[j]; 
		numeros[j] = pivote;

		if (izq < j-1)
			Quicksort(numeros,izq,j-1);

		if (j+1 < der)
			Quicksort(numeros,j+1,der);
	}

	private int partition(int start, int end,int[] arr)
	{
		int i = start, j = end;

		int pivoted = new Random().nextInt(j - i)+ i;

		int t = arr[j];
		arr[j] = arr[pivoted];
		arr[pivoted] = t;
		j--;

		while (i <= j) {

			if (arr[i] <= arr[end]) {
				i++;
				continue;
			}

			if (arr[j] >= arr[end]) {
				j--;
				continue;
			}

			t = arr[j];
			arr[j] = arr[i];
			arr[i] = t;
			j--;
			i++;
		}

		t = arr[j + 1];
		arr[j + 1] = arr[end];
		arr[end] = t;
		return j + 1;
	}

	public QuickSortMutliThreading(int start,int end,int[]arr)
	{
		this.arr = arr;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute()	{

		if (start >= end)
			return null;

		int p = partition(start, end, arr);

		QuickSortMutliThreading left = new QuickSortMutliThreading(start,p - 1,arr);

		QuickSortMutliThreading right = new QuickSortMutliThreading(p + 1,end,arr);

		left.fork();
		right.compute();

		left.join();

		return null;
	}


}







