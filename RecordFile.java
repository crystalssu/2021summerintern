package stt.system.file.recordfile;
import java.io.File;
import java.util.ArrayList;

public class RecordFile { //하위파일 리스트
   public ArrayList getRecordFileList(){
       String isDir ="C://recordFile/";
       ArrayList filename = new ArrayList();
       for (File info : new File(isDir).listFiles()) {
           if (info.isFile()) {
               filename.add(info.getName());
           }
       }
       ArrayList paths = new ArrayList();
       for(int i=0;i<filename.size();i++){
           String p = (String) filename.get(i);
           paths.add("C://recordFile/"+p);
       }
       return paths;
   }
}
