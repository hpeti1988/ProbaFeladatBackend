package hu.horvathpeter.probaFeladat.util;

import hu.horvathpeter.probaFeladat.model.Bolt;
import hu.horvathpeter.probaFeladat.repository.BoltRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
public class BoltExcel {

    @Autowired
    private BoltRepository boltRepository;

//    @PostConstruct
    public List<Bolt> readBoltExcel () throws FileNotFoundException {
        try {
            FileInputStream file = new FileInputStream(new File("src\\main\\resources\\feladat_adat_20200617.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Bolt> boltList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Bolt bolt = new Bolt();

                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            bolt.setId((int)currentCell.getNumericCellValue());
                            break;
                        case 1:
                            bolt.setNev(currentCell.getStringCellValue());
                            break;
                        case 2:
                            bolt.setPartnerId((int)currentCell.getNumericCellValue());
                            break;

                        default:
                            break;
                    }
                    cellIndex++;
                }
                boltRepository.save(bolt);
                boltList.add(bolt);
            }
            workbook.close();
            return boltList;
        } catch (IOException e) {
            throw new RuntimeException("Excel fájl beolvasása sikertelen: " + e.getMessage());
        }
    }

}

