package com.onurkol.app.calculator.fragment.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.onurkol.app.calculator.R;
import com.onurkol.app.calculator.interfaces.AppSettingsInterface;
import com.onurkol.app.calculator.libs.AppPreferenceManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonsPage2Fragment extends Fragment implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;

    ImageButton calcABSButton, calcSQRTButton, calcSIGNButton, calcBRACKONButton, calcBRACKOFFButton,
            calcLOGButton, calcLOG2Button, calcLOG10Button, calcEXPButton,
            calcSINButton, calcASINButton, calcSINHButton, calcCBRTButton,
            calcCOSButton, calcACOSButton, calcCOSHButton, calcFLOORButton,
            calcTANButton, calcATANButton, calcTANHButton, calcCEILButton;

    EditText calcShowPrevious, calcShowValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(requireContext());

        View view = inflater.inflate(R.layout.fragment_buttons_pager_2, container, false);

        calcShowPrevious = requireActivity().findViewById(R.id.calcShowPrevious);
        calcShowValue = requireActivity().findViewById(R.id.calcShowValue);
        calcABSButton = view.findViewById(R.id.calcABSButton);
        calcSQRTButton = view.findViewById(R.id.calcSQRTButton);
        calcSIGNButton = view.findViewById(R.id.calcSIGNButton);
        calcBRACKONButton = view.findViewById(R.id.calcBRACKONButton);
        calcBRACKOFFButton = view.findViewById(R.id.calcBRACKOFFButton);
        calcLOGButton = view.findViewById(R.id.calcLOGButton);
        calcLOG2Button = view.findViewById(R.id.calcLOG2Button);
        calcLOG10Button = view.findViewById(R.id.calcLOG10Button);
        calcEXPButton = view.findViewById(R.id.calcEXPButton);
        calcSINButton = view.findViewById(R.id.calcSINButton);
        calcASINButton = view.findViewById(R.id.calcASINButton);
        calcSINHButton = view.findViewById(R.id.calcSINHButton);
        calcCBRTButton = view.findViewById(R.id.calcCBRTButton);
        calcCOSButton = view.findViewById(R.id.calcCOSButton);
        calcACOSButton = view.findViewById(R.id.calcACOSButton);
        calcCOSHButton = view.findViewById(R.id.calcCOSHButton);
        calcFLOORButton = view.findViewById(R.id.calcFLOORButton);
        calcTANButton = view.findViewById(R.id.calcTANButton);
        calcATANButton = view.findViewById(R.id.calcATANButton);
        calcTANHButton = view.findViewById(R.id.calcTANHButton);
        calcCEILButton = view.findViewById(R.id.calcCEILButton);

        startAppTheme();

        calcBRACKONButton.setOnClickListener(printButtonsClickListener(" ("));
        calcBRACKOFFButton.setOnClickListener(printButtonsClickListener(") "));
        calcABSButton.setOnClickListener(getProcessButtonsClickListener(" abs("));
        calcSQRTButton.setOnClickListener(getProcessButtonsClickListener(" sqrt("));
        calcSIGNButton.setOnClickListener(getProcessButtonsClickListener(" signum("));
        calcLOGButton.setOnClickListener(getProcessButtonsClickListener(" log("));
        calcLOG2Button.setOnClickListener(getProcessButtonsClickListener(" log2("));
        calcLOG10Button.setOnClickListener(getProcessButtonsClickListener(" log10("));
        calcEXPButton.setOnClickListener(getProcessButtonsClickListener(" exp("));
        calcSINButton.setOnClickListener(getProcessButtonsClickListener(" sin("));
        calcASINButton.setOnClickListener(getProcessButtonsClickListener(" asin("));
        calcSINHButton.setOnClickListener(getProcessButtonsClickListener(" sinh("));
        calcCBRTButton.setOnClickListener(getProcessButtonsClickListener(" cbrt("));
        calcCOSButton.setOnClickListener(getProcessButtonsClickListener(" cos("));
        calcACOSButton.setOnClickListener(getProcessButtonsClickListener(" acos("));
        calcCOSHButton.setOnClickListener(getProcessButtonsClickListener(" cosh("));
        calcFLOORButton.setOnClickListener(getProcessButtonsClickListener(" floor("));
        calcTANButton.setOnClickListener(getProcessButtonsClickListener(" tan("));
        calcATANButton.setOnClickListener(getProcessButtonsClickListener(" atan("));
        calcTANHButton.setOnClickListener(getProcessButtonsClickListener(" tanh("));
        calcCEILButton.setOnClickListener(getProcessButtonsClickListener(" ceil("));

        return view;
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(requireContext(), calcABSButton);
        themeManager.setBackgroundTint(requireContext(), calcSQRTButton);
        themeManager.setBackgroundTint(requireContext(), calcSIGNButton);
        themeManager.setBackgroundTint(requireContext(), calcLOGButton);
        themeManager.setBackgroundTint(requireContext(), calcLOG2Button);
        themeManager.setBackgroundTint(requireContext(), calcLOG10Button);
        themeManager.setBackgroundTint(requireContext(), calcSINButton);
        themeManager.setBackgroundTint(requireContext(), calcASINButton);
        themeManager.setBackgroundTint(requireContext(), calcSINHButton);
        themeManager.setBackgroundTint(requireContext(), calcCOSButton);
        themeManager.setBackgroundTint(requireContext(), calcACOSButton);
        themeManager.setBackgroundTint(requireContext(), calcCOSHButton);
        themeManager.setBackgroundTint(requireContext(), calcTANButton);
        themeManager.setBackgroundTint(requireContext(), calcATANButton);
        themeManager.setBackgroundTint(requireContext(), calcTANHButton);
    }

    View.OnClickListener printButtonsClickListener(String getString) {
        return view -> {
            String getInputValueString = calcShowValue.getText().toString();
            int selectPos = calcShowValue.getSelectionEnd();

            if(getInputValueString.equals("0") || getInputValueString.isEmpty())
                calcShowValue.setText(getString);
            else
                calcShowValue.getText().insert(selectPos, getString);
            calcShowValue.setSelection(calcShowValue.getText().length());

        };
    }
    View.OnClickListener getProcessButtonsClickListener(String getProcess) {
        return view -> {
            String getInputValueString = calcShowValue.getText().toString();
            String saveNewValue = "";

            if(getInputValueString.equals("0") || getInputValueString.isEmpty()) {
                calcShowValue.setText(getProcess);
                saveNewValue = getProcess;
            }
            else {
                Pattern pattern = Pattern.compile("([ .%+*/-])");
                Matcher matcher = pattern.matcher(getInputValueString);
                if(matcher.find()) {
                    String ch = String.valueOf(getInputValueString.charAt(getInputValueString.length() - 1));
                    String topStr;

                    if (getInputValueString.length() > 2) {
                        String ch2 = String.valueOf(getInputValueString.charAt(getInputValueString.length() - 2));
                        String ch3 = String.valueOf(getInputValueString.charAt(getInputValueString.length() - 3));
                        topStr = ch3 + ch2 + ch;
                    } else if (getInputValueString.length() > 1) {
                        String ch2 = String.valueOf(getInputValueString.charAt(0));
                        topStr = ch2 + ch;
                    } else
                        topStr = ch;

                    if(topStr.equals(" + ") || topStr.equals(" +") || topStr.equals("+ ") || ch.equals("+") ||
                            topStr.equals(" - ") || topStr.equals(" -") || topStr.equals("- ") || ch.equals("-") ||
                            topStr.equals(" * ") || topStr.equals(" *") || topStr.equals("* ") || ch.equals("*") ||
                            topStr.equals(" / ") || topStr.equals(" /") || topStr.equals("/ ") || ch.equals("/") ||
                            topStr.equals(" % ") || topStr.equals(" %") || topStr.equals("% ") || ch.equals("%"))
                        saveNewValue = getInputValueString+getProcess;
                }
            }

            calcShowValue.setText(saveNewValue);
            calcShowValue.setSelection(calcShowValue.getText().length());

            appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, saveNewValue);
        };
    }
}
