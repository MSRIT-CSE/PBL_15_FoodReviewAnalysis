import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class posneg { 
//	private static String RESULT_FNAME = "Output.txt";
	static String[] negwords = new String[10000];
	static String[] poswords = new String[10000];
	static int poscount=0,negcount=0;
	
    public static Boolean isStopWord(String word, String[] Words)
    {
    	boolean found = false;
		
    	for(int f=0; f<Words.length; f++)
    	{
    		if(word.equals(Words[f]))
    		{
    			found=true;
    			break;
    		}
    	}
    	
    	return found;  
    }

    public static int compareWords(String word1, String word2)
    {
        return word1.compareToIgnoreCase(word2);
    }

    public static String[] readposWords() throws IOException 
    {
        
        int i=0;
        try
        {
        	try(BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ganesh Hegde/Desktop/stop/Positive.txt"))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		    	//System.out.println(line);
    		    	poswords[i] = line;
    		    	 i++;
    		    }
    		   
        	}
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }     

        return poswords;
     }

    public static String[] readnegWords() throws IOException 
    {
        
        int i=0;
        try
        {
        	try(BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ganesh Hegde/Desktop/stop/Negative.txt"))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		 // System.out.println(line);
    		    	negwords[i] = line;
    		    	 i++;
    		    }
    		   
        	}
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }     

        return negwords;
     }

    public static void analyzeWords() throws IOException
    {
        String word;
        String cs1 = "product/productId:";
		String cs2 = "review/ ";
		int check=0,i=0,j=0;

        try
        {
            Scanner textFile = new Scanner(new File("C:/Users/Ganesh Hegde/Desktop/stop/textop3.txt"));
            //textFile.useDelimiter(Pattern.compile("[ \n\r\t,.;:?!'\"]+"));
            FileWriter outFile = new FileWriter("C:/Users/Ganesh Hegde/Desktop/stop/posneg3.txt");
            //PrintWriter outFile = new PrintWriter(new File("D:/stopwords/op.txt"));

            while (textFile.hasNext())
            {
                word = textFile.next();
               
                if(word.equals(cs1))
                {
                	outFile.write("pos: "+ poscount +"\n");
                	outFile.write("neg: "+ negcount+"\n");
                	poscount=0;
                	negcount=0;
                	outFile.write("\n");
                	outFile.write(word + " ");
                	 check=1;
                	 continue;
                }
                if(check==1)
                {
                	outFile.append(word + "\n");
                	//outFile.append('\n');
                	check=0;
                	continue;
                }
             
                if(check==0)
                {
                if (isStopWord(word,poswords))
                { i++;
                	poscount++;
                   // System.out.print(word + " ");
                }
                else if(isStopWord(word,negwords))
                { j++;
                	negcount++;
                }
                }
                
                System.out.println(word);
                
            }
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
        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Input StopWord File: ");
        //String[] stopWords = readStopWords(keyboard.next());

        //System.out.print("Input file from which stopword to be removed: ");
       readposWords();
       readnegWords();
       analyzeWords();
    }
}
