package com.onurkol.app.calculator.activity.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.MainActivity;
import com.onurkol.app.calculator.lib.AppDataManager;
import com.onurkol.app.calculator.lib.ContextManager;
import com.onurkol.app.calculator.tools.KeyboardController;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.lang.ref.WeakReference;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CurrencyActivity extends AppCompatActivity {
    // Elements
    ImageButton backButton;
    TextView settingName;
    EditText currencyUSD, currencyEUR, currencyTRY, currencyGBP, currencyJPY, currencyCNY;

    static String rateUSDBasedUSD="", rateEURBasedUSD="", rateTRYBasedUSD="", rateGBPBasedUSD="", rateJPYBasedUSD="", rateCNYBasedUSD="";
    static String rateUSDBasedEUR="", rateEURBasedEUR="", rateTRYBasedEUR="", rateGBPBasedEUR="", rateJPYBasedEUR="", rateCNYBasedEUR="";
    static String rateUSDBasedTRY="", rateEURBasedTRY="", rateTRYBasedTRY="", rateGBPBasedTRY="", rateJPYBasedTRY="", rateCNYBasedTRY="";
    static String rateUSDBasedGBP="", rateEURBasedGBP="", rateTRYBasedGBP="", rateGBPBasedGBP="", rateJPYBasedGBP="", rateCNYBasedGBP="";
    static String rateUSDBasedJPY="", rateEURBasedJPY="", rateTRYBasedJPY="", rateGBPBasedJPY="", rateJPYBasedJPY="", rateCNYBasedJPY="";
    static String rateUSDBasedCNY="", rateEURBasedCNY="", rateTRYBasedCNY="", rateGBPBasedCNY="", rateJPYBasedCNY="", rateCNYBasedCNY="";

    // <BUG> Required Context! (for Keyboard Hidden Methods.)
    static WeakReference<Context> contextStatic;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Set Current Activity Context
        ContextManager.Build(this);
        // Load App Data
        AppDataManager.loadApplicationData();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        // L40:
        contextStatic=new WeakReference<>(this);
        // Get Elements
        backButton=findViewById(R.id.backButton);
        settingName=findViewById(R.id.settingName);
        currencyUSD=findViewById(R.id.currencyUSD);
        currencyEUR=findViewById(R.id.currencyEUR);
        currencyTRY=findViewById(R.id.currencyTRY);
        currencyGBP=findViewById(R.id.currencyGBP);
        currencyJPY=findViewById(R.id.currencyJPY);
        currencyCNY=findViewById(R.id.currencyCNY);
        // Set Toolbar Title
        settingName.setText(getString(R.string.currency_converter_text));

        // Check & Get Live Data
        if(!MainActivity.isGetCurrencyData) {
            new Thread(() -> {
                try {
                    // URL List
                    String URL_MAIN="https://api.exchangerate.host/latest?format=xml&symbols=USD,EUR,TRY,GBP,JPY,CNY&places=2";
                    URL urlBasedUSD = new URL(URL_MAIN+"&base=USD");
                    URL urlBasedEUR = new URL(URL_MAIN+"&base=EUR");
                    URL urlBasedTRY = new URL(URL_MAIN+"&base=TRY");
                    URL urlBasedGBP = new URL(URL_MAIN+"&base=GBP");
                    URL urlBasedJPY = new URL(URL_MAIN+"&base=JPY");
                    URL urlBasedCNY = new URL(URL_MAIN+"&base=CNY");

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    // Documents
                    Document docBasedUSD = db.parse(urlBasedUSD.openConnection().getInputStream());
                    docBasedUSD.getDocumentElement().normalize();
                    Document docBasedEUR = db.parse(urlBasedEUR.openConnection().getInputStream());
                    docBasedEUR.getDocumentElement().normalize();
                    Document docBasedTRY = db.parse(urlBasedTRY.openConnection().getInputStream());
                    docBasedTRY.getDocumentElement().normalize();
                    Document docBasedGBP = db.parse(urlBasedGBP.openConnection().getInputStream());
                    docBasedGBP.getDocumentElement().normalize();
                    Document docBasedJPY = db.parse(urlBasedJPY.openConnection().getInputStream());
                    docBasedJPY.getDocumentElement().normalize();
                    Document docBasedCNY = db.parse(urlBasedCNY.openConnection().getInputStream());
                    docBasedCNY.getDocumentElement().normalize();
                    // Nodes
                    NodeList nodesCodeBasedUSD = docBasedUSD.getElementsByTagName("code");
                    NodeList nodesRateBasedUSD = docBasedUSD.getElementsByTagName("rate");
                    NodeList nodesRateBasedEUR = docBasedEUR.getElementsByTagName("rate");
                    NodeList nodesRateBasedTRY = docBasedTRY.getElementsByTagName("rate");
                    NodeList nodesRateBasedGBP = docBasedGBP.getElementsByTagName("rate");
                    NodeList nodesRateBasedJPY = docBasedJPY.getElementsByTagName("rate");
                    NodeList nodesRateBasedCNY = docBasedCNY.getElementsByTagName("rate");

                    String code = "";
                    for (int i = 0; i < nodesCodeBasedUSD.getLength(); i++) {
                        code = nodesCodeBasedUSD.item(i).getTextContent();
                        if (code.equals("USD")) {
                            rateUSDBasedUSD = nodesRateBasedUSD.item(i).getTextContent().replace(",", ".");
                            rateUSDBasedEUR = nodesRateBasedEUR.item(i).getTextContent().replace(",", ".");
                            rateUSDBasedTRY = nodesRateBasedTRY.item(i).getTextContent().replace(",", ".");
                            rateUSDBasedGBP = nodesRateBasedGBP.item(i).getTextContent().replace(",", ".");
                            rateUSDBasedJPY = nodesRateBasedJPY.item(i).getTextContent().replace(",", ".");
                            rateUSDBasedCNY = nodesRateBasedCNY.item(i).getTextContent().replace(",", ".");
                        }
                        else if (code.equals("EUR")) {
                            rateEURBasedUSD = nodesRateBasedUSD.item(i).getTextContent().replace(",", ".");
                            rateEURBasedEUR = nodesRateBasedEUR.item(i).getTextContent().replace(",", ".");
                            rateEURBasedTRY = nodesRateBasedTRY.item(i).getTextContent().replace(",", ".");
                            rateEURBasedGBP = nodesRateBasedGBP.item(i).getTextContent().replace(",", ".");
                            rateEURBasedJPY = nodesRateBasedJPY.item(i).getTextContent().replace(",", ".");
                            rateEURBasedCNY = nodesRateBasedCNY.item(i).getTextContent().replace(",", ".");
                        }
                        else if (code.equals("TRY")) {
                            rateTRYBasedUSD = nodesRateBasedUSD.item(i).getTextContent().replace(",", ".");
                            rateTRYBasedEUR = nodesRateBasedEUR.item(i).getTextContent().replace(",", ".");
                            rateTRYBasedTRY = nodesRateBasedTRY.item(i).getTextContent().replace(",", ".");
                            rateTRYBasedGBP = nodesRateBasedGBP.item(i).getTextContent().replace(",", ".");
                            rateTRYBasedJPY = nodesRateBasedJPY.item(i).getTextContent().replace(",", ".");
                            rateTRYBasedCNY = nodesRateBasedCNY.item(i).getTextContent().replace(",", ".");
                        }
                        else if (code.equals("GBP")) {
                            rateGBPBasedUSD = nodesRateBasedUSD.item(i).getTextContent().replace(",", ".");
                            rateGBPBasedEUR = nodesRateBasedEUR.item(i).getTextContent().replace(",", ".");
                            rateGBPBasedTRY = nodesRateBasedTRY.item(i).getTextContent().replace(",", ".");
                            rateGBPBasedGBP = nodesRateBasedGBP.item(i).getTextContent().replace(",", ".");
                            rateGBPBasedJPY = nodesRateBasedJPY.item(i).getTextContent().replace(",", ".");
                            rateGBPBasedCNY = nodesRateBasedCNY.item(i).getTextContent().replace(",", ".");
                        }
                        else if (code.equals("JPY")) {
                            rateJPYBasedUSD = nodesRateBasedUSD.item(i).getTextContent().replace(",", ".");
                            rateJPYBasedEUR = nodesRateBasedEUR.item(i).getTextContent().replace(",", ".");
                            rateJPYBasedTRY = nodesRateBasedTRY.item(i).getTextContent().replace(",", ".");
                            rateJPYBasedGBP = nodesRateBasedGBP.item(i).getTextContent().replace(",", ".");
                            rateJPYBasedJPY = nodesRateBasedJPY.item(i).getTextContent().replace(",", ".");
                            rateJPYBasedCNY = nodesRateBasedCNY.item(i).getTextContent().replace(",", ".");
                        }
                        else if (code.equals("CNY")) {
                            rateCNYBasedUSD = nodesRateBasedUSD.item(i).getTextContent().replace(",", ".");
                            rateCNYBasedEUR = nodesRateBasedEUR.item(i).getTextContent().replace(",", ".");
                            rateCNYBasedTRY = nodesRateBasedTRY.item(i).getTextContent().replace(",", ".");
                            rateCNYBasedGBP = nodesRateBasedGBP.item(i).getTextContent().replace(",", ".");
                            rateCNYBasedJPY = nodesRateBasedJPY.item(i).getTextContent().replace(",", ".");
                            rateCNYBasedCNY = nodesRateBasedCNY.item(i).getTextContent().replace(",", ".");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.runOnUiThread(() -> {
                    currencyUSD.setText(rateUSDBasedUSD);
                    currencyEUR.setText(rateEURBasedUSD);
                    currencyTRY.setText(rateTRYBasedUSD);
                    currencyGBP.setText(rateGBPBasedUSD);
                    currencyJPY.setText(rateJPYBasedUSD);
                    currencyCNY.setText(rateCNYBasedUSD);

                    currencyUSD.setEnabled(true);
                    currencyEUR.setEnabled(true);
                    currencyTRY.setEnabled(true);
                    currencyGBP.setEnabled(true);
                    currencyJPY.setEnabled(true);
                    currencyCNY.setEnabled(true);
                });
            }).start();
        }

        // Button Click Events
        backButton.setOnClickListener(view -> {
            // Close Activity
            finish();
        });

        // Currency Events
        currencyUSD.setOnKeyListener((view, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                double valueDouble=Double.parseDouble(currencyUSD.getText().toString());
                double outEUR = Double.parseDouble(rateEURBasedUSD)*valueDouble;
                double outTRY = Double.parseDouble(rateTRYBasedUSD)*valueDouble;
                double outGBP = Double.parseDouble(rateGBPBasedUSD)*valueDouble;
                double outJPY = Double.parseDouble(rateJPYBasedUSD)*valueDouble;
                double outCNY = Double.parseDouble(rateCNYBasedUSD)*valueDouble;
                currencyEUR.setText(String.valueOf(outEUR));
                currencyTRY.setText(String.valueOf(outTRY));
                currencyGBP.setText(String.valueOf(outGBP));
                currencyJPY.setText(String.valueOf(outJPY));
                currencyCNY.setText(String.valueOf(outCNY));
                KeyboardController.hideKeyboard(contextStatic.get(), view);
            }
            return false;
        });
        currencyEUR.setOnKeyListener((view, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                double valueDouble=Double.parseDouble(currencyEUR.getText().toString());
                double outUSD = Double.parseDouble(rateUSDBasedEUR)*valueDouble;
                double outTRY = Double.parseDouble(rateTRYBasedEUR)*valueDouble;
                double outGBP = Double.parseDouble(rateGBPBasedEUR)*valueDouble;
                double outJPY = Double.parseDouble(rateJPYBasedEUR)*valueDouble;
                double outCNY = Double.parseDouble(rateCNYBasedEUR)*valueDouble;
                currencyUSD.setText(String.valueOf(outUSD));
                currencyTRY.setText(String.valueOf(outTRY));
                currencyGBP.setText(String.valueOf(outGBP));
                currencyJPY.setText(String.valueOf(outJPY));
                currencyCNY.setText(String.valueOf(outCNY));
                KeyboardController.hideKeyboard(contextStatic.get(), view);
            }
            return false;
        });
        currencyTRY.setOnKeyListener((view, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                double valueDouble=Double.parseDouble(currencyTRY.getText().toString());
                double outUSD = Double.parseDouble(rateUSDBasedTRY)*valueDouble;
                double outEUR = Double.parseDouble(rateEURBasedTRY)*valueDouble;
                double outGBP = Double.parseDouble(rateGBPBasedTRY)*valueDouble;
                double outJPY = Double.parseDouble(rateJPYBasedTRY)*valueDouble;
                double outCNY = Double.parseDouble(rateCNYBasedTRY)*valueDouble;
                currencyUSD.setText(String.valueOf(outUSD));
                currencyEUR.setText(String.valueOf(outEUR));
                currencyGBP.setText(String.valueOf(outGBP));
                currencyJPY.setText(String.valueOf(outJPY));
                currencyCNY.setText(String.valueOf(outCNY));
                KeyboardController.hideKeyboard(contextStatic.get(), view);
            }
            return false;
        });
        currencyGBP.setOnKeyListener((view, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                double valueDouble=Double.parseDouble(currencyGBP.getText().toString());
                double outUSD = Double.parseDouble(rateUSDBasedGBP)*valueDouble;
                double outEUR = Double.parseDouble(rateEURBasedGBP)*valueDouble;
                double outTRY = Double.parseDouble(rateGBPBasedGBP)*valueDouble;
                double outJPY = Double.parseDouble(rateJPYBasedGBP)*valueDouble;
                double outCNY = Double.parseDouble(rateCNYBasedGBP)*valueDouble;
                currencyUSD.setText(String.valueOf(outUSD));
                currencyEUR.setText(String.valueOf(outEUR));
                currencyTRY.setText(String.valueOf(outTRY));
                currencyJPY.setText(String.valueOf(outJPY));
                currencyCNY.setText(String.valueOf(outCNY));
                KeyboardController.hideKeyboard(contextStatic.get(), view);
            }
            return false;
        });
        currencyJPY.setOnKeyListener((view, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                double valueDouble=Double.parseDouble(currencyJPY.getText().toString());
                double outUSD = Double.parseDouble(rateUSDBasedJPY)*valueDouble;
                double outEUR = Double.parseDouble(rateEURBasedJPY)*valueDouble;
                double outTRY = Double.parseDouble(rateTRYBasedJPY)*valueDouble;
                double outGBP = Double.parseDouble(rateGBPBasedJPY)*valueDouble;
                double outCNY = Double.parseDouble(rateCNYBasedJPY)*valueDouble;
                currencyUSD.setText(String.valueOf(outUSD));
                currencyEUR.setText(String.valueOf(outEUR));
                currencyTRY.setText(String.valueOf(outTRY));
                currencyGBP.setText(String.valueOf(outGBP));
                currencyCNY.setText(String.valueOf(outCNY));
                KeyboardController.hideKeyboard(contextStatic.get(), view);
            }
            return false;
        });
        currencyCNY.setOnKeyListener((view, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                double valueDouble=Double.parseDouble(currencyCNY.getText().toString());
                double outUSD = Double.parseDouble(rateUSDBasedCNY)*valueDouble;
                double outEUR = Double.parseDouble(rateEURBasedCNY)*valueDouble;
                double outTRY = Double.parseDouble(rateTRYBasedCNY)*valueDouble;
                double outGBP = Double.parseDouble(rateGBPBasedCNY)*valueDouble;
                double outJPY = Double.parseDouble(rateJPYBasedCNY)*valueDouble;
                currencyUSD.setText(String.valueOf(outUSD));
                currencyEUR.setText(String.valueOf(outEUR));
                currencyTRY.setText(String.valueOf(outTRY));
                currencyGBP.setText(String.valueOf(outGBP));
                currencyJPY.setText(String.valueOf(outJPY));
                KeyboardController.hideKeyboard(contextStatic.get(), view);
            }
            return false;
        });
    }
}