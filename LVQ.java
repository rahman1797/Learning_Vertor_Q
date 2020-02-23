/*
* @author erikwii a.k.a Erik Santiago
* this code based on https://sutikno.blog.undip.ac.id/files/2013/06/5.-LVQ-algoritma.pdf
*/


import java.util.*;

class LVQ {
	private double[][] weight;
	private int[] wClass;
	private int[][] data;
	private int[] target;
	private int epoch;
	private double eps;
	private double alpha;

	public LVQ(){}

	public LVQ(double[][] _weight, int[] _wClass, int[][] _data, int[] _target, int _epoch, double _eps, double _alpha){
		this.weight = _weight;
		this.wClass = _wClass;
		this.data = _data;
		this.target = _target;
		this.epoch = _epoch;
		this.eps = _eps;
		this.alpha = _alpha;
	}

	void setWeight(double[][] _weight){
		this.weight = _weight;
	}

	double[][] getWeight(){
		return this.weight;
	}

	void setData(int[][] _data){
		this.data = _data;
	}

	int[][] getData(){
		return this.data;
	}

	void setEpoch(int _epoch){
		this.epoch = _epoch;
	}

	int getEpoch(){
		return this.epoch;
	}

	void setEps(double _eps){
		this.eps = _eps;
	}

	double getEps(){
		return this.eps;
	}

	void setAlpha(double _alpha){
		this.alpha = _alpha;
	}

	double getAlpha(){
		return this.alpha;
	}

	void learn(){
		// length/determinant checker init 
		double[] detCheck = new double[weight.length];
		for (int i = 0; i < weight.length; i++) {
			detCheck[i] = 0;
		}

		// do learning
		for (int e = 0; e < this.epoch; e++) {
			System.out.println("Epoch "+(e+1)+":");
			for (int i = 0; i < this.data.length; i++) {
				// get sum of the dataVector[i] for all data
				for (int j = 0; j < this.data[i].length; j++) {
					for (int n = 0; n < weight.length; n++) {
						detCheck[n] = detCheck[n] + Math.pow(data[i][j]-weight[n][j],2);
					}
				}

				// do sqrt for all sum to get real result of
				// lenght/determinant checker
				for (int n = 0; n < detCheck.length; n++) {
					detCheck[n] = Math.sqrt(detCheck[n]);
				}

				// looking for minimum length of 
				// length/determinant checker to dataVector[i]
				double min = Double.MAX_VALUE;
				int detIndex = 0;
				for (int n = 0; n < detCheck.length; n++) {
					if (min > detCheck[n]) {
						min = detCheck[n];
						detIndex = n;
					}
				}
				System.out.println("Target data "+ (i+1) +": "+target[i]);
				System.out.println("Data "+ (i+1) +" Nearest to " + (detIndex+1));

				if (target[i] == (detIndex+1)) {
					// Update Weight based on minimum length to the data
					for (int j = 0; j < this.weight[detIndex].length; j++) {
						weight[detIndex][j] = weight[detIndex][j] + alpha*(data[i][j]-weight[detIndex][j]);
					}
				}else{
					// Update Weight based on minimum length to the data
					for (int j = 0; j < this.weight[detIndex].length; j++) {
						weight[detIndex][j] = weight[detIndex][j] - alpha*(data[i][j]-weight[detIndex][j]);
					}
				}
			}
			alpha = eps*alpha;
			System.out.println();
		}
	}

	/* Test for data test*/
	void test(int[] dataTest){
		// length/determinant checker init 
		double[] detCheck = new double[weight.length];
		for (int i = 0; i < weight.length; i++) {
			detCheck[i] = 0;
		}

		// get sum of the dataVector of dataTest
		for (int j = 0; j < dataTest.length; j++) {
			for (int n = 0; n < weight.length; n++) {
				detCheck[n] = detCheck[n] + Math.pow(dataTest[j]-weight[n][j],2);
			}
		}
		// do sqrt for all detCheck
		for (int n = 0; n < detCheck.length; n++) {
			detCheck[n] = Math.sqrt(detCheck[n]);
		}

		double min = Double.MAX_VALUE;
		int detIndex = 0;
		for (int n = 0; n < detCheck.length; n++) {
			if (min > detCheck[n]) {
				min = detCheck[n];
				detIndex = n;
			}
		}
		
		// print length result
		for (int i = 0; i < detCheck.length; i++) {
			System.out.print(detCheck[i]+"\t");
		}
		System.out.println();

		System.out.print("Data is in class: ");
		System.out.println(detIndex+1);
	}

	void printWeight(){
		System.out.println("Weight:");
		for (int i = 0; i < this.weight.length; i++) {
			for (int j = 0; j < this.weight[i].length; j++) {
				System.out.print(this.weight[i][j]+"\t");
			}
			System.out.println();
		}
	}

	void printData(){
		System.out.println("Data:");
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[i].length; j++) {
				System.out.print(this.data[i][j]+"\t");
			}
			System.out.println();
		}
	}
}

class driver {
	public static void main(String[] args) {
		// Weight init
		// SNM, SBM, UMB, Mandiri
		// variable (skor test [0,100], IP[0,4])
		// double[][] weight = {{77.5,	2.89},
		// 					{72.0,	3.34},
		// 					{67.75,	2.76},
		// 					{70.15,	3.12}};

		double[][] weight = {{1.,1.,1.,.0},{1.,.0,1.,1.}};
		int[] wClass = {1,2};

		// data init
		int[][] data = {{0,1,1,0},
						{0,0,1,1},
						{1,1,1,1},
						{1,0,0,1}};
		int[] target = {1,2,1,2};
		
		LVQ coba = new LVQ(weight, wClass, data, target, 10, 0.1, 0.05);
		System.out.println("Before Learning");
		coba.printWeight();
		coba.printData();
		System.out.println("\nLearning");
		coba.learn();
		System.out.println("\nAfter Learning");
		coba.printWeight();

		// test data(1,0,1,1)
		System.out.println("\n\nPercobaan data (1,0,1,1)");
		int[] dataTest = {1,0,1,1};
		coba.test(dataTest);
	}
}