package stt.system.file.simfile;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.format.CellElapsedFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import stt.system.file.recordfile.RecordFile;

import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class SimFile {
    public ArrayList getAddressList() { //주소 정답 목록 가져오는 메소드
        ArrayList addlist = new ArrayList(); //전체 주소 목록
        ArrayList answer= new ArrayList(); //정답문장 목록
        RecordFile r = new RecordFile();
        try { //문장번호를 파일이름에서 불러와서 그 숫자에 맞는 행을 리스트에 넣어서 유사도 처리 클래스로 보내기
            String isDir ="C://recordFile/";
            ArrayList filename = new ArrayList();
            for (File info : new File(isDir).listFiles()) { //파일이름 목록을 filename에 받아오기 -> 1.wav같은식으로
                if (info.isFile()) {
                    filename.add(info.getName());
                }
            }
            int[] addnum = new int[filename.size()]; //문장 번호 목록
            for(int i = 0; i<filename.size();i++){
                String name = (String) filename.get(i);
                int Idx = name .lastIndexOf(".");
                String fname = name.substring(0, Idx);
                addnum[i] = Integer.parseInt(fname); //addnum 은 문장의 번호
            }
            //문장 번호에 해당되는 엑셀문장 추출
            String splitBy = ",";
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\USER\\Desktop\\인턴\\0712\\주소_테스트_데이터.csv"),"euc-kr"));
            String line;
            while((line = br.readLine()) != null){
                String[] b = line.split(splitBy);
                addlist.add(b[0]);
            }
            br.close();
            //addlist에서 addnum번째 문장들을 answer에 넣기
            for(int j = 0; j< addnum.length; j++){
                answer.add(addlist.get(addnum[j]-1));
            }
            return answer;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }
    public static void makeSimExcel(ArrayList answer, ArrayList sttlist, ArrayList sim) throws Exception{
        XSSFWorkbook xssfWb = null;
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        XSSFCell xssfCell = null;
        try{
            int rowNo = 0;
            xssfWb = new XSSFWorkbook();
            xssfSheet = xssfWb.createSheet("similarity");
            XSSFFont font = xssfWb.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short)20);
            font.setBold(true);
            font.setColor(new XSSFColor(Color.black,null));

            CellStyle cellStyle = xssfWb.createCellStyle();
            xssfSheet.setColumnWidth(0,(xssfSheet.getColumnWidth(0))+(short)2048);
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            xssfSheet.addMergedRegion(new CellRangeAddress(0,1,0,4));
            xssfRow = xssfSheet.createRow(rowNo++);
            xssfCell = xssfRow.createCell((short)0);
            xssfCell.setCellStyle(cellStyle);
            xssfCell.setCellValue("유사도 분석 결과");

            xssfSheet.createRow(rowNo++);


            xssfRow = xssfSheet.createRow(rowNo++);

            //셀에 값 입력 : 첫 열에는 정답문장, 두번째 열에는 stt, 마지막유사도
            xssfCell = xssfRow.createCell((short)0);
            xssfCell.setCellValue("정답문장");
            xssfCell = xssfRow.createCell((short)1);
            xssfCell.setCellValue("STT결과문장");
            xssfCell = xssfRow.createCell((short)2);
            xssfCell.setCellValue("유사도 결과");

            for(int i=0; i<sttlist.size(); i++) {
                xssfRow = xssfSheet.createRow(rowNo++);
                xssfCell = xssfRow.createCell((short)0);
                String string1 = (String) answer.get(i);
                xssfCell.setCellValue(string1);
                xssfCell = xssfRow.createCell((short)1);
                String string2 = (String) sttlist.get(i);
                xssfCell.setCellValue(string2);
                xssfCell = xssfRow.createCell((short)2);
                Float s3 = (Float) sim.get(i);
                String string3 = String.format("%.1f",s3*100);
                xssfCell.setCellValue(string3+"%");
            }

            String localFile = "C:\\Users\\USER\\Desktop\\인턴\\" + "result" +".xlsx";

            File file = new File(localFile);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            xssfWb.write(fos);

            if(fos != null)fos.close();
        }catch (Exception e){

        }
    }
}
