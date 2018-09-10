package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

abstract public class XlsParser extends RequestParser {
    public XlsParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected boolean loop() {
        for (int i = 0; i < REQUESTS_MAX; i++)
        {
            Double tmp;
            try{
                if(informValueFilter(tmp=onValueExtract(getXlsWorkbook()))){
                    System.out.println(tmp);
                    return true;
                }
            }catch (InvalidSourceArchitectureException e){
                throw new RuntimeException(e.getMessage());

            }


        }
        return false;

    }

    abstract protected HSSFSheet getSheet(Workbook workbook);



    /***
     * Using the Apache Poi in order to read every .xls file!
     * WorkBook is the main object which represents every .xls file! we obtain this by
     * getting the InputStream of connection using onConnection() method (provided by RequestParser)
     * @return a brand-new HSSFWorkbook
     */
    protected Workbook getXlsWorkbook() {

        try {

            return new HSSFWorkbook(onConnection().getInputStream());

        } catch (IOException e) {

            e.printStackTrace();
        }
        return new HSSFWorkbook();
    }
}
