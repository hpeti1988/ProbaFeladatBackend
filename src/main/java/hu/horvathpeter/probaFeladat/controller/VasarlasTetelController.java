package hu.horvathpeter.probaFeladat.controller;

import hu.horvathpeter.probaFeladat.model.VasarlasTetel;
import hu.horvathpeter.probaFeladat.service.VasarlasTetelService;
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
public class VasarlasTetelController {

    @Autowired
    private VasarlasTetelService vasarlasTetelService;

    @GetMapping("/vasarlasTetel")
    public List<VasarlasTetel> retrieveAllVasarlasTetel() {
        return vasarlasTetelService.retrieveAllVasarlasTetel();
    }

    @GetMapping("/vasarlasTetelById/{id}")
    public List<VasarlasTetel> findAllByVasarlas(@PathVariable int id) {
        return vasarlasTetelService.findAllByVasarlas(id);
    }

    @GetMapping("/vasarlasTetel/{id}")
    public VasarlasTetel retrieveVasarlasTetelById(@PathVariable int id) {
        return vasarlasTetelService.retrieveVasarlasTetelById(id);
    }

    @PostMapping("/vasarlas/{vasarlasId}/vasarlasTetel/{cikkId}")
    public void createVasarlasTetel(@RequestBody VasarlasTetel vasarlasTetel, @PathVariable int vasarlasId, @PathVariable int cikkId) {
        vasarlasTetelService.createVasarlasTetel(vasarlasTetel, vasarlasId, cikkId);
    }

    @PutMapping("/vasarlasTetel/{id}")
    public void updateVasarlasTetel(@RequestBody VasarlasTetel vasarlasTetel, @PathVariable int id) {
        vasarlasTetelService.updateVasarlasTetel(vasarlasTetel, id);
    }

    @DeleteMapping("/vasarlasTetel/{id}")
    public void deleteVasarlasTetel(@PathVariable int id) {
        vasarlasTetelService.deleteVasarlasTetel(id);
    }

    @GetMapping("/vasarlasTetel/export")
    public void exportVasarlasTetelToExcel(HttpServletResponse response) throws Exception {

        List<VasarlasTetel> vasarlasTetelList = vasarlasTetelService.retrieveAllVasarlasTetel();

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("VasarlasTetel");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("partnerctid");
        header.createCell(2).setCellValue("mennyiseg");
        header.createCell(3).setCellValue("brutto");
        header.createCell(4).setCellValue("partnerid");

        int rowNum = 1;

        for (VasarlasTetel vasarlasTetel : vasarlasTetelList) {
            Row aRow = sheet.createRow(rowNum++);
            aRow.createCell(0).setCellValue(vasarlasTetel.getId());
            aRow.createCell(1).setCellValue(vasarlasTetel.getPartnerct().getId());
            aRow.createCell(2).setCellValue(vasarlasTetel.getMennyiseg());
            aRow.createCell(3).setCellValue(vasarlasTetel.getBrutto());
            aRow.createCell(4).setCellValue(vasarlasTetel.getPartnerId());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment; filename=vasarlasTetel.xlsx");
        FileOutputStream fileOut = new FileOutputStream("vasarlasTetel.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.write(response.getOutputStream());
    }
}
