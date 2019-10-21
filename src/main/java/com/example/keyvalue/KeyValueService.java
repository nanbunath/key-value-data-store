package com.example.keyvalue;


import com.example.keyvalue.model.KeyValue;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class KeyValueService {

public String checkFileSize(List<KeyValue> keyValue){

    File file = new File("D:\\Directory2\\db.xlsx");
    long sizeInBytes = file.length();
    long sizeInMb = sizeInBytes / 1024;

    if(sizeInMb < 1024) {
        if(!(keyValue.get(0).getKey().length()>32)) {
            if (!(keyValue.get(0).getJsonValue().length() > 16)) {
                return "true";
            }
            else
            return "JSON Object size is Greater than 16KB";
        }
        else
        return "KEY Contains More than 32 Characters";
    }
    else
        return "File size is Greater than 1GB";
}

   synchronized public void createOperation(List<KeyValue> keyValues) throws IOException {
        int rowCount;
        String line;
        FileReader reader = new FileReader("C:\\Directory2\\Info.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);

            //FileInputStream file = new FileInputStream(line+"\\db.xlsx");
            FileInputStream fileIn = new FileInputStream("C:\\Directory2\\db.xlsx");
            //Workbook workbook = WorkbookFactory.create(fileIn);
            Workbook workbook = new XSSFWorkbook(fileIn);
            fileIn.close();
            FileInputStream keyfileIn = new FileInputStream("C:\\Directory2\\keys.xlsx");
            //Workbook workbook = WorkbookFactory.create(fileIn);
            Workbook keyworkbook = new XSSFWorkbook(keyfileIn);
            CreationHelper createHelper = keyworkbook.getCreationHelper();
            keyfileIn.close();
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            System.out.println(lastRow);
            Row row = sheet.createRow(++lastRow);
            row.createCell(0).setCellValue(keyValues.get(0).getKey());
            row.createCell(1).setCellValue(String.valueOf(keyValues.get(0).getJsonValue()));

            Sheet keysheet = keyworkbook.getSheetAt(0);
            int KeylastRow = keysheet.getLastRowNum();
            Row keyrow = keysheet.createRow(++KeylastRow);
            keyrow.createCell(0).setCellValue(keyValues.get(0).getKey());
            Cell cell = keyrow.createCell(1);
            CellStyle cellStyle = keyworkbook.createCellStyle();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("d/m/yy h:mm"));
            cell.setCellValue(new Date());
            cell.setCellStyle(cellStyle);
            keyrow.createCell(2).setCellValue(keyValues.get(0).getTimeToLive());
            keyrow.createCell(3).setCellValue(lastRow);
            keyrow.createCell(4).setCellValue("db.xlsx");
            FileOutputStream out =
                    new FileOutputStream("C:\\Directory2\\db.xlsx");
            workbook.write(out);
            FileOutputStream keyout =
                    new FileOutputStream("C:\\Directory2\\keys.xlsx");
            keyworkbook.write(keyout);
            keyout.close();
            out.close();
        }

        reader.close();


    }
    public boolean getPath(String pathName) throws IOException {
        File f = new File(pathName);

        List<String> list = new ArrayList<>();

        if(f.exists()){
            File DBFile = new File(pathName+"\\db.xlsx");
            File KeysFile = new File(pathName+"\\keys.xlsx");
            //File LinesFile = new File(pathName+"\\lines.txt");

            if(DBFile.exists()&&KeysFile.exists())
                addInfo(pathName);
                return true;
            }
            return false;
    }

   public void createPath() throws IOException {

       File DBFile = new File("C:\\Directory2\\db.xlsx");
       File KeysFile = new File("C:\\Directory2\\keys.xlsx");
       //File LinesFile = new File("D:\\Directory2\\lines.txt");



       if(!KeysFile.getParentFile().exists()){
           KeysFile.getParentFile().mkdirs();
       }
       DBFile.createNewFile();
       KeysFile.createNewFile();
       //LinesFile.createNewFile();
       String path = "C:\\Directory2";
       addInfo(path);


       XSSFWorkbook wb = new XSSFWorkbook ();
       Sheet sheet1 = wb.createSheet("KeyValue");
       Row headerRow = sheet1.createRow(0);
       Cell cell = headerRow.createCell(0);
       cell.setCellValue("Keys");
       Cell cell1 = headerRow.createCell(1);
       cell1.setCellValue("JSON Values");
       OutputStream fileOut = new FileOutputStream(DBFile);
       wb.write(fileOut);
       fileOut.close();

       XSSFWorkbook wb1 = new XSSFWorkbook ();
       Sheet sheet = wb1.createSheet("KeysInfo");
       Row row = sheet.createRow(0);
       Cell c1 = row.createCell(0);
       c1.setCellValue("Keys");
       Cell c2 = row.createCell(1);
       c2.setCellValue("Created Date");
       Cell c3 = row.createCell(2);
       c3.setCellValue("Time To Live");
       Cell c4 = row.createCell(3);
       c4.setCellValue("Line Number");
       Cell c5 = row.createCell(4);
       c5.setCellValue("File Name");
       OutputStream fileOut1 = new FileOutputStream(KeysFile);
       wb1.write(fileOut1);
       fileOut1.close();


   }

   private void addInfo(String pathName) throws IOException {

       File InfoFile = new File("C:\\Directory2\\info.txt");
       FileWriter writer = new FileWriter(InfoFile);
       writer.write(pathName);
       writer.close();

   }


   public boolean checkKeyAlreadyexists(List<KeyValue> keyValue) throws IOException {
       boolean IsExists = false;
       FileInputStream keyfileIn = new FileInputStream("C:\\Directory2\\keys.xlsx");
       //Workbook workbook = WorkbookFactory.create(fileIn);
       Workbook keyworkbook = new XSSFWorkbook(keyfileIn);
       Sheet sheet =  keyworkbook.getSheetAt(0);

       for(Row row : sheet) {
           Cell cell = row.getCell(0);
                   if(cell.getRichStringCellValue().getString().equalsIgnoreCase(keyValue.get(0).getKey())){
                       IsExists = true;
                       return IsExists;
                   }
               }
           return false;
   }

    public double getKeyRowNum(String key,String type) throws IOException, ParseException {
        double lineNumber = 0;
        FileInputStream keyfileIn = new FileInputStream("C:\\Directory2\\keys.xlsx");
        //Workbook workbook = WorkbookFactory.create(fileIn);
        Workbook keyworkbook = new XSSFWorkbook(keyfileIn);
        Sheet sheet =  keyworkbook.getSheetAt(0);

        for(Row row : sheet) {
            Cell cell = row.getCell(0);


                if (cell.getRichStringCellValue().getString().equalsIgnoreCase(key)) {
                    Date CreatedDateString = row.getCell(1).getDateCellValue();
                    System.out.println(CreatedDateString);
                    SimpleDateFormat formatter=new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
                    String CreatedDate=formatter.format(CreatedDateString);
                    Date CreatedDateTime = formatter.parse(CreatedDate);
                    Date dt = new Date();
                    String currentDateTimeString = formatter.format(dt);
                    Date currentDateTime = formatter.parse(currentDateTimeString);
                    System.out.println("Current" +currentDateTime.getTime());
                    System.out.println("Crated" +CreatedDateTime.getTime());
                    long diff = currentDateTime.getTime() - CreatedDateTime.getTime();
                    long diffSeconds = diff / 1000;
                    if(diffSeconds<row.getCell(2).getNumericCellValue()) {
                    Cell lineCell = row.getCell(3);

                    lineNumber = lineCell.getNumericCellValue();
                    if (type.equalsIgnoreCase("delete")) {
                        sheet.removeRow(lineCell.getRow());
                        FileOutputStream out =
                                new FileOutputStream("C:\\Directory2\\keys.xlsx");
                        keyworkbook.write(out);
                        out.close();
                    }
                    return lineNumber;
                }
            }
        }
        return 0;
    }

    synchronized public String getKeyValues(String key) throws IOException, ParseException {
        String type = "get";
        double lineNumber = getKeyRowNum(key,type);
        if(lineNumber!=0) {
            FileInputStream keyfileIn = new FileInputStream("C:\\Directory2\\db.xlsx");
            //Workbook workbook = WorkbookFactory.create(fileIn);
            Workbook keyworkbook = new XSSFWorkbook(keyfileIn);
            Sheet sheet = keyworkbook.getSheetAt(0);
            Row row = sheet.getRow((int) lineNumber);
            Cell cell = row.getCell(1);
            return cell.getStringCellValue();
        }
        else
            return "Key Not Available for Read Operation";

   }

    synchronized public String deleteKeyValues(String key) throws IOException, ParseException {
    String type = "delete";
       double lineNumber = getKeyRowNum(key,type);
       if(lineNumber!=0) {
           FileInputStream keyfileIn = new FileInputStream("C:\\Directory2\\db.xlsx");
           Workbook keyworkbook = new XSSFWorkbook(keyfileIn);
           Sheet sheet = keyworkbook.getSheetAt(0);
           Row row = sheet.getRow((int) lineNumber);
           sheet.removeRow(row);
           FileOutputStream out = new FileOutputStream("C:\\Directory2\\db.xlsx");
           keyworkbook.write(out);
           out.close();
           return "Key-Value Deleted";
       }
       else
           return "Key Not Available for Deletion Operation";
   }
}



