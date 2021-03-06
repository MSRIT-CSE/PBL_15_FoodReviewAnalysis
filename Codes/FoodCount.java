//package org.myorg;

import java.io.IOException;
import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class FoodCount {

  public static class Classifier {
    static Scanner tf1;
    static Scanner tf2;
    static String wrd="";
    static String str="";
    static Text res=new Text();
    public static Text classify(Text w) throws Exception
    {
      tf1 = new Scanner(new File("/home/hduser/pbl/Negative.txt"));
      tf2 = new Scanner(new File("/home/hduser/pbl/Positive.txt"));

      str=w.toString();
      while(tf1.hasNext())
      {
	wrd = tf1.next();
	if(wrd.equals(str))
	{
	  res.set("Neg");
	  return res;
	}
      }
      while(tf2.hasNext())
      {
	wrd = tf2.next();
	if(wrd.equals(str))
	{
	  res.set("Pos");
	  return res;
	}
      }
      res.set("Neu");
      return res;
    }
  }

  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private Text word1 = new Text();

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
      String line = value.toString();
      int flag=1;
      Text usr_pnt=new Text();
      Text userid=new Text();
      
      String usr_pns="";
      StringTokenizer tokenizer = new StringTokenizer(line);
      try
      {
      while (tokenizer.hasMoreTokens()) {
        word.set(tokenizer.nextToken());
	if(flag==1)
	{
	  userid.set(word);
	  flag=0;
	  continue;
	}
		word1=Classifier.classify(word);
	usr_pns=userid.toString()+" "+word1.toString();
	usr_pnt.set(usr_pns);
        output.collect(usr_pnt, one);
      }
      }
      catch(Exception e)
      {}
    }
  }

  public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
      int sum = 0;
      while (values.hasNext()) {
        sum += values.next().get();
      }
      output.collect(key, new IntWritable(sum));
    }
  }

  public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf(FoodCount.class);
    conf.setJobName("tweetcount");

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(IntWritable.class);

    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Reduce.class);
    conf.setReducerClass(Reduce.class);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
}
