package com.example.easypoi.poitest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.example.easypoi.pojo.MsgClient;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 大数据量导出 Created by JueYue on 2017/9/7.
 */
public class ExcelExportBigData {

    @Test
    public void bigDataExport() throws Exception {

        Workbook workbook = null;
        Workbook workbook1 = null;
        Date         start    = new Date();
        ExportParams params   = new ExportParams("大数据测试", "测试");

        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 10000000; i++) {
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("JueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
            if (list.size()==10000){
                 workbook1 = ExcelExportUtil.exportBigExcel(params, MsgClient.class, list);
                list.clear();
            }
        }
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/home/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/home/excel/ExcelExportBigData.MybigDataExport6.xlsx");
        workbook1.write(fos);
        fos.close();
    }

}
