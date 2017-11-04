import java.util.*;
public class Fits {
	Scanner scr = new Scanner(System.in);
	int flagBlock[];
	int flagProcess[];
	int processAllocated[];
	int blockSize[] = {100,200,400,800,600};
	int processSize[]={880,400,300,100,530};
	int nBlock,nProcess;

	public void reset() {
		int i;
		for(i=0;i<nBlock;i++){
			flagBlock[i]=-1;
			processAllocated[i]=-1;
		}

		for(i=0;i<nProcess;i++)
			flagProcess[i]=-1;
	}

	public void firstFit() {
		int i,j;
		reset();
		for(i=0;i<nProcess;i++) {
			for (j=0;j<nBlock;j++ ) {
				if(blockSize[j]>=processSize[i] && flagBlock[j]==-1)
				{
					flagBlock[j]=0;
					flagProcess[i]=0;
					processAllocated[j]=i;
					break;
				}
			}

		}
		System.out.println("\n\t\tFirst Fit is:");
		display(blockSize,nBlock,processSize,nProcess,processAllocated);
	}

	public  void bestFit() {
		int i,j,diff=999,temp=-1;
		reset();
		for(i=0;i<nProcess;i++) {
			diff=999;
			temp=-1;
			for(j=0;j<nBlock;j++) {
				if(flagBlock[j]==-1) {
					if(blockSize[j]-processSize[i]>=0 && blockSize[j]-processSize[i]<diff) {
						diff =blockSize[j]-processSize[i];
						temp=j;
					}	
				}
			}
			if(temp!=-1) {
				flagBlock[temp]=0;
				processAllocated[temp]=i;
				flagProcess[i]=0;
			}

		}
		System.out.println("\n\n\n\t\t Best Fit is :");
		display(blockSize,nBlock,processSize,nProcess,processAllocated);
	}

	public void worstFit() {
		int i,j,diff=0,temp=-1;
		reset();
		for(i=0;i<nProcess;i++) {
			diff=0;
			temp=-1;
			for(j=0;j<nBlock;j++) {
				if(flagBlock[j]==-1) {
					if(blockSize[j]-processSize[i]>=diff) {
						diff =blockSize[j]-processSize[i];
						temp=j;
					}	
				}
			}
			if(temp!=-1) {
				flagBlock[temp]=0;
				processAllocated[temp]=i;
				flagProcess[i]=0;
			}

		}
		System.out.println("\n\n\n\t\t Worst Fit is :");
		display(blockSize,nBlock,processSize,nProcess,processAllocated);
	}

	public void display(int blockSize[],int nBlock,int processSize[],int nProcess,int processAllocated[]) {
		int i;
		System.out.println("\nBlock Id\tBlock Size\tProcess Id\tProcess\tRemaining Memory");
		for (i=0;i<nBlock;i++ ) {
			if(processAllocated[i]==-1)
				System.out.println(i+"\t\t"+blockSize[i]+"\t\t---\t\t---\t\t"+blockSize[i]);
			else
				System.out.println(i+"\t\t"+blockSize[i]+"\t\t"+processAllocated[i]+"\t\t"+processSize[processAllocated[i]]+"\t\t"+(blockSize[i]-processSize[processAllocated[i]]) );
		}
		System.out.print("\n\nIds of Processes Not allocated Memory : ");
		for(i=0;i<nProcess;i++) {
			if(flagProcess[i]==-1)
				System.out.print(i+", ");
		}
	}

	public static void main(String args[]) {
		
		Fits obj=new Fits();
		obj.nBlock=obj.blockSize.length;
		obj.nProcess=obj.processSize.length;
		obj.flagBlock=new int[obj.nBlock];
		obj.flagProcess = new int[obj.nProcess];
		obj.processAllocated = new int[obj.nBlock];
		System.out.println("Block sizes =  ");
		for(int i=0;i<obj.nBlock;i++) {
			System.out.print(obj.blockSize[i]+" ");
		}
		System.out.println("\nProcess sizes =  ");
		for(int i=0;i<obj.nProcess;i++) {
			System.out.print(obj.processSize[i]+" ");
		}
		obj.firstFit();
		obj.bestFit();
		obj.worstFit();
	}
}