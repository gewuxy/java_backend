package cn.medcn.common.utils;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.supports.ExcelField;
import cn.medcn.common.supports.FileTypeSuffix;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/2/15.
 */
public class ExcelUtils {


    /**
     * 创建workbook对象
     *
     * @param workbookName 工作薄名称
     * @param headers      表头
     * @param datas        表数据
     * @return Workbook 用headers和datas填充后的表格文件
     */

    public static Workbook writeExcel(String workbookName, String[] headers, Collection<String[]> datas) {
        Workbook wb = createWorkbook(workbookName);
        Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(workbookName));
        createHeaderRow(sheet, headers);
        fillDatas(sheet, datas);
        return wb;
    }


    public static void outputWorkBook(String fileName, Workbook workbook, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] bytes = os.toByteArray();
        response.setContentType("application/msexcel;charset=gb2312");
        response.setHeader("Content-disposition", "attachment;filename= " + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 只将第一个sheet转为list
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static List<Object[]> readSingleExcel(File file) throws Exception {
        List<Object[]> list = Lists.newArrayList();
        InputStream is = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(is);
        getSingleSheet(wb, 0, list);
        return list;
    }

    /**
     * 解析Excel文件为List
     *
     * @param file
     * @return
     */
    public static List<Object[]> readExcel(File file) throws Exception {
        List<Object[]> list = Lists.newArrayList();
        InputStream is = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(is);
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            getSingleSheet(wb, sheetIndex, list);
        }
        return list;
    }


    private static void getSingleSheet(Workbook wb, Integer sheetIndex, List<Object[]> list) {
        int totalColumn = 0;
        Sheet sheet = wb.getSheetAt(sheetIndex);
        for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (rowIndex == 1) {
                totalColumn = row.getPhysicalNumberOfCells();
            }
            Object[] dataList = new Object[totalColumn];
            for (int cellIndex = 0; cellIndex < totalColumn; cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                }
                dataList[cellIndex] = cell == null ? "" : cell.getStringCellValue();
            }
            list.add(dataList);
        }
    }


    private static void createHeaderRow(Sheet sheet, String[] headers) {
        if (headers == null) {
            return;
        }
        Row row = sheet.createRow(0);
        for (int colIdx = 0; colIdx < headers.length; colIdx++) {
         //   sheet.setColumnWidth(colIdx,headers[colIdx].getBytes().length*256);
            fillCellData(row, colIdx, headers[colIdx]);
        }
    }

    private static void fillDatas(Sheet sheet, Collection<String[]> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        int rowIdx = 0;
        for (String[] dataArr : datas) {
            rowIdx++;
            Row row = sheet.createRow(rowIdx);
            for (int colIdx = 0; colIdx < dataArr.length; colIdx++) {
                fillCellData(row, colIdx, dataArr[colIdx]);
            }
        }
    }

    private static Workbook createWorkbook(String fileName){
        boolean isXls = fileName.endsWith(FileTypeSuffix.EXCEL_SUFFIX_XLS.suffix);
        Workbook wb = isXls?new HSSFWorkbook():new XSSFWorkbook();
        return wb;
    }

    /**

     * 根据ExcelData列表写excel
     *
     * @param workbookName
     * @param dataList
     * @return
     * @throws SystemException
     */
    public static Workbook writeExcel(String workbookName, List<?> dataList, Class actualClazz) throws SystemException {
        Workbook wb = createWorkbook(workbookName);
        if(!CheckUtils.isEmpty(dataList)){
            Object object = dataList.get(0);
            Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(workbookName));
            List<String> headers = Lists.newArrayList();
            Field[] fields = actualClazz.getDeclaredFields();
            int listLoop = 0;
            int listSize = 0;

            for(Field field : fields){
                ExcelField excelField = field.getAnnotation(ExcelField.class);

                if(excelField != null){
                    if (Collection.class.isAssignableFrom(field.getType())){
                        Collection subList = (Collection) ReflectUtils.getFieldValue(object, field);
                        for (int index = 0 ; index < subList.size(); index++){
                            headers.add(excelField.columnIndex()+index, String.format(excelField.title(),index+1));
                        }
                        listLoop ++;
                        listSize += subList.size();
                    }else{
                        if (listLoop > 0){
                            headers.add(excelField.columnIndex()+listSize, excelField.title());
                        }else{
                            headers.add(excelField.columnIndex(), excelField.title());
                        }
                    }
                }
            }
            String[] headArray = new String[headers.size()];
            createHeaderRow(sheet, headers.toArray(headArray));
            fillData(sheet, dataList, true);
        }
        return wb;
    }

    /**
     * 将excel内容读到ExcelData对象集合中
     * @param file
     * @param hasHead
     * @param excelDataClazz
     * @return
     */
    public static List<?> readToExcelDataList(File file, boolean hasHead, Class excelDataClazz){
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            List<Object> excelDataList = Lists.newArrayList();
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet;
            int rowStartNum;
            Row row;
            for(int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++){
                rowStartNum = hasHead?1:0;
                sheet = wb.getSheetAt(sheetIndex);
                for(int rowNum = rowStartNum; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++){
                    row = sheet.getRow(rowNum);
                    excelDataList.add(createExcelDataFromRow(row, excelDataClazz));
                }
            }
            return excelDataList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 将excel第一张表内容读到ExcelData对象集合中
     * @param file
     * @param hasHead
     * @param excelDataClazz
     * @return
     */
    public static List<?> readFirstSheetToExcelDataList(File file, boolean hasHead, Class excelDataClazz){
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            List<Object> excelDataList = Lists.newArrayList();
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet;
            int rowStartNum;
            Row row;
            rowStartNum = hasHead?1:0;
            sheet = wb.getSheetAt(0);
            for(int rowNum = rowStartNum; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++){
                row = sheet.getRow(rowNum);
                excelDataList.add(createExcelDataFromRow(row, excelDataClazz));
            }

            return excelDataList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private static Object createExcelDataFromRow(Row row, Class excelDataClazz){
        if(row == null){
            return null;
        }
        try {
            Object data = excelDataClazz.newInstance();
            Method method;
            Field[] fields = excelDataClazz.getDeclaredFields();
            ExcelField excelField;
            Cell cell;
            for(int colIndex = 0; colIndex < row.getPhysicalNumberOfCells(); colIndex++){
                cell = row.getCell(colIndex);
                if(cell != null){
                    cell.setCellType(CellType.STRING);
                }
                for(Field field : fields){
                    excelField = field.getAnnotation(ExcelField.class);
                    if(excelField != null && excelField.columnIndex() == colIndex){
                        method =  ReflectUtils.getWriteAbleMethod(excelDataClazz, field);
                        method.setAccessible(true);
                        method.invoke(data, cell == null?"":cell.getStringCellValue());
                        break;
                    }
                }
            }
            return data;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据excelData填充excel
     * @param sheet
     * @param dataList
     * @param hasHead
     * @throws SystemException
     */
    private static void fillData(Sheet sheet, List<?> dataList, boolean hasHead) throws SystemException {
        if (CheckUtils.isEmpty(dataList)) {
            return;
        }
        int row = hasHead ? 1 : 0;
        String data;
        Method fieldGetMethod;
        Row sheetRow;
        for (Object excelData : dataList) {
            sheetRow = sheet.createRow(row);
            Field[] fields = excelData.getClass().getDeclaredFields();
            for (Field field : fields) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                // 设置列宽
                sheet.setColumnWidth(excelField.columnIndex(),10*512);

                if (excelField != null) {

                    try {
                        fieldGetMethod = ReflectUtils.getReadOnlyMethod(excelData.getClass(), field);
                        fieldGetMethod.setAccessible(true);
                        if (Collection.class.isAssignableFrom(field.getType())){
                            Collection subList = (Collection) ReflectUtils.getFieldValue(excelData, field);
                            if (!CheckUtils.isEmpty(subList)){
                                for (int index = 0 ; index < subList.size(); index++){
                                    Iterator it = subList.iterator();
                                    while (it.hasNext()){
                                        data = (String) it.next();
                                        fillCellData(sheetRow, excelField.columnIndex()+index, data);
                                        index++;
                                    }

                                }
                            }
                        }else{
                            if (Integer.class.isAssignableFrom(field.getType())){
                                data = fieldGetMethod.invoke(excelData).toString();
                            } else {
                                data = (String) fieldGetMethod.invoke(excelData);
                            }
                            fillCellData(sheetRow, excelField.columnIndex(), data);
                        }

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            row++;
        }
    }

    private static void fillCellData(Row row, int colIdx, String data) {
        Cell cell = row.createCell(colIdx);
        cell.setCellValue(data);
    }

    /**
     * 按列的下标位置以及行分组来合并单元格
     *
     * @param workbook
     * @param rowsPerGroup  每次合并的行的数目
     * @param colIndexArray 要合并的列的下标位置
     * @param hasHeader     是否有标题行
     * @throws IOException
     */
    public static void mergeRows(Workbook workbook, int rowsPerGroup, int[] colIndexArray, boolean hasHeader) throws IOException {
        if (workbook == null) {
            return;
        }
        CellStyle style = workbook.createCellStyle(); // 样式对象
        style.setVerticalAlignment(style.getVerticalAlignmentEnum().CENTER);  // 垂直
        style.setAlignment(style.getAlignmentEnum().CENTER);             // 水平

        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int totalRows = sheet.getPhysicalNumberOfRows();
            if (totalRows == 0) {
                continue;
            }
            int startRow = hasHeader ? 1 : 0;
            if (colIndexArray != null) {
                for (int colIndex : colIndexArray) {
                    for (int rowNum = startRow; rowNum < totalRows; rowNum += rowsPerGroup) {
                        CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, rowNum + rowsPerGroup - 1, colIndex, colIndex);
                        sheet.addMergedRegion(cellRangeAddress);

                    }
                }
            }

        }
    }

    /**
     * 根据Map集合 获取用户分组数据来合并单元格
     * @param workbook
     * @param mapData
     * @param colIndexArray
     * @param hasHeader
     * @throws IOException
     */
    public static void mergeExcelRows(Workbook workbook, Map<Integer,List> mapData, int[] colIndexArray, boolean hasHeader)throws IOException{
        if (workbook == null) {
            return;
        }
        CellStyle style = workbook.createCellStyle(); // 样式对象
        style.setVerticalAlignment(style.getVerticalAlignmentEnum().CENTER);  // 垂直
        style.setAlignment(style.getAlignmentEnum().CENTER);

        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int totalRows = sheet.getPhysicalNumberOfRows();
            if (totalRows == 0) {
                continue;
            }
            int startRow = hasHeader ? 1 : 0;

            if (colIndexArray != null) {
                int mergeRows = 0;
                for (int colIndex : colIndexArray) {
                    if(mapData != null && mapData.size()!=0){
                        for (Integer key : mapData.keySet()){
                            mergeRows = mapData.get(key).size();
                            if (mergeRows>1){
                                for (int rowNum = startRow; rowNum < totalRows; rowNum += mergeRows ){
                                    CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, rowNum + mergeRows - 1, colIndex, colIndex);
                                    sheet.addMergedRegion(cellRangeAddress);
                                    startRow = rowNum + mergeRows;
                                    break;
                                }
                            } else {
                                startRow ++;
                            }
                            continue;
                        }
                    }
                    startRow = 1;
                }
            }

        }
    }

    public static void createMergeExcelByMap(String fileName, String[] heads,List<String[]> datas, Map<Integer,List> mapList, HttpServletResponse response, int[] colindexs) throws IOException {
        Workbook workbook = writeExcel(fileName, heads, datas);
        // 合并
        mergeExcelRows(workbook, mapList, colindexs, true);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] bytes = os.toByteArray();

        response.setContentType("application/msexcel;charset=gb2312");
        response.setHeader("Content-disposition", "attachment;filename= " + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 创建合并单元格Excel
     * @param fileName
     * @param workbook
     * @param mapList  要合并的数据
     * @param colIndexArray 需要合并的列数组
     * @param response
     * @throws IOException
     */
    public static void createMergeExcel(String fileName,Workbook workbook,Map<Integer,List> mapList,int[] colIndexArray, HttpServletResponse response) throws IOException {
        // 合并单元格
        mergeExcelRows(workbook,mapList,colIndexArray,true);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] bytes = os.toByteArray();
        response.setContentType("application/msexcel;charset=gb2312");
        response.setHeader("Content-disposition", "attachment;filename= " + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 创建合并单元格Excel
     *
     * @param fileName
     * @param totalCount 需要合并的行数
     * @param response
     * @throws IOException
     */
    public static void createMergeExcel(String fileName, Workbook workbook,int totalCount, int[] colIndexArray, HttpServletResponse response) throws IOException {
        // 合并单元格
        mergeRows(workbook, totalCount, colIndexArray, true);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] bytes = os.toByteArray();
        response.setContentType("application/msexcel;charset=gb2312");
        response.setHeader("Content-disposition", "attachment;filename= " + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    public static void main(String[] args) throws Exception {
        List<Object[]> list = readExcel(new File("D:\\lixuan\\test\\test.xls"));
        for (Object[] objs : list) {
            for (Object obj : objs) {
                System.out.println(obj);
            }
        }
    }
}
