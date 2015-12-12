import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class navie { 

	static String[] negwords = new String[10000];
	static String[] poswords = new String[10000];
	static double posprob[]= new double[1000];
	static double negprob[] = new double[1000];
	static int i=0,j=0;
	static float ppos=0,pneg=0;
	static float temp=0;

	public static void analyzeWords() throws IOException
    {
        String word,tempID="";
        String cs1 = "product/productId:";
        String cs2 = "pos:";
        String cs3 = "neg:";
		int check=0;
		int flag=0;
		float pos=0,neg=0,total;
	

        try
        {
            Scanner textFile = new Scanner(new File("C:/Users/Ganesh Hegde/Desktop/stop/posneg3.txt"));
            //textFile.useDelimiter(Pattern.compile("[ \n\r\t,.;:?!'\"]+"));
            FileWriter outFile = new FileWriter("C:/Users/Ganesh Hegde/Desktop/stop/output3.txt");
            //PrintWriter outFile = new PrintWriter(new File("D:/stopwords/op.txt"));

            while (textFile.hasNext())
            {
                word = textFile.next();
               
                if(word.equals(cs1))
                {
                	outFile.write("\n");
                	 
                	 check=1;
                	 
                	 continue;
                }
                if(check==1)
                {
                	if(pneg==0 && ppos==0)
                	{
                		
                	}
                	else if(pneg==0)
                	{
                		temp= (float)(temp+Math.log(0.99999999/0.00000001));
                		//outFile.append(Math.log(0.99999999/0.00000001)+"\n");
                	}
                	else if(ppos==0)
                	{
                		temp= (float)(temp+Math.log(0.00000001)/0.99999999);
                		//outFile.append(Math.log(0.00000001)/0.99999999+"\n");
                	}
                	
                	else if(pneg!=0 && ppos!=0 ) 
        			{
                		temp= (float) (temp+Math.log(ppos/pneg));
                		//outFile.append(Math.log(ppos/pneg)+"\n");
        			}
                	
                	if(tempID.equals(word))
                	{
                		outFile.append(cs1+" "+word+"\n");
                	}
                	else
                	{
                		if(temp>0)
                		{
                			  outFile.write("This food  has positive reviews with " + temp);
                			  System.out.println("Food has +ve review " + temp );
                              outFile.write("\n");
                		}
                		else if(temp==0)
                		{
                			 outFile.write("This food  has nuetral reviews with " + temp);
                			 System.out.println("Food has nuetral review " + temp );
                		}
                		else
                		{
                			outFile.write("This food has negative reviews with " + temp );
                			System.out.println("Food has -ve review " + temp );
                			outFile.write("\n");
                		}
                		outFile.write("\n");
                		
                		outFile.append(cs1+" "+word+"\n");
                		
                	//outFile.append('\n');
                		tempID=word;
                		temp=0;
                		
                		
                	}
                	check=0;
                	continue;	
                	
                }
                
                if(word.equals(cs2))
                {   
                    flag=1;
                   
                    continue;
                    
                }
                
                if(flag==1)
                {
                	pos=Float.parseFloat(word);
                	 
                	flag=0;
                	continue;
                }
                
                if(word.equals(cs3))
                {
                    flag=2;
                    continue;
                }
                
                if(flag==2)
                {
                	neg=Float.parseFloat(word);
                	
                	total= pos + neg;
                	if(total!=0)
                	{
                		ppos = pos/total;
                 		pneg = neg/total;
                    	
                	}
                	
                    outFile.write("ppos: " + ppos);
                    outFile.write("\n");
                    outFile.write("npos: " + pneg);
                    outFile.write("\n");
                	flag=0;
                	continue;
                }
                

            }
            textFile.close();
            outFile.close();
           // System.out.println("Output File " + RESULT_FNAME);
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
       
    }


    public static void main(String[] arg) throws IOException
    {
       analyzeWords();
    }
}

