package com.onurkol.app.calculator.fragments.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.onurkol.app.calculator.R;

public class ButtonsPage2Fragment extends Fragment {

    Button logButton,cotButton,degButton,radButton,absButton,sinButton,cosButton,
            tanButton,piButton,eButton,factButton,powerButton,sqrtButton,lnButton,
            brackOnButton,brackOffButton,sinhButton,asinButton,acosButton,atanButton;
    EditText calcShowProcess, calcShowValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_buttons_page2, container, false);

        // Get Elements
        calcShowProcess=getActivity().findViewById(R.id.calcShowProcess);
        calcShowValue=getActivity().findViewById(R.id.calcShowValue);
        logButton=view.findViewById(R.id.logButton);
        cotButton=view.findViewById(R.id.cotButton);
        degButton=view.findViewById(R.id.degButton);
        radButton=view.findViewById(R.id.radButton);
        absButton=view.findViewById(R.id.absButton);
        sinButton=view.findViewById(R.id.sinButton);
        cosButton=view.findViewById(R.id.cosButton);
        tanButton=view.findViewById(R.id.tanButton);
        piButton=view.findViewById(R.id.piButton);
        eButton=view.findViewById(R.id.eButton);
        factButton=view.findViewById(R.id.factButton);
        powerButton=view.findViewById(R.id.powerButton);
        sqrtButton=view.findViewById(R.id.sqrtButton);
        lnButton=view.findViewById(R.id.lnButton);
        brackOnButton=view.findViewById(R.id.brackOnButton);
        brackOffButton=view.findViewById(R.id.brackOffButton);
        sinhButton=view.findViewById(R.id.sinhButton);
        asinButton=view.findViewById(R.id.asinButton);
        acosButton=view.findViewById(R.id.acosButton);
        atanButton=view.findViewById(R.id.atanButton);

        // Button Click Events
        cotButton.setOnClickListener(printFunctionsClickListener);
        degButton.setOnClickListener(printFunctionsClickListener);
        radButton.setOnClickListener(printFunctionsClickListener);
        absButton.setOnClickListener(printFunctionsClickListener);
        sinButton.setOnClickListener(printFunctionsClickListener);
        cosButton.setOnClickListener(printFunctionsClickListener);
        tanButton.setOnClickListener(printFunctionsClickListener);
        eButton.setOnClickListener(printTextOnlyClickListener);
        lnButton.setOnClickListener(printFunctionsClickListener);
        brackOnButton.setOnClickListener(printTextOnlyClickListener);
        brackOffButton.setOnClickListener(printTextOnlyClickListener);
        sinhButton.setOnClickListener(printFunctionsClickListener);
        asinButton.setOnClickListener(printFunctionsClickListener);
        acosButton.setOnClickListener(printFunctionsClickListener);
        atanButton.setOnClickListener(printFunctionsClickListener);
        // Custom Events
        logButton.setOnClickListener(view1 -> calcShowValue.append("log10("));
        piButton.setOnClickListener(view1 -> calcShowValue.append("pi"));
        factButton.setOnClickListener(view1 -> {
            if(checkValueStringLength())
                calcShowValue.append("!");
        });
        powerButton.setOnClickListener(view1 -> {
            if(checkValueStringLength())
                calcShowValue.append("^");
        });
        sqrtButton.setOnClickListener(view1 -> calcShowValue.append("sqrt("));

        return view;
    }

    // Brackets,e Button Click Listener
    View.OnClickListener printTextOnlyClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Text
            String getTextString=((Button)view).getText().toString();
            // Check Selection Position
            int selectPos=calcShowValue.getSelectionEnd();
            if(selectPos<=-1)
                calcShowValue.append(getTextString);
            else
                calcShowValue.getText().insert(selectPos,getTextString);
        }
    };

    // Function Buttons, Click Listener
    View.OnClickListener printFunctionsClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Text
            String getNameString=((Button)view).getText().toString().toLowerCase();
            // Convert Function Type
            String convertFunc=getNameString+"(";
            // Print Text
            calcShowValue.append(convertFunc);
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