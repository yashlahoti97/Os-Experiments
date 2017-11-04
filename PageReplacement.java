import java.util.*;

public class PageReplacement {

	int ram[];
	int pageSize[];
	int nRam,nPage;

	public int ramContains(int a) {
		for(int i=0;i<nRam;i++) {
			if(ram[i]==a)
				return i;
		}
		return -1;
	}

	public int pageContains(int a,int pos) {
		for(int i=pos;i<nPage;i++) {
			if(pageSize[i]==a)
				return i;
		}
		return 99;
	} 

	public void resetRam() {
		for(int i=0;i<nRam;i++) {
			ram[i]=-1;
		}
	}

	public void FIFO() {
		int count=0;
		int hit=0;
		int miss=0;
		resetRam();
		for(int i=0;i<nPage;i++) {
			int pos= ramContains(pageSize[i]);
			if(pos!=-1) {
				hit++;
			}
			else{
				if(ram[count]==-1) {
					ram[count] = pageSize[i];
					count=(count+1)%nRam;
					miss++;
				}
				else{	
					ram[count]=pageSize[i];
					count=(count+1)%nRam;
					miss++;
				}
			}
		}
		System.out.println("Number of Hits : "+hit);
		System.out.println("Number of Misses : "+miss);
	}

	public void LRU() {
		int hit=0;
		int miss=0;
		resetRam();
		for(int i=0;i<nPage;i++) {
			int flag=0;
			int pos = ramContains(pageSize[i]);
			if(pos!=-1) {
				for(int j=pos;j<nRam-1;j++) {
					ram[j]=ram[j+1];
				}
				ram[nRam-1]=pageSize[i];
				hit++;
			}
			else {
				for(int j=0;j<nRam-1;j++) {
					ram[j]=ram[j+1];
				}
				ram[nRam-1]=pageSize[i];
				miss++;
			}
		}		
		System.out.println("Number of Hits : "+hit);
		System.out.println("Number of Misses : "+miss);
	}

	public void optimal() {
		int hit=0;
		int miss=0;
		resetRam();
		for(int i =0;i<nPage;i++) {
			if(ramContains(pageSize[i])!=-1) {
				hit++;
			}
			else {
				int futurePosition[] = new int[nRam];
				for (int j=0;j<nRam;j++ ) {
					futurePosition[j]=-1;
				}
				for(int j=0;j<nRam;j++) {
					if(ram[j]==-1) 
						futurePosition[j]=100;
					else
						futurePosition[j]=pageContains(ram[j],i);
				}
				int max=0;
				for (int j=0;j<nRam;j++) {
					if(futurePosition[j]>futurePosition[max])
						max=j;
				}
				ram[max]=pageSize[i];
				miss++;
			}
		}
		System.out.println("Number of Hits : "+hit);
		System.out.println("Number of Misses : "+miss);
	}
	
	public static void main(String args[]) {
		Scanner scr = new Scanner(System.in);
		PageReplacement obj = new PageReplacement();
		System.out.println("Enter the number of Frames in Ram : ");
		obj.nRam = scr.nextInt();
		obj.ram = new int[obj.nRam];
		System.out.println("Enter the number of Pages : ");
		obj.nPage = scr.nextInt();
		System.out.println("Enter the page sizes : ");
		obj.pageSize = new int[obj.nPage];
		for(int i = 0;i<obj.nPage;i++)
			obj.pageSize[i]=scr.nextInt();
		System.out.println("\nFIFO : ");
		obj.FIFO();
		System.out.println("\nLRU : ");
		obj.LRU();
		System.out.println("\nOPTIMAL : ");
		obj.optimal();
	}

}