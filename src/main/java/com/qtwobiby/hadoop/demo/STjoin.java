package com.qtwobiby.hadoop.demo;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class STjoin {

	public static int time = 0;
	//map将输入分割成child和parent，然后正序输出一次作为右表，反
	//序输出一次作为左表，需要注意的是在输出的value中必须加上左右表
	//区别标志
	public static class Map extends Mapper<Object, Text, Text, Text> {

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String childname = new String();
			String parentname = new String();
			String relationtype = new String();
			String line = value.toString();
			int i = 0;
			while(line.charAt(i) != ' ') {
				i++;
			}
			String[] values = {line.substring(0, i), line.substring(i + 1)};
			if (values[0].compareTo("child") != 0) {
				childname = values[0];
				parentname = values[1];
				relationtype = "1";
				context.write(new Text(values[1]), new Text(relationtype + "+" + childname + "+" + parentname));
				
				relationtype = "2";
				context.write(new Text(values[0]), new Text(relationtype + "+" + childname + "+" + parentname));
			}
		}
		
	}
	
	public static class Reduce extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			if (time == 0) {
				context.write(new Text("grandchild"), new Text("grandparent"));
				time ++;
			}
			
			int grandchldnum = 0;
			String grandchild[] = new String[10];
			int grandparennum = 0;
			String grandparent[] = new String[10];
			Iterator ite = values.iterator();
			while(ite.hasNext()) {
				String record = ite.next().toString();
				int len = record.length();
				int i = 2;
				if (len == 0) continue;
				char relationtype = record.charAt(0);
				String childname = new String();
				String parentname = new String();
				while (record.charAt(i) != '+') {
					childname = childname  + record.charAt(i);
					i ++;
				}
				i = i + 1;
				while(i < len) {
					parentname = parentname + record.charAt(i);
					i ++;
				}
				
				if (relationtype == '1') {
					grandparent[grandparennum] = childname;
					grandchldnum ++;
				} else {
					grandparent[grandparennum] = parentname;
					grandparennum ++;
				}
			}
			if (grandparennum != 0 && grandchldnum != 0) {
				for (int m = 0; m < grandchldnum; m++) {
					for(int n = 0; n < grandparennum; n++) {
						context.write(new Text(grandchild[m]), new Text(grandparent[n]));
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: STjoin <in> <out>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "single table join");
		job.setJarByClass(STjoin.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
}
