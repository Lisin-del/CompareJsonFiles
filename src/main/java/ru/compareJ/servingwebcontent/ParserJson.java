package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class ParserJson {
    private File directoryUploads = new File("./src/main/resources/uploadFiles/");
    private File[] files = directoryUploads.listFiles();

    private BufferedReader buffReaderFile1;
    private BufferedReader buffReaderFile2;

    private StringBuilder resultStringFile1 = new StringBuilder();
    private StringBuilder resultStringFile2 = new StringBuilder();
    private String line;

    public void read() {
       try {
           buffReaderFile1 = new BufferedReader(new FileReader(files[0]));
           while((line = buffReaderFile1.readLine()) != null) {
               resultStringFile1.append(line);
           }

           buffReaderFile2 = new BufferedReader(new FileReader(files[1]));
           while((line = buffReaderFile2.readLine()) != null) {
               resultStringFile2.append(line);
           }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
