package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;

import java.util.regex.Pattern;

public class A20_OnsGov_CpiAnnualRate_ChangeOver12Months_UK extends CsvParser {
    public A20_OnsGov_CpiAnnualRate_ChangeOver12Months_UK(ThreadRunnerTaskEventsDispacher disp,
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
