package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A43_RbaGov_CashRate_AU extends HtmlParser {
    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile("(\\d\\.\\d\\d)",Pattern.MULTILINE|Pattern.DOTALL);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.rba.gov.au/";
    }



    public A43_RbaGov_CashRate_AU(ThreadRunnerTaskEventsDispacher disp,
                                  SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }
}

