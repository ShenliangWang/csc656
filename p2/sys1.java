//Shenliang Wang
//edited 4.24


import java.lang.Exception;
import java.lang.Long;
import java.lang.String;
import java.lang.System;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

class sys1
{
    
	public static void simulate(InputStream incomingStream, int size, String s, int arg3, int arg4) throws Exception 
    {
        // See the documentation to understand what these variables mean.
        long microOpCount;
        long instructionAddress;
        long sourceRegister1;
        long sourceRegister2;
        long destinationRegister;
        String conditionRegister;
        String TNnotBranch;
        String loadStore;
        long immediate;
        long addressForMemoryOp;
        long fallthroughPC;
        long targetAddressTakenBranch;
        String macroOperation;
        String microOperation;
        long totalMicroops = 0;
        long totalMacroops = 0;
        
        long totalRead = 0;
        long totalWrite = 0;
        long accesses = 0;
        long readMiss = 0;
        long writeMiss = 0;
        long totalMiss = 0;
        long dirtyRMiss = 0;
        long dirtyWMiss = 0;
        long readFMem = 0;
        long writeFMem = 0;
        long totalAccessR = 0;
        long totalAccessW = 0;
        long missRate = 0;
        long ref = 0;
        String binaryString;
        long index = 0;
        long totalBit = 0;
        long tagBitNum = 0;
        String tag;
        int hitOrMiss = 0;
        String cases;
        
        
        int blockNum = size/16;
        int indexNum = (int)(Math.log((double)blockNum)/Math.log(2));
        long offset = (long)(Math.log(16)/Math.log(2));
        
        int[] vBit = new int[blockNum];
        for(int i=0; i<vBit.length; i++) {
        		vBit[i] = 0;
		}
        int[] dBit = new int[blockNum];
        for(int i=0; i<dBit.length; i++) {
        		dBit[i] = 0;
		}
        String[] tags = new String[blockNum];
        for(int i=0; i<tags.length; i++) {
        		tags[i] = "0";
        }
        
        
        
        BufferedReader r = new BufferedReader(new InputStreamReader(incomingStream));
        String line;
        
        
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            String [] tokens = line.split("\\s+");
            
            
            instructionAddress = Long.parseLong(tokens[1], 16);
            loadStore = tokens[7];
            addressForMemoryOp = Long.parseLong(tokens[9], 16);
            
            if(loadStore.equals("L") || loadStore.equals("S")) {
            		ref++;
            		
            		//to binary
            		binaryString = Long.toBinaryString(addressForMemoryOp);
            		//total bits
            		totalBit = binaryString.length();
            		//# bits for tag
            		tagBitNum = totalBit - indexNum - offset;
            		//index
            		index = Long.parseLong(binaryString.substring((int)tagBitNum, (int)(binaryString.length() - offset)), 2);
            		String hexIndex = Long.toHexString(index);
            		//tag
            		long temp = Long.parseLong(binaryString.substring(0, (int)(binaryString.length() - offset - indexNum)), 2);
            		tag = Long.toHexString(temp);
            		//v
            		int curV = vBit[(int)index];
            		//cache tag
            		String cacheTag = tags[(int)index];
            		//dirty
            		int curDirty = dBit[(int)index];
            		
            		
            		if(!tag.equals(cacheTag)) {
            			if(loadStore.equals("L")) {
            				readMiss++;
            				if(curDirty == 1) {
            					dirtyRMiss++;
            				}
            			}else {
            				writeMiss++;
            				if(curDirty == 1) {
            					dirtyWMiss++;
            				}
            			}
            		}
            		
            		
            		if(tag.equals(cacheTag)) {
            			if(loadStore.equals("L")) {
            				totalRead++;
            				totalAccessR++;
            			}else {
            				totalWrite++;
            				totalAccessW++;
            				dBit[(int)index] =1;
            			}
            			hitOrMiss = 1;
            			vBit[(int)index] = 1;
            			cases = "1";
            		}else if(cacheTag.equals("0") || curDirty == 0) {
            			if(loadStore.equals("L")) {
            				totalRead++;
            				totalAccessR = totalAccessR + 81;
            				tags[(int)index] = tag;
            				dBit[(int)index] = 0;
            			}else {
            				totalWrite++;
            				totalAccessW = totalAccessW + 81;
            				tags[(int)index] = tag;
            				dBit[(int)index] =1;
            			}
            			vBit[(int)index] = 1;
            			cases = "2a";
            			hitOrMiss = 0;
            		}else{
            			if(loadStore.equals("L")) {
            				totalRead++;
            				totalAccessR = totalAccessR + 1 +160;
            				tags[(int)index] = tag;
            				dBit[(int)index] =0;
            			}else {
            				totalWrite++;
            				totalAccessW = totalAccessW + 1 + 160;
            				tags[(int)index] = tag;
            				dBit[(int)index] =1;
            			}
            			vBit[(int)index] = 1;
            			hitOrMiss = 0;
            			cases = "2b";
            		}
             		
            		if(s.equals("-v") && arg3 >= 0 && arg4 >= 0 && arg3 <= arg4 && (ref-1) >= arg3 && (ref-1) <= arg4) {
            			System.out.println((ref-1) + " " + tokens[9] + " " + hexIndex + " "
            					+ tag + " " + curV + " " + cacheTag + " " + curDirty + " " + hitOrMiss
            					+ " " + cases);
            		}	
            }   
        }
        
        readFMem = (readMiss + writeMiss) * 16;
        writeFMem = (dirtyRMiss +  dirtyWMiss)* 16;
        
        System.out.println("direct-mapped, writeback, size = " + size/1024 + "KB");
        System.out.format("loads %d stores %d total %d\n", totalRead, totalWrite, totalRead + totalWrite);
        System.out.format("rmiss %d wmiss %d total %d\n", readMiss, writeMiss, readMiss + writeMiss);
        System.out.format("dirty rmiss %d dirty wmiss %d\n", dirtyRMiss, dirtyWMiss);
        System.out.format("bytes read %d bytes write %d\n", readFMem, writeFMem);
        System.out.format("read time %d write time %d\n", totalAccessR, totalAccessW);
        System.out.format("miss rate %f\n", (double)(readMiss + writeMiss)/(double)(totalRead + totalWrite));
        System.out.format("total access time %d\n", totalAccessR + totalAccessW);
    }
    
    public static void main(String[] args) throws Exception
    {
    		InputStream inputStream = System.in;
        String s = " ";
        int arg3 = 0, arg4 = 0;
        int size = 0;
        
        if (args.length >= 2) {
            inputStream = new FileInputStream(args[0]);
            size = Integer.parseInt(args[1]) * 1024;
        }
        
       
        
        if (args.length >= 5 && args[2].equals("-v")) {
        		s = args[2];
        		arg3 = Integer.parseInt(args[3]);
        		arg4 = Integer.parseInt(args[4]);
        }
        
        if(Math.log((double)size)/Math.log(2) == (int)(Math.log((double)size)/Math.log(2))) {
        		sys1.simulate(inputStream, size, s, arg3, arg4);
        		
        }else {
        		System.out.print("size is not the power of 2");
        		System.exit(0);
        }
    }
}