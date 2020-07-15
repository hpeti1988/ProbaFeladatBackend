package hu.horvathpeter.probaFeladat.controller;

import hu.horvathpeter.probaFeladat.model.Vasarlas;
import hu.horvathpeter.probaFeladat.repository.VasarlasRepository;
import hu.horvathpeter.probaFeladat.service.VasarlasService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class VasarlasController {

    @Autowired
    private VasarlasService vasarlasService;

    @Autowired
    private VasarlasRepository vasarlasRepository;

    @GetMapping("/vasarlas/page")
    public Page<Vasarlas> findAllVasarlas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        return vasarlasRepository.findAll(PageRequest.of(page,size));
    }

    @GetMapping("/vasarlas")
    public List<Vasarlas> retrieveAllVasarlas() {
        return vasarlasService.retrieveAllVasarlas();
    }

    @GetMapping("/vasarlas/{id}")
    public Vasarlas retrieveVasarlasById(@PathVariable int id) {
        return vasarlasService.retrieveVasarlasById(id);
    }

    @PostMapping("/bolt/{id}/vasarlas")
    public void createVasarlas(@RequestBody Vasarlas vasarlas, @PathVariable int id) {
        vasarlasService.createVasarlas(vasarlas, id);
    }

    @PutMapping("/vasarlas/{id}")
    public void updateVasarlas(@RequestBody Vasarlas vasarlas, @PathVariable int id) {
        vasarlasService.updateVasarlas(vasarlas, id);
    }

    @DeleteMapping("/vasarlas/{id}")
    public void deleteVasarlas(@PathVariable int id) {
        vasarlasService.deleteVasarlas(id);
    }

    @GetMapping("/vasarlas/export")
    public void exportVasarlasToExcel(HttpServletResponse response) throws Exception {

        List<Vasarlas> vasarlasList = vasarlasService.retrieveAllVasarlas();

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Vasarlas");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("esemenydatumido");
        header.createCell(2).setCellValue("vasarlasosszeg");
        header.createCell(3).setCellValue("penztargepazonosito");
        header.createCell(4).setCellValue("partnerid");
        header.createCell(5).setCellValue("boltid");

        int rowNum = 1;

        for (Vasarlas vasarlas : vasarlasList) {
            Row aRow = sheet.createRow(rowNum++);
            aRow.createCell(0).setCellValue(vasarlas.getId());
            aRow.createCell(1).setCellValue(vasarlas.getEsemenyDatumIdo());
            aRow.createCell(2).setCellValue(vasarlas.getVasarlasOsszeg());
            aRow.createCell(3).setCellValue(vasarlas.getPenztargepAzonosito());
            aRow.createCell(4).setCellValue(vasarlas.getPartnerId());
            aRow.createCell(5).setCellValue(vasarlas.getBolt().getId());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment; filename=vasarlas.xlsx");
        FileOutputStream fileOut = new FileOutputStream("vasarlas.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.write(response.getOutputStream());
    }
}
