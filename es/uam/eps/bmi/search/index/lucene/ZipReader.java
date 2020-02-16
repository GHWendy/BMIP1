/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            
            clear(destPath);
            File destDir = new File(destPath);
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
    
    private void clear (String indexPath) throws IOException {
            File dir = new File(indexPath);
            if (!dir.exists()) Files.createDirectories(Paths.get(indexPath));
            for (File f : dir.listFiles()) if (f.isFile()) f.delete();
        }      
 }

   
