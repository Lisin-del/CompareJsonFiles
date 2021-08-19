package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;

public class ParserJson {

    private BufferedReader buffReaderFile1;
    private BufferedReader buffReaderFile2;

    private StringBuilder resultStringFile1 = new StringBuilder();
    private StringBuilder resultStringFile2 = new StringBuilder();
    private String line;

    public void read() {
        File directoryUploads = new File("./src/main/resources/uploadFiles/");
        ArrayList<File> files = new ArrayList<>();
        for(File file : directoryUploads.listFiles()) {
            String ext = FilenameUtils.getExtension(file.getName());
            if(ext.equals("json")) {
                files.add(file);
            }
        }

        if(files.size() == 2) {
            try {
                buffReaderFile1 = new BufferedReader(new FileReader(files.get(0)));
                while((line = buffReaderFile1.readLine()) != null) {
                    resultStringFile1.append(line);
                }

                buffReaderFile2 = new BufferedReader(new FileReader(files.get(1)));
                while((line = buffReaderFile2.readLine()) != null) {
                    resultStringFile2.append(line);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    buffReaderFile1.close();
                    buffReaderFile2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //getting the result string
    public String getResultStringFile1() {
        return resultStringFile1.toString();
    }

    public String getResultStringFile2() {
        return resultStringFile2.toString();
    }



}
