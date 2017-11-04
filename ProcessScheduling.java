import java.util.*;
public class ProcessScheduling {
	int burstTime[];
	int tempburstTime[];
	int arrivalTime[];
	int priority[];
	int completionTime[];
	int waitTime[];
	int turnAroundTime[];
	int nProcess;
	Scanner scr;

	public void reset() {
		for(int i=0;i<nProcess;i++) {
			waitTime[i]=0;
			tempburstTime[i]=burstTime[i];
		}
	}

	public int maxWaitTime() {
		int max=-1,pos=-1;
		for(int i=0;i<nProcess;i++) {
			if(max<waitTime[i]) {
				max=waitTime[i];
				pos = i;
			}
		}
		return pos;
	}

	public int minimum(int flag[],int currentTime) {
		int min=99,pos=-1;
		for(int i=0;i<nProcess;i++) {
			if(flag[i]!=1 && tempburstTime[i]<=min && tempburstTime[i]>0 && currentTime>=arrivalTime[i]) {
				min = burstTime[i];
				pos = i;
			}
		}
		return pos;
	}

	public int maxPriority(int flag[],int currentTime) {
		int max=-1,pos=-1;
		for(int i=0;i<nProcess;i++) {
			if(pos==-1) {
				if(flag[i]!=1 && currentTime>=arrivalTime[i] && tempburstTime[i]>0 && priority[i]>=max) {
					max = priority[i];
					pos = i;
				}
			}
			else {
				if(flag[i]!=1 && currentTime>=arrivalTime[i] && tempburstTime[i]>0 && priority[i]>=max) {
					if(priority[i]==max) {
						if(arrivalTime[i]<arrivalTime[pos]) {
							max = priority[i];
							pos = i;
						}
					}
					else {
						max = priority[i];
						pos = i;
					}
				}
			}
		}
		return pos;
	}

	public int nextProcess(int currentTime,int prevProcess) {
		for(int i=prevProcess+1;i<nProcess;i++) {
			if(tempburstTime[i]>0 && arrivalTime[i]>=currentTime) 
				return i;
		}
		return -1;
	}

	public void display(int flag) {
		System.out.println("Process ID\tAT\tBT\tPri\tCT\tTAT\tWT");
		if(flag==0)
			for(int i=0;i<nProcess;i++) {
				System.out.print(i+"\t\t0\t"+burstTime[i]+"\t"+priority[i]+"\t"+completionTime[i]+"\t"+turnAroundTime[i]+"\t"+waitTime[i]+"\n");
			}
		else {	
			for(int i=0;i<nProcess;i++) {
				System.out.print(i+"\t\t"+arrivalTime[i]+"\t"+burstTime[i]+"\t"+priority[i]+"\t"+completionTime[i]+"\t"+turnAroundTime[i]+"\t"+waitTime[i]+"\n");
			}
		}
	}

	public void FCFS() {
		reset();
		float avgWT=0;
		float avgTAT=0;
		waitTime[0]=0;
		completionTime[0]=burstTime[0];
		turnAroundTime[0]=completionTime[0];
		for(int i=1;i<nProcess;i++) {
			waitTime[i]=waitTime[i-1]+burstTime[i-1];
			avgWT+=waitTime[i];
			completionTime[i]=waitTime[i]+burstTime[i];
			turnAroundTime[i]=completionTime[i];
			avgTAT+=turnAroundTime[i];
		}
		System.out.println("\nFCFS :");
		display(0);
		System.out.println("Avg. WT = "+avgWT/nProcess+"\nAvg. TAT = "+avgTAT/nProcess);
	}

	public void SJFNonPremptive() {
		reset();
		float avgWT=0;
		float avgTAT=0;
		int flag[] = new int[nProcess];
		for(int i=0;i<nProcess;i++) {
			flag[i]=0;
		}
		int i=0;
		int currentTime=0;
		while(i<nProcess) {
			int pos = minimum(flag,currentTime);
			if(pos==-1) {
				currentTime++;
			}
			else {
				flag[pos]=1;
				i++;
				completionTime[pos] = currentTime+burstTime[pos];	
				turnAroundTime[pos] = completionTime[pos] - arrivalTime[pos];
				waitTime[pos] = turnAroundTime[pos]-burstTime[pos];
				avgTAT+=turnAroundTime[pos];
				avgWT+=waitTime[pos];
				currentTime=completionTime[pos];
			}
			
		}
		System.out.println("\nSJF Non-Premptive :");
		display(1);
		System.out.println("Avg. WT = "+avgWT/nProcess+"\nAvg. TAT = "+avgTAT/nProcess);
	}

	public void SJFPremptive() {
		reset();
		float avgWT=0;
		float avgTAT=0;
		int flag[] = new int[nProcess];
		for(int i=0;i<nProcess;i++) {
			flag[i]=0;
		}
		int i=0;
		int currentTime=0;
		while(i<nProcess) {
			int pos = minimum(flag,currentTime);
			if(pos!=-1) {
				tempburstTime[pos]--;
				if(tempburstTime[pos]==0) {
					completionTime[pos]=currentTime+1;
					turnAroundTime[pos] = completionTime[pos] - arrivalTime[pos];
					waitTime[pos] = turnAroundTime[pos]-tempburstTime[pos];
					avgTAT+=turnAroundTime[pos];
					avgWT+=waitTime[pos];
					i++;
				}
			}
			currentTime++;
		}
		System.out.println("\nSJF Premptive :");
		display(1);
		System.out.println("Avg. WT = "+avgWT/nProcess+"\nAvg. TAT = "+avgTAT/nProcess);
	}

	public void priorityNonPreemptive() {
		reset();
		float avgWT=0;
		float avgTAT=0;
		int flag[] = new int[nProcess];
		for(int i=0;i<nProcess;i++) {
			flag[i]=0;
		}
		int i=0;
		int currentTime=0;
		while(i<nProcess) {
			int pos=maxPriority(flag,currentTime);
			//System.out.println(pos);
			if(pos!=-1) {
				flag[pos]=1;
				i++;
				completionTime[pos] = currentTime+burstTime[pos];	
				turnAroundTime[pos] = completionTime[pos] - arrivalTime[pos];
				waitTime[pos] = turnAroundTime[pos]-burstTime[pos];
				avgTAT+=turnAroundTime[pos];
				avgWT+=waitTime[pos];
				currentTime=completionTime[pos];
			}
			else { 
				currentTime++;
			}
		}
		System.out.println("\nPriority Non-Premptive :");
		display(1);
		System.out.println("Avg. WT = "+avgWT/nProcess+"\nAvg. TAT = "+avgTAT/nProcess);
	}

	public void priorityPreemptive() {
		reset();
		float avgWT=0;
		float avgTAT=0;
		int flag[] = new int[nProcess];
		for(int i=0;i<nProcess;i++) {
			flag[i]=0;
		}
		int i=0;
		int currentTime=0;
		while(i<nProcess) {
			int pos = maxPriority(flag,currentTime);
			if(pos!=-1) {
				tempburstTime[pos]--;
				if(tempburstTime[pos]==0) {
					completionTime[pos]=currentTime+1;
					turnAroundTime[pos] = completionTime[pos] - arrivalTime[pos];
					waitTime[pos] = turnAroundTime[pos]-burstTime[pos];
					avgTAT+=turnAroundTime[pos];
					avgWT+=waitTime[pos];
					i++;
				}
			}
			//System.out.println("currentTime :"+currentTime+" process:"+pos);
			currentTime++;
		}
		System.out.println("\nPriority Premptive :");
		display(1);
		System.out.println("Avg. WT = "+avgWT/nProcess+"\nAvg. TAT = "+avgTAT/nProcess);
	}

	public void roundRobin() {
		System.out.println("Enter the time quantum : ");
		int quantum = scr.nextInt();
		int i=0;
		reset();
		float avgWT=0;
		float avgTAT=0;
		int currentTime=0;
		int pos = nextProcess(currentTime,-1);
		

	}

	public static void main(String args[]) {
		ProcessScheduling obj = new ProcessScheduling();
		scr = new Scanner(System.in);
		System.out.println("Enter the number of Process : ");
		obj.nProcess=scr.nextInt();
		obj.burstTime = new int[obj.nProcess];
		obj.tempburstTime = new int[obj.nProcess];
		obj.arrivalTime = new int[obj.nProcess];
		obj.waitTime = new int[obj.nProcess];
		obj.turnAroundTime = new int[obj.nProcess];
		obj.completionTime = new int[obj.nProcess];
		obj.priority = new int[obj.nProcess];
		System.out.println("Enter the Process Burst Times : ");
		for(int i=0;i<obj.nProcess;i++) {
			obj.burstTime[i]=scr.nextInt();
		}
		for(int i=0;i<obj.nProcess;i++) {
			obj.tempburstTime[i]=obj.burstTime[i];
		}
		System.out.println("Enter the arrival Time for the processes : ");
		for(int i=0;i<obj.nProcess;i++)
			obj.arrivalTime[i]=scr.nextInt();
		System.out.println("Enter the Priorities for the processes : ");
		for(int i=0;i<obj.nProcess;i++)
			obj.priority[i]=scr.nextInt();
		obj.FCFS();
		obj.SJFNonPremptive();
		obj.SJFPremptive();
		obj.priorityNonPreemptive();
		obj.priorityPreemptive();
	}
}