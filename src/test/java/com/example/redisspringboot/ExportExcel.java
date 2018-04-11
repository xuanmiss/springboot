package com.example.redisspringboot;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/2/1
 */
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExportExcel {

    public static void main(String[] args) {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            //加载图片
            bufferImg = ImageIO.read(new File("F:\\Photos\\111.png"));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("F:\\define\\Define Job\\武汉康顺（274201）处罚报告.xls"));
//            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet1 = wb.getSheet("sheet1");

            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
            /**
             dx1 - the x coordinate within the first cell.//定义了图片在第一个cell内的偏移x坐标，既左上角所在cell的偏移x坐标，一般可设0
             dy1 - the y coordinate within the first cell.//定义了图片在第一个cell的偏移y坐标，既左上角所在cell的偏移y坐标，一般可设0
             dx2 - the x coordinate within the second cell.//定义了图片在第二个cell的偏移x坐标，既右下角所在cell的偏移x坐标，一般可设0
             dy2 - the y coordinate within the second cell.//定义了图片在第二个cell的偏移y坐标，既右下角所在cell的偏移y坐标，一般可设0
             col1 - the column (0 based) of the first cell.//第一个cell所在列，既图片左上角所在列
             row1 - the row (0 based) of the first cell.//图片左上角所在行
             col2 - the column (0 based) of the second cell.//图片右下角所在列
             row2 - the row (0 based) of the second cell.//图片右下角所在行
             */
            int aurow1=sheet1.getLastRowNum()+1-10;
            int aucol1=0;
            int aurow2 = aurow1+2;
            int aucol2=2;
            int warow1=aurow1;
            int warow2=aurow2;
            int wacol1=aucol1+3;
            int wacol2=aucol2+4;
            int afrow1=aurow1;
            int afrow2=aurow2;
            int afcol1=wacol1+4;
            int afcol2=wacol2+3;
            int gerow1=aurow1+6;
            int gerow2=aurow2+6;
            int gecol1=aucol1;
            int gecol2=aucol2;
            HSSFClientAnchor auanchor = new HSSFClientAnchor(0, 0, 0, 0, (short) aucol1, aurow1, (short) aucol2, aurow2);
            HSSFClientAnchor waanchor = new HSSFClientAnchor(0, 0, 0, 0, (short) wacol1, warow1, (short) wacol2, warow2);
            HSSFClientAnchor afanchor = new HSSFClientAnchor(0, 0, 0, 0, (short) afcol1, afrow1, (short) afcol2, afrow2);
            HSSFClientAnchor geanchor = new HSSFClientAnchor(0, 0, 0, 0, (short) gecol1, gerow1, (short) gecol2, gerow2);

            //插入图片
            patriarch.createPicture(auanchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

            patriarch.createPicture(waanchor,wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));

            patriarch.createPicture(afanchor,wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));

            patriarch.createPicture(geanchor,wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));

            fileOut = new FileOutputStream("F:\\define\\Define Job\\武汉康顺.pdf");
            // 输出文件
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRowsAndColums() {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            //加载图片
            bufferImg = ImageIO.read(new File("F:\\Photos\\111.png"));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("F:\\define\\Define Job\\武汉康顺（274201）处罚报告.xls"));
//            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet1 = wb.getSheet("sheet1");

            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
            int rows=sheet1.getLastRowNum();
            System.out.println(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}