package com.onurkol.app.calculator.fragments.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.activity.HistoryActivity;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.onurkol.app.calculator.controllers.HistoryController.addHistory;

public class ButtonsPage1Fragment extends Fragment {

    Button calc0Button,calc1Button,calc2Button,calc3Button,calc4Button,calc5Button,calc6Button,
            calc7Button,calc8Button,calc9Button,calcCButton,calcDelButton,negPosButton,percentButton,
            colonButton,divisionButton,multipleButton,extractionButton,additionButton,equalButton;
    EditText calcShowProcess, calcShowValue;

    boolean PROCESS_RESET_COLON=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_buttons_page1, container, false);

        // Get Elements
        calcShowProcess=getActivity().findViewById(R.id.calcShowProcess);
        calcShowValue=getActivity().findViewById(R.id.calcShowValue);
        calc0Button=view.findViewById(R.id.calc0Button);
        calc1Button=view.findViewById(R.id.calc1Button);
        calc2Button=view.findViewById(R.id.calc2Button);
        calc3Button=view.findViewById(R.id.calc3Button);
        calc4Button=view.findViewById(R.id.calc4Button);
        calc5Button=view.findViewById(R.id.calc5Button);
        calc6Button=view.findViewById(R.id.calc6Button);
        calc7Button=view.findViewById(R.id.calc7Button);
        calc8Button=view.findViewById(R.id.calc8Button);
        calc9Button=view.findViewById(R.id.calc9Button);
        calcCButton=view.findViewById(R.id.clearButton);
        calcDelButton=view.findViewById(R.id.deleteButton);
        negPosButton=view.findViewById(R.id.negPosButton);
        percentButton=view.findViewById(R.id.percentButton);
        colonButton=view.findViewById(R.id.colonButton);
        divisionButton=view.findViewById(R.id.divisionButton);
        multipleButton=view.findViewById(R.id.multipleButton);
        extractionButton=view.findViewById(R.id.extractionButton);
        additionButton=view.findViewById(R.id.additionButton);
        equalButton=view.findViewById(R.id.equalButton);

        // Button Click Events
        // Number Buttons
        calc0Button.setOnClickListener(numberButtonsClickListener);
        calc1Button.setOnClickListener(numberButtonsClickListener);
        calc2Button.setOnClickListener(numberButtonsClickListener);
        calc3Button.setOnClickListener(numberButtonsClickListener);
        calc4Button.setOnClickListener(numberButtonsClickListener);
        calc5Button.setOnClickListener(numberButtonsClickListener);
        calc6Button.setOnClickListener(numberButtonsClickListener);
        calc7Button.setOnClickListener(numberButtonsClickListener);
        calc8Button.setOnClickListener(numberButtonsClickListener);
        calc9Button.setOnClickListener(numberButtonsClickListener);
        // Clear Button
        calcCButton.setOnClickListener(clearButtonClickListener);
        // Delete Button
        calcDelButton.setOnClickListener(deleteButtonClickListener);
        // Process Buttons
        divisionButton.setOnClickListener(processButtonClickListener);
        multipleButton.setOnClickListener(processButtonClickListener);
        extractionButton.setOnClickListener(processButtonClickListener);
        additionButton.setOnClickListener(processButtonClickListener);
        percentButton.setOnClickListener(processButtonClickListener);
        // -/+ Button
        negPosButton.setOnClickListener(negposButtonClickListener);
        // Colon Button
        colonButton.setOnClickListener(colonButtonClickListener);
        // Equal Button
        equalButton.setOnClickListener(equalButtonClickListener);

        return view;
    }

    // Number Buttons Click Listener
    View.OnClickListener numberButtonsClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Number
            String getNumString=((Button)view).getText().toString();
            // Check Selection Position
            int selectPos=calcShowValue.getSelectionEnd();
            if(selectPos<=-1)
                calcShowValue.append(getNumString);
            else
                calcShowValue.getText().insert(selectPos,getNumString);
        }
    };

    // Clear Button Click Listener
    View.OnClickListener clearButtonClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Reset Variables
            PROCESS_RESET_COLON=true;
            calcShowProcess.setText("");
            calcShowValue.setText("");
        }
    };

    // Colon Button Click Listener
    View.OnClickListener deleteButtonClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Check String Exist
            if(calcShowValue.getText().length()>0) {
                // Check Selection Position
                int selectPos = calcShowValue.getSelectionEnd();
                if (selectPos > 0)
                    calcShowValue.getText().delete(selectPos - 1, selectPos);
            }
        }
    };

    // Process Buttons Click Listener
    View.OnClickListener processButtonClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Process Clicked to Reset Variables
            PROCESS_RESET_COLON=true;
            // Get String
            String getProcString=((Button)view).getText().toString();
            String getString=calcShowValue.getText().toString();
            int getStringLength=getString.length();
            // Check Exist Process
            if(checkValueStringLength()) {
                // Get End Char
                String getEndChar = String.valueOf(getString.charAt(getStringLength-1));
                // Check Process Exist
                Pattern pattern=Pattern.compile("[0-9i()]");
                Matcher matcher=pattern.matcher(getEndChar);
                if(matcher.matches()) {
                    calcShowValue.append(getProcString);
                }
                else{
                    calcShowValue.getText().delete(getStringLength-1, getStringLength);
                    calcShowValue.getText().insert(getStringLength-1, getProcString);
                }
            }
        }
    };

    // -/+ Button Click
    View.OnClickListener negposButtonClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String getVal=calcShowValue.getText().toString();
            if(checkValueStringLength()) {
                Pattern pattern=Pattern.compile("([0-9]{1,100})");
                Matcher matcher=pattern.matcher(getVal);

                String val="";
                int i=0;
                while (matcher.find()){
                    val=matcher.group(i);
                    i++;
                }
                // Find Match
                if(getVal.contains("-"+val))
                    calcShowValue.setText(getVal.replace("-"+val,val));
                else
                    calcShowValue.setText(getVal.replace(val,"-"+val));

                // Reset Selection Position
                calcShowValue.setSelection(calcShowValue.getText().length());
            }
        }
    };

    // Colon Button Click
    View.OnClickListener colonButtonClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Check exists colon
            if(checkValueStringLength()) {
                if(PROCESS_RESET_COLON){
                    // Reset Variable
                    PROCESS_RESET_COLON=false;
                    calcShowValue.append(".");
                }
            }
        }
    };

    // Equal Button Click
    View.OnClickListener equalButtonClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Updating Preference Data
            HistoryActivity.updatePreferenceHistoryData();
            // Convert x to *
            String expressString=calcShowValue.getText().toString();
            String convertExpressString=expressString.replace("x","*");
            // Process
            Expression express=new Expression(convertExpressString);
            String value=String.valueOf(express.calculate());
            // Check value int or float ('X.0' replaced to 'X')
            String convertValue=value.replace(".0","");
            // Show Process to History Text
            calcShowProcess.setText(expressString);
            // Show Process Value
            calcShowValue.setText(convertValue);
            // Add History
            addHistory(expressString,convertValue);
            // Set Selection Position
            calcShowValue.setSelection(convertValue.length());
        }
    };

    private boolean checkValueStringLength(){
        // Get String
        String getString=calcShowValue.getText().toString();
        int getStringLength=getString.length();
        // Check exists colon
        return getStringLength>0;
    }
}