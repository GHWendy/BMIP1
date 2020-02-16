/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Wendy Sosa
 */
public class ZipReader {
 
    public String unzip(String fileZip) {
        File file = new File(fileZip);
        String destPath = fileZip.replace(file.getName(), "unzipped");
        ZipInputStream zis = null;
        try {
            File destDir = new File(destPath);
            
        if (!destDir.exists()) Files.createDirectories(Paths.get(destPath));
            byte[] buffer = new byte[1024];
            zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zis.getNextEntry();
            }   zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el Zip");
        } catch (IOException ex) {
            System.out.println("Error abriendo el archivo");
        } finally {
            try {
                zis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return destPath;
        }
    }
     
    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }         
        return destFile;
    }    
 }

   
