package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A16_AbsGov_SeasonallyAdjusted_CrossDomesticProduct_AU extends HtmlParser {
    public A16_AbsGov_SeasonallyAdjusted_CrossDomesticProduct_AU(ThreadRunnerTaskEventsDispacher disp,
                                                                 AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    protected Pattern onPatternLoad(){
        return null;

    }
    protected String    onUrlLoad(){
        return null;
    }
    protected Double    onValueExtract(String tmpString){
        return null;
    }
}
