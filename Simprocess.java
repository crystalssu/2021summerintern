package stt.system.process.sim;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Simprocess { //s.answer은 정답문장
    public ArrayList jaccard(ArrayList sttlist, ArrayList answer) {

        ArrayList jsimlist = new ArrayList(); //유사도 결과 리스트
        for (int j = 0; j < sttlist.size(); j++) {
            String str1 = (String) sttlist.get(j);
            String str2 = (String) answer.get(j);
            Set<Character> s1 = new HashSet<>();
            Set<Character> s2 = new HashSet<>();
            for(int p = 0; p<str1.length(); p++){
                s1.add(str1.charAt(p));
            }
            for(int q = 0; q<str2.length(); q++){
                s2.add(str2.charAt(q));
            }
            float mergeNum = 0;
            float commonNum = 0;

            for(Character ch1:s1){
                for(Character ch2:s2){
                    if(ch1.equals(ch2)){
                        commonNum++;
                    }
                }
            }
            mergeNum = s1.size() + s2.size() - commonNum;
            float jaccard = commonNum/mergeNum;
            jsimlist.add(jaccard);
        }
        return jsimlist;
    }
}
