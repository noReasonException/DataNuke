package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.NoUserAgentRequired;
import com.noreasonexception.loadable.base.etc.UserAgent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/***
 * RequestParser
 * this class represents an abstract parser
 * And what is an parser?
 * A parser is a subsystem who its mission is..
 *
 * 1) Take arbitrary data via a DataProvider in a known interval
 * 2) Retrieves specific information inside these data
 * 3) Informs the saverequestfilterhandler
 * 4) When a new value is discovered , the saverequestfilterhandler.submitValue() returns true .
 * 5) When the @4 happens , the Parser must kill himself
 * @implNote In some steps , the ThreadRunnerTaskEventsDispacher must informed by corresponding methods
 */
abstract public class RequestParser extends AbstractParser{
    protected final int REQUESTS_MAX=5;
    protected final String USER_AGENT_FIELD="User-Agent";


    /***
     * Returns the value filter , to inform the .csv file with the new values!
     * @return the DoubleSaveRequestFilterHandler
     */
    public RequestParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter)
    {
        super(disp,valueFilter);
    }

    /****
     * @Overridable_By_Children
     * This method called when the parser it needs the url of the source
     * @return a plain string containing the URL
     */
    abstract protected String onUrlLoad();



    /***
     * Returns the HttpURLConnection used by convertSourceToText to extract the source information as string
     * @return The HttpURLConnection object //TODO Consider making the RequestParser Handlers only Connection Objects(to be flexible in case of http/https)
     * @throws MalformedURLException in case of bad url form
     * @throws IOException           in case of any IOE (not internet found for example)
     */
    protected HttpURLConnection onConnection() throws MalformedURLException,IOException{
        HttpURLConnection conn=(HttpURLConnection) new URL(onUrlLoad()).openConnection();
        try{
            conn.addRequestProperty(USER_AGENT_FIELD,onUserAgentFieldLoad().getUserAgentField());
        }catch (NoUserAgentRequired e){}
        return conn;
    }
    protected UserAgent onUserAgentFieldLoad() throws NoUserAgentRequired{
        throw new NoUserAgentRequired();
    }



}
