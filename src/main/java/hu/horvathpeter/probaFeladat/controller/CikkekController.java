package hu.horvathpeter.probaFeladat.controller;

import hu.horvathpeter.probaFeladat.model.Cikkek;
import hu.horvathpeter.probaFeladat.service.CikkekService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CikkekController {

    @Autowired
    private CikkekService cikkekService;

    @GetMapping("/cikkek")
    public List<Cikkek> retrieveAllCikk() {
        return cikkekService.retrieveAllCikk();
    }

    @GetMapping("/cikkek/{id}")
    public Cikkek retrieveCikkById(@PathVariable int id) {
        return cikkekService.retrieveCikkById(id);
    }

    @PostMapping("/cikkek")
    public void createCikk (@RequestBody Cikkek cikkek) {
        cikkekService.createCikk(cikkek);
    }

    @PutMapping("/cikkek/{id}")
    public void updateCikk(@RequestBody Cikkek cikkek, @PathVariable int id) {
        cikkekService.updateCikk(cikkek, id);
    }

    @DeleteMapping("/cikkek/{id}")
    public void deleteCikk(@PathVariable int id) {
        cikkekService.deleteCikk(id);
    }

    @GetMapping("/cikk/export")
    public void exportCikkekToExcel(HttpServletResponse response) throws Exception {

        List<Cikkek> cikkekList = cikkekService.retrieveAllCikk();

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Cikkek");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("cikkszam");
        header.createCell(2).setCellValue("vonalkod");
        header.createCell(3).setCellValue("nev");
        header.createCell(4).setCellValue("mennyisegiegyseg");
        header.createCell(5).setCellValue("nettoegysegar");
        header.createCell(6).setCellValue("verzio");
        header.createCell(7).setCellValue("partnerid");


        int rowNum = 1;

        for (Cikkek cikkek : cikkekList) {
            Row aRow = sheet.createRow(rowNum++);
            aRow.createCell(0).setCellValue(cikkek.getId());
            aRow.createCell(1).setCellValue(cikkek.getCikkSzam());
            aRow.createCell(2).setCellValue(cikkek.getVonalKod());
            aRow.createCell(3).setCellValue(cikkek.getNev());
            aRow.createCell(4).setCellValue(cikkek.getMennyisegEgyseg());
            aRow.createCell(5).setCellValue(cikkek.getNettoEgysegar());
            aRow.createCell(6).setCellValue(cikkek.getVerzio());
            aRow.createCell(7).setCellValue(cikkek.getPartnerId());

        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment; filename=cikkek.xlsx");
        FileOutputStream fileOut = new FileOutputStream("cikkek.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.write(response.getOutputStream());
    }
}
