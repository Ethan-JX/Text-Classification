package com.ethan.util;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by Ethan on 2017/3/29.
 */
public class PassageClassifyToWordUtil {

    public static String passageToWord(String passage)throws IOException {
        Analyzer analyzer = new IKAnalyzer(true);
        StringReader reader = new StringReader(passage);
        TokenStream ts = analyzer.tokenStream("", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        StringBuffer stringBuffer = new StringBuffer();
        while (ts.incrementToken()) {
            stringBuffer.append(term.toString()).append(" ");
        }
        analyzer.close();
        reader.close();
        return stringBuffer.toString();
    }


    public static void main(String[] args) throws Exception {
        String passage = "大数据彻底将是非常厉害的";
        System.out.print(PassageClassifyToWordUtil.passageToWord(passage));
    }
}
