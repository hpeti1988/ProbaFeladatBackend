package hu.horvathpeter.probaFeladat.util;

import hu.horvathpeter.probaFeladat.model.Vasarlas;
import hu.horvathpeter.probaFeladat.repository.BoltRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class VasarlasExcel {

    @Autowired
    private BoltRepository boltRepository;
    @Autowired
    private VasarlasRepository vasarlasRepository;

//    @PostConstruct
    public List<Vasarlas> readVasarlasExcel () throws FileNotFoundException {
        try {
            FileInputStream file = new FileInputStream(new File("src\\main\\resources\\feladat_adat_20200617.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rows = sheet.iterator();

            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat isoFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            List<Vasarlas> vasarlasList = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();

                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Vasarlas vasarlas = new Vasarlas();

                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            vasarlas.setId((int)currentCell.getNumericCellValue());
                            break;
                        case 1:
                            try {
                                vasarlas.setEsemenyDatumIdo(isoFormat.parse(currentCell.getStringCellValue()));
                            }
                            catch (ParseException e) {
                                vasarlas.setEsemenyDatumIdo(isoFormat1.parse(currentCell.getStringCellValue()));
                            }
                            break;
                        case 2:
                            vasarlas.setVasarlasOsszeg((float)currentCell.getNumericCellValue());
                            break;
                        case 3:
                            vasarlas.setPenztargepAzonosito((int)currentCell.getNumericCellValue());
                            break;
                        case 4:
                            vasarlas.setPartnerId((int)currentCell.getNumericCellValue());
                            break;
                        case 5:
                            vasarlas.setBolt(boltRepository.findById((int)currentCell.getNumericCellValue()).get());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                vasarlasRepository.save(vasarlas);
                vasarlasList.add(vasarlas);
            }
            workbook.close();
            return vasarlasList;


        } catch (IOException | ParseException e) {
            throw new RuntimeException("Excel fájl beolvasása sikertelen: " + e.getMessage());
        }
    }
}
