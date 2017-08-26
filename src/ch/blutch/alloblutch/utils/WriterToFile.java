package ch.blutch.alloblutch.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriterToFile {

	private static String fileName="C:\\AlloBlutch.log";
	
	public static void write(String value) {
		Path file = Paths.get(fileName);
		try {
			BufferedWriter bw = Files.newBufferedWriter(file, Charset.defaultCharset(), StandardOpenOption.APPEND);
			bw.write(value);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
