package sammas.countrybycurrency;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sammas on 10.05.2017.
 */

public class CallSoapSpinner {

    public  String GetCountries (String[] Ulkeler)
    {

        String SOAP_ADDRESS = "http://www.webservicex.net/country.asmx";
        String SOAP_ACTION = "http://www.webserviceX.NET/GetCountries";
        String OPERATION_NAME = "GetCountries";
        String WSDL_TARGET_NAMESPACE ="http://www.webserviceX.NET";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("GetCountriesResult");
        pi.setValue(Ulkeler);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
      //  List<String> response;
        String response = null;


        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_ADDRESS);
            httpTransportSE.call(SOAP_ACTION,envelope);




            for (int i=0;i<245;i++)
            {

                response=envelope.getResponse().toString();
                //response.add(envelope.getResponse().toString());
                //list.add(response);
            }

        }
        catch (Exception ex)
        {
            response = ex.toString();
        }

        return  response;
    }

}

