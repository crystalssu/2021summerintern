package stt.system.main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import stt.system.file.recordfile.RecordFile;
import stt.system.file.simfile.SimFile;
import stt.system.process.sim.Simprocess;
import stt.system.process.stt.Transfer;
import java.util.ArrayList;
import static stt.system.process.stt.Transfer.sampleRecognize;
import static stt.system.file.simfile.SimFile.makeSimExcel;
public class Process {
    public static void main(String[] args) throws Exception {
        RecordFile r = new RecordFile();
        SimFile s = new SimFile();
        Simprocess p = new Simprocess();
        ArrayList sttlist = new ArrayList();
        Options options = new Options();
        options.addOption(Option.builder("").required(false).hasArg(true).longOpt("local_file_path").build());
        CommandLine cl = (new DefaultParser()).parse(options, args);
        for (int i = 0; i < r.getRecordFileList().size(); i++) {
            String localFilePath = cl.getOptionValue("local_file_path", (String) r.getRecordFileList().get(i));
            sampleRecognize(localFilePath);
            sttlist.add(Transfer.res);
        }
        ArrayList sim = p.jaccard(sttlist,s.getAddressList());
        System.out.println(s.getAddressList());
        System.out.println(sttlist);
        System.out.println(sim);
        makeSimExcel(s.getAddressList(),sttlist,sim);
    }
}
