package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;

import java.util.regex.Pattern;

public class A19_OnsGov_ManufacturingProductionMomm_Percent_UK extends CsvParser {
    public A19_OnsGov_ManufacturingProductionMomm_Percent_UK(ThreadRunnerTaskEventsDispacher disp,
                                                             AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    protected Pattern onPatternLoad(){
        return null;

    }
    protected String    onUrlLoad(){
        return "https://www.ons.gov.uk/generator?format=csv&uri=/economy/economicoutputandproductivity/output/timeseries/k27y/diop";
    }
    protected Double    onValueExtract(Object context){
        String e[];
        return Double.valueOf((e = context.toString().split(","))[e.length - 1].replace("\"",""));
    }

}
