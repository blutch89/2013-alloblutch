package ch.blutch.alloblutch.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

public class UploadUtils {

	public static void uploadFile(UploadedFile picture, String directory, String fileName) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(picture.getInputstream());
		
		String destinationPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(directory + fileName);
		Path destinationFile = Paths.get(destinationPath);
		OutputStream bos = Files.newOutputStream(destinationFile);
		
		// Copie le contenu du fichier uploadé et l'écrit dans un nouveau fichier sur le serveur
		int data = bis.read();
		while(data != -1) {
			bos.write(data);

			data = bis.read();
		}
		
		bis.close();
		bos.close();
	}
	
	public static String purifyPictureName(String pictureName) {
		pictureName = pictureName.replace(" ", "_");
		pictureName = pictureName.replaceAll("\\W", "");
		
		return pictureName;
	}
	
	public static String getFileExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		
		return extension;
	}
	
}
