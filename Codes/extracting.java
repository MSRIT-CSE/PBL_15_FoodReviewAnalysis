import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class extracting {
	public static void main(String[] args) throws IOException{
		
		CharSequence cs2 = "text: ";
		CharSequence cs1 = "userId: ";
		double counter=0;
		int check=0;

		File file = new File("C:/Users/Ganesh Hegde/Desktop/stop/text3.txt");
		File file1 = new File("C:/Users/Ganesh Hegde/Desktop/stop/id.txt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FileWriter writer = new FileWriter(file); 
		FileWriter writer1 = new FileWriter(file1); 
		
		try(BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ganesh Hegde/Desktop/foods.txt"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
			if(line.contains(cs1))
		    	{
				counter++;
				String line1=" ";
				writer.write(line1 + "");
		    		check=0;
				line1 = line.replace("ID: ","  ");
		    	 	writer1.write(line1 +" "+ "");
		    	}
			if(line.contains(cs2))
			{
				check=1;
			}
		    	if(check==1)
		    	{
		    		String line1 = line.replace("text: ","  ");
		    	 	writer.append(line1 + " ");
		    	}
		    	if(counter==150001 && check==1)
			{
				break;
			}
		    } 	
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
	    writer.close();

	}

}