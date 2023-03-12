package id.kawahedukasi.service;

import com.opencsv.CSVWriter;
import id.kawahedukasi.model.Store;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ExportService {
    public Response exportPdfStore() throws JRException {

        //load template jasper
        File file = new File("src/main/resources/TemplateStore.jrxml");

        //build object jasper report dari load template
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        //create datasource jasper for all data store
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Store.listAll());

//        Map<String, Object> param = new HashMap<>();
//        param.put("DATASOURCE", jrBeanCollectionDataSource);

        //create object jasper print
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        //export jasperprint to byte array
        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        //return response dengan content type pdf
        return Response.ok().type("application/pdf").entity(jasperResult).build();

    }

    public Response exportExcelStore() throws IOException {

        ByteArrayOutputStream outputStream = excelStore();

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=\"store_list_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();

    }

    public ByteArrayOutputStream excelStore() throws IOException {
        List<Store> storeList = Store.listAll();

        //create new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //create sheet
        XSSFSheet sheet = workbook.createSheet("data");

        //set header
        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("count");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("type");
        row.createCell(5).setCellValue("description");
        row.createCell(6).setCellValue("createdAt");
        row.createCell(7).setCellValue("updatedAt");

        //set data
        for(Store store : storeList){
            row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(store.id);
            row.createCell(1).setCellValue(store.name);
            row.createCell(2).setCellValue(store.count);
            row.createCell(3).setCellValue(store.price);
            row.createCell(4).setCellValue(store.type);
            row.createCell(5).setCellValue(store.description);
            row.createCell(6).setCellValue(store.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
            row.createCell(7).setCellValue(store.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

    public Response exportCsvStore() throws IOException {
        //get all data store
        List<Store> StoreList= Store.listAll();

        File file = File.createTempFile("temp", "");

        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        String[] headers = {"id", "name", "email", "phoneNumber", "createdAt", "updateAt"};
        writer.writeNext(headers);
        for(Store store : StoreList){
            String[] data = {
                    store.id.toString(),
                    store.name,
                    store.count.toString(),
                    store.name,
                    store.price.toString(),
                    store.type,
                    store.description,
                    store.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                    store.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            };
            writer.writeNext(data);
        }

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment; filename=\"store_list_excel.csv\"")
                .entity(file).build();

    }
}
