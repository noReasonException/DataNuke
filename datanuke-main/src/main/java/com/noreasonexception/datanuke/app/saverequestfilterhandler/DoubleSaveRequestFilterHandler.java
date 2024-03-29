package com.noreasonexception.datanuke.app.saverequestfilterhandler;


import com.noreasonexception.datanuke.app.fileprotocol.ClientRequirementFileProtocol;
import com.noreasonexception.datanuke.app.fileprotocol.FuseFileProtocol;
import com.noreasonexception.datanuke.app.fileprotocol.IntervalCsvFileProtocol;
import com.noreasonexception.datanuke.app.fileprotocol.ListToFileProtocol;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.ClassNotRegisteredException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.InconsistentStateException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.MailformedFileException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.GenericSaveRequestFilterException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.interfaces.MostRecentUnixTimestampFileFilter;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;

/***
 * This is the main implementation of Save Subsystem
 * Saves the values into a .csv file , just numbers seperated by commas!
 *
 * How it works?
 *
 * At first The DoubleSaveRequestFilterHandler reads all the contexts of the file given in constructor
 * Lets suppose that the file contains n values
 *
 * Now , the object has n values but 0 classes . so it is in Invalid State .
 * When the object is in invalid state ,every operation ,except .submitClass(),
 * will throw an InconsistentStateException .
 *
 * The object , to be consistent , expect n .submitClass() calls
 * The first call will correlate the first class into the first value came from file and vice versa
 *
 * =====================================================================================================================
 * Every call into .submitValue() will return true if the RequestParser class finds an new value
 *
 */
public class DoubleSaveRequestFilterHandler implements SaveRequestFilterHandler<Double> {
    private Hashtable<String,Integer>   classIDs;
    private ArrayList<Double>           classValues;
    private java.lang.String            directory;
    private java.lang.String            lastClassAquiredLock;
    private DataProvider                fileDataProvider;
    private ListToFileProtocol<Double>  internalCsvFileProtocol;
    private ListToFileProtocol<Double>  clientRequirementFileProtocol;
    private ListToFileProtocol<Double>  fuseFileProtocol;
    private static int                  cnt=0;


    public DoubleSaveRequestFilterHandler(String directory){
        this.classIDs=new Hashtable<>();
        this.directory =directory;
        this.internalCsvFileProtocol=new IntervalCsvFileProtocol(this.directory);
        this.clientRequirementFileProtocol=new ClientRequirementFileProtocol(this.directory);
        this.fuseFileProtocol=new FuseFileProtocol();

    }

    /****
     * Call always before any operation , otherwise , a InconsistentStateException will be thrown.
     * @return this object
     * @throws GenericSaveRequestFilterException in case of any error(IOE or corrupted file)
     */
    @Override
    public DoubleSaveRequestFilterHandler buildFromFile() throws GenericSaveRequestFilterException {
        this.classValues=this.fileContextToArray();
        return this;
    }

    /****
     * .fileContextToString()
     * just reads the initial file data returs it as plain string
     * @return the contexts of file as String
     * @throws GenericSaveRequestFilterException in case any type of IOException
     */
    /*Package-Private*/String fileContextToString() throws GenericSaveRequestFilterException {
        Path p;
        return DataProvider.Utills.DataProviderToString(this.fileDataProvider = new FileDataProvider(
                p = Paths.get(getMostRecentFile(this.directory))));

    }
    @SuppressWarnings("unchecked")
    private static String getMostRecentFile(String directory){
        List e;
        (e=Arrays.asList(
                new File(directory).
                        list(new MostRecentUnixTimestampFileFilter()))).
                sort((a1,a2)->{
                    String ref1,ref2;
                    return Integer.valueOf((ref1=(String)a1).substring(0,ref1.length()-4))<
                        Integer.valueOf((ref2=(String)a2).substring(0,ref2.length()-4))?1:-1;
        });
        return directory+e.get(0);
    }

    /*Package-Private*/ ArrayList<Double> fileContextToArray()throws GenericSaveRequestFilterException {
        String tmp="none";
        try{
            String str = fileContextToString();
            ArrayList<Double> retval=new ArrayList<>();
            for (String s:str.split(",")){ //NumberFormatException
                retval.add(Double.valueOf(tmp=s));
            }
            return retval;
        }catch (NumberFormatException|NoSuchElementException e){
            throw new MailformedFileException(
                    "The parser detected something is not an number "+e.getMessage()+"("+tmp+")",e);
        }

    }

    /****
     * gets the id of a class by using its name
     *
     * @param className the class name as string
     * @return the class id to be used in this.classValues
     * @throws InconsistentStateException in case of calling this method without call .buildFromFile() first
     */
    /*Package-Private*/ int getIdByClassObj(String className) throws GenericSaveRequestFilterException {
        if (this.classValues==null)throw new InconsistentStateException();
        Integer id;
        if((id=classIDs.get(className))==null)return -1;
        return id;

    }

    @Override
    public boolean sameValueSituation(String className) throws GenericSaveRequestFilterException {
        return __submitValue(className, getClassesValues().get(getIdByClassObj(className)),false);
    }

    protected Object getLockObject(){return this;}

    /***
     * just a funky getter
     */
    protected ArrayList<Double> getClassesValues(){
        return this.classValues;
    }


    /****
     * a thin wrapper over real __saveCSVContext
     * Why?
     *      After a request of changed requirements , i am forced to save the actual contents into two files
     *      one file is the <<interface>> with another product . It is a terrible way but , whatever the client wants :/
     *
     *      starting with version 4.0 , all content will be saved in two files
     * @see .saveCSVContext()
     */
    protected boolean saveContext(int issuingClassID){
        return  //this.internalCsvFileProtocol.saveList(this.classValues,null) &&
                //this.clientRequirementFileProtocol.saveList(this.classValues,new Object[]{issuingClassID})&&
                this.fuseFileProtocol.saveList(this.classValues,new Object[]{issuingClassID});

    }
    @Override
    public boolean submitValue(String klassName, Double value) throws GenericSaveRequestFilterException {
        return __submitValue(klassName,value,true);
    }
    /****
     * the real submitValue()
     * @param klassName the class submitted the value
     * @param value the actual value
     * @param sameRejectionCheck , if a request for a submit value received ,
     *                           with this option engaged , if the value is the same the call will be just return false (will fail)
     * @return true in success.
     */
    synchronized public boolean __submitValue(String klassName, Double value,boolean sameRejectionCheck) throws GenericSaveRequestFilterException {
        synchronized (getLockObject()){
            lastClassAquiredLock=klassName;
            try {
                int id;
                if(this.classValues.size()!=this.classIDs.size())
                    throw new InconsistentStateException(classValues.size()+"-"+classIDs.size());
                if((id= getIdByClassObj(klassName))==-1){
                    throw new ClassNotRegisteredException(klassName);
                }

                if(!sameRejectionCheck || getClassesValues().get(id).compareTo(value)!=0){
                    getClassesValues().set(id,value);
                    saveContext(id);
                    return true;
                }
                return false;
            }catch (Exception e){
                //TODO : Fix
                return false;
            }
        }

    }

    /****
     * Submit a class by his name before use
     *
     * @param klassName the class name
     * @return  true for success
     * @throws InconsistentStateException
     */
    @Override
    synchronized public boolean submitClass(String klassName) throws InconsistentStateException {
        if(this.classValues==null)throw new InconsistentStateException();
        this.classIDs.put(klassName,cnt);
        cnt+=1;
        return true;
    }
}
