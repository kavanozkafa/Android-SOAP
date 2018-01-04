package sammas.countrybycurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tvulke,tvulkekod,tvmoney,tvmoneycode,tvulkeler;
    EditText et;
    String Ulke;
    Spinner sp;
    ArrayList listulkeler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btnservis);
        tvulke = (TextView)findViewById(R.id.txtulke);
        tvulkekod = (TextView)findViewById(R.id.txtcode);
        tvmoney = (TextView)findViewById(R.id.txtmoney);
        tvmoneycode = (TextView)findViewById(R.id.txtmoneycode);
        tvulkeler=(TextView)findViewById(R.id.txtulkeler);
        sp=(Spinner) findViewById(R.id.spUlkeler);

        AsenkronCagri2 as2 = new AsenkronCagri2();
        as2.execute();

   et = (EditText)findViewById(R.id.etCountry);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ulke = et.getText().toString();
                AsenkronCagri as = new AsenkronCagri();
                as.execute(Ulke);

            }
        });


        //TODO spiner click item
    }

    public class AsenkronCagri extends AsyncTask<String,Void,String>
    {
            String getValue(String tag, Element element) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }

        @Override
        protected String doInBackground(String... params) {
            CallSoap cs = new CallSoap();
            String response = cs.GetMoneyByCountry(params[0]);
            return response;
        }





        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");
                for (int i=0; i<nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        tvulke.setText(getValue("Name", element2));
                        tvulkekod.setText(getValue("CountryCode", element2));
                        tvmoney.setText(getValue("Currency", element2));
                        tvmoneycode.setText(getValue("CurrencyCode", element2));
                     //   Toast.makeText(MainActivity.this, getValue("Name", element2)+" "+getValue("Currency", element2), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {e.printStackTrace();}


        }

    }

    public class AsenkronCagri2 extends AsyncTask<String,Void,String>
    {
        String getValue(String tag, Element element) {

            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }


        @Override
        protected String doInBackground(String... string) {
            CallSoapSpinner css=new CallSoapSpinner();
            String ulkeler=css.GetCountries(string);
            return ulkeler;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");
                for (int i=0; i<nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                            tvulkeler.setText(getValue("Name", element2));

       //  ArrayAdapter<ArrayList> adapter1 = new ArrayAdapter<ArrayList>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,list);

              //          sp.setAdapter(adapter1);
                    }
                }
            } catch (Exception e) {e.printStackTrace();}

        }

}}

