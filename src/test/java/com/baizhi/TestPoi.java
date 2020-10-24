package com.baizhi;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.Person;
import com.baizhi.entity.Student;
import com.baizhi.entity.Teacher;
import com.baizhi.util.AliyunOssUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPoi {

    @Test
    public void testPoi() throws IOException {
        //在f盘撞见一个表格  并导出数据
        //1.创建Excle文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建工作表
        HSSFSheet sheet = workbook.createSheet();
        //3.创建行 第0行开始
        HSSFRow row = sheet.createRow(0);
        //4.创建单元格 第0个单元格
        HSSFCell cell = row.createCell(0);
        //5.往单元格中输入值
        cell.setCellValue("1111");
        //6.导出Excle表格
        workbook.write(new File("F:/aaa.xls"));
    }

    @Test
    public void testPoiDate() throws IOException {
        //在f盘撞见一个表格  并导出数据
        //1.创建Excle文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建时间格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //为格式设置样式
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //创建单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        //2.创建工作表
        HSSFSheet sheet = workbook.createSheet();
        //为单元格设置宽
        sheet.setColumnWidth(0, 20 * 256);
        //3.创建行 第0行开始
        HSSFRow row = sheet.createRow(0);
        //4.创建单元格 第0个单元格
        HSSFCell cell = row.createCell(0);
        //5.往单元格中输入值
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);
        //6.导出Excle表格
        workbook.write(new File("F:/aaa.xls"));
    }

    @Test
    public void testPoiIn() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("F:/aaa.xls"));
        HSSFSheet sheet = workbook.getSheet("sheet0");
        int lastRowNum = sheet.getLastRowNum(); //最后一行下标
        for (int i = 0; i <= lastRowNum; i++) {
            //String id = sheet.getRow(i).getCell(0).getStringCellValue();
            //String name = sheet.getRow(i).getCell(1).getStringCellValue();
            Date date = sheet.getRow(i).getCell(0).getDateCellValue();
            System.out.println(date);
            //调用dao的插入方法，放入数据库
            //使用批量插入  mybatis <foreach>标签

        }
    }

    @Test
    public void testEasyPoi() throws Exception {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("1", "张三", "123456", new Date()));
        list.add(new Student("2", "李四", "123456", new Date()));
        list.add(new Student("3", "王五", "123456", new Date()));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"),
                Student.class, list);
        workbook.write(new FileOutputStream(new File("F:/2003班.xls")));
        workbook.close();
    }

    @Test
    public void testEasyPoiTeacher() throws Exception {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("1", "张三", "123456", new Date()));
        list.add(new Student("2", "李四", "123456", new Date()));
        list.add(new Student("3", "王五", "123456", new Date()));

        ArrayList<Teacher> list1 = new ArrayList<>();
        list1.add(new Teacher("111", "张老师", list));
        list1.add(new Teacher("222", "叶老师", list));
        list1.add(new Teacher("333", "陈老师", list));


        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机老师", "老师"),
                Teacher.class, list1);
        workbook.write(new FileOutputStream(new File("F:/2003班老师.xls")));
        workbook.close();
    }

    //导出 bendi
    @Test
    public void testPhoto() throws Exception {
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("1", "小黑", "http://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg"));
        list.add(new Person("2", "小红", "http://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg"));
        list.add(new Person("3", "小蓝", "http://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg"));

        ArrayList<Object> list1 = new ArrayList<>();
        //yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg
        for (Person person : list) {
            String[] split = person.getHeadImg().split("/");
            String fileName = split[4];
            System.out.println("fileName--- " + fileName);
            String filePath = "F:\\img\\" + fileName;
            AliyunOssUtil.testDownload("photo/" + fileName, filePath);
            person.setHeadImg(filePath);
            list1.add(person);
        }
        //先将远程网络图片下载到本地  再导出

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户数据", "用户"),
                Person.class, list1);
        workbook.write(new FileOutputStream(new File("F:/2003用户.xls")));
        workbook.close();
    }

    //导入
    @Test
    public void testEasyPoiIn() throws Exception {
        //设置导入得相关参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格的标题行数
        params.setHeadRows(1);  //表头所占行
        //long start = new Date().getTime();
        FileInputStream stream = new FileInputStream(new File("F:/2003用户.xls"));
        List<Person> list = ExcelImportUtil.importExcel(stream, Person.class, params);
        list.forEach(person -> System.out.println(person));
        //Person(id=1, name=小黑, headImg=/excel/upload/img\Person\pic20374222194.JPG)
    }

}
