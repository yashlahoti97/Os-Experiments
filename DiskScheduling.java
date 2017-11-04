import java.util.*;
class DiskScheduling {
	int disk[];
	int start;
	int nDisk;
	int tst;
	
	
	public void SSTF() {
		int currentPos=Arrays.binarySearch(disk,start);	
		int right = currentPos+1;
		int left = currentPos-1;
		tst=0;
		while(right<=disk.length-2 && left>=1) {
			if(disk[currentPos]-disk[left] < disk[right]-disk[currentPos]) {
				tst += disk[currentPos]-disk[left];
				currentPos = left;
				left --;
//				System.out.print("->"+disk[currentPos]+" ");
			}
			else if(disk[currentPos]-disk[left] >= disk[right]-disk[currentPos]) {
				tst +=disk[right]-disk[currentPos];
				currentPos = right;
				right++;
//				System.out.print("->"+disk[currentPos]+" ");
			}
//			System.out.println("TST : "+tst);
		}
		while(left>=1) {
			tst += disk[currentPos]-disk[left];
			currentPos = left;
			left --;
//				System.out.print("->"+disk[currentPos]+" ");
		//	System.out.println("TST : "+tst);
		}
		while(right<=disk.length-2) {
			tst +=disk[right]-disk[currentPos];
			currentPos = right;
			right++;
		//		System.out.print("->"+disk[currentPos]+" ");
		//	System.out.println("TST : "+tst);
		}
		
		System.out.println("***** For SSTF *****\n Total Seek Time = "+tst);
		
	}

	public void LOOK() {
		tst = 0;
		//for decreasingorder
		int currentPos=Arrays.binarySearch(disk,start);
		int right = currentPos+1;
		int left = currentPos-1;
		while(currentPos!=1) {
			tst += disk[currentPos]-disk[left];
			currentPos=left;
			left--;
		}
		while(currentPos<disk.length-2) {
			tst+= disk[right]-disk[currentPos];
			currentPos=right;
			right++;
		}
		System.out.println("Assuming a Decreasing Order of Access ");
		System.out.println("***** For LOOK *****\n Total Seek Time = "+tst);

	}

	public void SCAN() {
		tst=0;
		int currentPos=Arrays.binarySearch(disk,start);
		int right = currentPos+1;
		int left = currentPos-1;
		while(currentPos!=0) {
			tst += disk[currentPos]-disk[left];
			currentPos=left;
			left--;
		}
		while(currentPos<disk.length-2) {
			tst+= disk[right]-disk[currentPos];
			currentPos=right;
			right++;
		}
		System.out.println("Assuming a Decreasing Order of Access ");
		System.out.println("***** For SCAN *****\n Total Seek Time = "+tst);
	}
	
	public static void main(String args[]) {
		Scanner scr = new Scanner(System.in);
		DiskScheduling obj= new DiskScheduling();
		System.out.println("Enter the number of Disks : ");
		obj.nDisk = scr.nextInt();
		obj.disk = new int[obj.nDisk+3];
		System.out.println("Enter the Disks : ");
		int i;
		for(i=0;i<obj.nDisk;i++) {
			obj.disk[i] = scr.nextInt();
		}
		System.out.println("Enter the starting disk number : ");
		obj.start = scr.nextInt();
		obj.disk[obj.nDisk] = obj.start;
		System.out.println("Enter the starting n end of Cylinder : ");
		obj.disk[obj.nDisk+1]= scr.nextInt();
		obj.disk[obj.nDisk+2]= scr.nextInt();
		Arrays.sort(obj.disk);
		obj.SSTF();
		obj.LOOK();
		obj.SCAN();
	}
	
}