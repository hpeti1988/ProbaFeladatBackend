package hu.horvathpeter.probaFeladat.controller;

import hu.horvathpeter.probaFeladat.model.Bolt;
import hu.horvathpeter.probaFeladat.service.BoltService;
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
public class BoltController {

    @Autowired
    private BoltService boltService;

    @GetMapping("/bolt")
    public List<Bolt>retrieveAllBolt() {
    return boltService.retrieveAllBolt();
    }

    @GetMapping("/bolt/{id}")
    public Bolt retrieveOneBolt(@PathVariable int id) {
    return boltService.retrieveBoltById(id);
    }

    @PostMapping("/bolt")
    public void createBolt(@RequestBody Bolt bolt) {
        boltService.createBolt(bolt);
    }

    @PutMapping("/bolt/{id}")
    public void updateBolt(@RequestBody Bolt bolt, @PathVariable int id) {
        boltService.updateBolt(bolt, id);
    }

    @DeleteMapping("/bolt/{id}")
    public void deleteBolt(@PathVariable int id) {
        boltService.deleteBolt(id);
    }

    @GetMapping("/bolt/export")
    public void exportBoltToExcel(HttpServletResponse response) throws Exception {

        List<Bolt> boltList = boltService.retrieveAllBolt();

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Bolt");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("nev");
        header.createCell(2).setCellValue("partnerid");

        int rowNum = 1;

        for (Bolt bolt : boltList) {
            Row aRow = sheet.createRow(rowNum++);
            aRow.createCell(0).setCellValue(bolt.getId());
            aRow.createCell(1).setCellValue(bolt.getNev());
            aRow.createCell(2).setCellValue(bolt.getPartnerId());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment; filename=bolt.xlsx");
        FileOutputStream fileOut = new FileOutputStream("bolt.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.write(response.getOutputStream());
    }


}
