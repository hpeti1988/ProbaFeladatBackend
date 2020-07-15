package hu.horvathpeter.probaFeladat.util;

import hu.horvathpeter.probaFeladat.model.Vasarlas;
import hu.horvathpeter.probaFeladat.model.VasarlasTetel;
import hu.horvathpeter.probaFeladat.repository.CikkekRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasTetelRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class VasarlasTetelExcel {

    @Autowired
    private VasarlasTetelRepository vasarlasTetelRepository;

    @Autowired
    private VasarlasRepository vasarlasRepository;

    @Autowired
    private CikkekRepository cikkekRepository;

//    @PostConstruct
    public List<VasarlasTetel> readVasarlasTetelExcel() throws FileNotFoundException {
        try {
            FileInputStream file = new FileInputStream(new File("src\\main\\resources\\feladat_adat_20200617.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(4);
            Iterator<Row> rows = sheet.iterator();

            List<VasarlasTetel> vasarlasTetelList = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();

                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                VasarlasTetel vasarlasTetel = new VasarlasTetel();

                int cellIndex = 0;
                DataFormatter formatter = new DataFormatter();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            vasarlasTetel.setId((int)currentCell.getNumericCellValue());
                            break;
                        case 1:
                            String cikkekString = formatter.formatCellValue(currentCell);
                            vasarlasTetel.setPartnerct(cikkekRepository.findById(Integer.parseInt(cikkekString)).get());
                            break;
                        case 2:
                            try {
                                String vasarlasString = formatter.formatCellValue(currentCell);
                                Vasarlas v = vasarlasRepository.findById(Integer.parseInt(vasarlasString)).get();
                                if(v != null) {
                                    vasarlasTetel.setVasarlas(vasarlasRepository.findById(Integer.parseInt(vasarlasString)).get());
                                }
                            } catch (NoSuchElementException e) {

                            }
                            break;
                        case 3:
                            String mennyisegString = formatter.formatCellValue(currentCell);
                            vasarlasTetel.setMennyiseg(Float.parseFloat(mennyisegString));
                            break;
                        case 4:
                            String bruttoString = formatter.formatCellValue(currentCell);
                            vasarlasTetel.setBrutto(Float.parseFloat(bruttoString));
                            break;
                        case 5:
                            String partnerString = formatter.formatCellValue(currentCell);
                            vasarlasTetel.setPartnerId(Integer.parseInt(partnerString));
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                vasarlasTetelRepository.save(vasarlasTetel);
                vasarlasTetelList.add(vasarlasTetel);
            }
            workbook.close();
            return vasarlasTetelList;
        } catch (IOException e) {
            throw new RuntimeException("Excel fájl beolvasása sikertelen: " + e.getMessage());
        }
    }
}
