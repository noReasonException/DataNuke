package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A31_GovUk_PublicationsConsumerPriceInflation_UK extends HtmlParser {
    @Override
    protected Pattern onPatternLoad() {
        return null;
    }

    @Override
    protected String onUrlLoad() {
        return null;
    }

    @Override
    protected Double onValueExtract(String tmpString) {
        return null;
    }

    public A31_GovUk_PublicationsConsumerPriceInflation_UK(ThreadRunnerTaskEventsDispacher disp,
                                                           AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
}
