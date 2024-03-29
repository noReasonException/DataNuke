package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.TableParser;

import java.util.regex.Pattern;

public class A14_AbsGov_MomentsInSeasonallyAdjustedSeries_MonthlyChange_AU extends TableParser {
    public A14_AbsGov_MomentsInSeasonallyAdjustedSeries_MonthlyChange_AU(ThreadRunnerTaskEventsDispacher disp,
                                                                         SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return "\n" +
                "<p><a name=\"282218292822995348992822182928229950\"></a>\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>\n" +
                " <br>\n" +
                " <br>\n" +
                "<b>INQUIRIES</b><br>\n" +
                " <br>\n" +
                "For further";
    }

    @Override
    protected Pattern getValuePattern() {
        return Pattern.compile("<font size=\"2\">(.*?) pts </font>");
    }

    @Override
    protected int onRowIndexLoad() {
        return 8;
    }

    @Override
    protected int onCellIndexLoad() {
        return 2;
    }
    @Override
    protected String onUrlLoad() {
        return "http://www.abs.gov.au/ausstats/abs@.nsf/mf/6202.0/";
    }
}
