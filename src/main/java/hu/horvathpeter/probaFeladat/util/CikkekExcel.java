package hu.horvathpeter.probaFeladat.util;

import hu.horvathpeter.probaFeladat.model.Cikkek;
import hu.horvathpeter.probaFeladat.repository.CikkekRepository;
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
@Component
public class CikkekExcel {

    @Autowired
    private CikkekRepository cikkekRepository;

//    @PostConstruct
    public List<Cikkek> readCikkekExcel() throws FileNotFoundException {
        try {
            FileInputStream file = new FileInputStream(new File("src\\main\\resources\\feladat_adat_20200617.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(3);
            Iterator<Row> rows = sheet.iterator();


            List<Cikkek> cikkekList = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();

                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Cikkek cikkek = new Cikkek();

                int cellIndex = 0;
                DataFormatter formatter = new DataFormatter();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {

                        case 0:
                            cikkek.setId((int)currentCell.getNumericCellValue());
                            break;
                        case 1:
                            String cikkString = formatter.formatCellValue(currentCell);
                            cikkek.setCikkSzam(cikkString);
                            break;
                        case 2:
                            String vonalString = formatter.formatCellValue(currentCell);
                            cikkek.setVonalKod(vonalString);
                            break;
                        case 3:
                            cikkek.setNev(currentCell.getStringCellValue());
                            break;
                        case 4:
                            cikkek.setMennyisegEgyseg(currentCell.getStringCellValue());
                            break;
                        case 5:
                            String nettoString = formatter.formatCellValue(currentCell);
                            cikkek.setNettoEgysegar(Float.parseFloat(nettoString));
                            break;
                        case 6:
                            cikkek.setVerzio((int)currentCell.getNumericCellValue());
                            break;
                        case 7:
                            cikkek.setPartnerId((int)currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                cikkekRepository.save(cikkek);
                cikkekList.add(cikkek);
            }
            workbook.close();
            return cikkekList;


        } catch (IOException e) {
            throw new RuntimeException("Excel fájl beolvasása sikertelen: " + e.getMessage());
        }
    }
}
