package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A36_InstituteForSupplyManagment_MostRecentNMIReport_US extends HtmlParser {
    public A36_InstituteForSupplyManagment_MostRecentNMIReport_US(ThreadRunnerTaskEventsDispacher disp,
                                                                  SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile("(\\d\\d\\.\\d)%");
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.instituteforsupplymanagement.org/ISMReport/NonMfgROB.cfm?navItemNumber=31090";
    }
}
