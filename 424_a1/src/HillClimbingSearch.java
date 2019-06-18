
import java.lang.Math;


public class HillClimbingSearch {

	public static void main (String[] args){
		double input[] = {0,1,2,3,4,5,6,7,8,9,10}; 
		double stepSize[] = new double[100];
		double xValue;
		double yValue;
		double xtemp1;
		double xtemp2;
		double ytemp1;
		double ytemp2;
		double start = 0.01;
		double x;
		double y;
		int counter = 0;
		for(int i = 0;i<100;i++){
			stepSize[i] = start;
			start = start+0.01;
			
		}
		for(int i =0;i<10;i++){
			x = input[i];
			for(int j=0;j<100;j++){
				while(true){
					
					
					y = function(x);
					xtemp1 = x+stepSize[j];
					xtemp2 = x-stepSize[j];
					ytemp1 = function(xtemp1);
					ytemp2 = function(xtemp2);
					if(max(ytemp1,ytemp2)==1){
						xValue = xtemp1;
						yValue = ytemp1;
					}else{
						xValue = xtemp2;
						yValue = ytemp2;
					}
					//System.out.println("yvalue is "+yValue);
					if( yValue > y){
						x = xValue;
					}else{
						//System.out.println(x);
						//System.out.println(y);
						System.out.println(counter);
						break;
					}
					
					
					
					counter++;
					
					
				}
				//counter
			}
			
		}
		
		
		
		
		
	}


	private static double max(double a, double b) {
		if(a>b){
			return 1;
		}else{
			return 0;
		}
		
	}


	private static double function(double x) {
	
		return Math.sin(x*x/2) / log2(x+4);
	}

	private static double log2(double d) {
		return Math.log(d) / Math.log(2);
	}
	


}
