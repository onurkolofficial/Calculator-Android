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
import com.onurkol.app.calculator.libs.app.HistoryManager;
import com.onurkol.app.calculator.libs.settings.ThemeManager;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonsPage1Fragment extends Fragment implements AppSettingsInterface {
    ThemeManager themeManager;
    AppPreferenceManager appPreferenceManager;

    HistoryManager historyManager;

    ImageButton dotButton, calc0Button, equalButton, additionButton,
            calc1Button, calc2Button, calc3Button, extractionButton,
            calc4Button, calc5Button, calc6Button, multipleButton,
            calc7Button, calc8Button, calc9Button, divisionButton,
            reverseButton, moduloButton,  clearButton, deleteButton;

    EditText calcShowPrevious, calcShowValue;

    private boolean DOT_RESET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        themeManager = ThemeManager.getInstance();
        appPreferenceManager = AppPreferenceManager.getInstance(requireContext());

        historyManager = HistoryManager.getManager(requireContext());

        View view = inflater.inflate(R.layout.fragment_buttons_pager_1, container, false);

        calcShowPrevious = requireActivity().findViewById(R.id.calcShowPrevious);
        calcShowValue = requireActivity().findViewById(R.id.calcShowValue);
        dotButton = view.findViewById(R.id.dotButton);
        calc0Button = view.findViewById(R.id.calc0Button);
        equalButton = view.findViewById(R.id.equalButton);
        additionButton = view.findViewById(R.id.additionButton);
        calc1Button = view.findViewById(R.id.calc1Button);
        calc2Button = view.findViewById(R.id.calc2Button);
        calc3Button = view.findViewById(R.id.calc3Button);
        extractionButton = view.findViewById(R.id.extractionButton);
        calc4Button = view.findViewById(R.id.calc4Button);
        calc5Button = view.findViewById(R.id.calc5Button);
        calc6Button = view.findViewById(R.id.calc6Button);
        multipleButton = view.findViewById(R.id.multipleButton);
        calc7Button = view.findViewById(R.id.calc7Button);
        calc8Button = view.findViewById(R.id.calc8Button);
        calc9Button = view.findViewById(R.id.calc9Button);
        divisionButton = view.findViewById(R.id.divisionButton);
        reverseButton = view.findViewById(R.id.reverseButton);
        moduloButton = view.findViewById(R.id.moduloButton);
        clearButton = view.findViewById(R.id.clearButton);
        deleteButton = view.findViewById(R.id.deleteButton);

        startAppTheme();

        Pattern pattern = Pattern.compile("([.])");
        Matcher matcher = pattern.matcher(calcShowValue.getText());
        if(matcher.find())
            DOT_RESET = false;
        else
            DOT_RESET = true;

        calc0Button.setOnClickListener(getNumberButtonsClickListener(0));
        calc1Button.setOnClickListener(getNumberButtonsClickListener(1));
        calc2Button.setOnClickListener(getNumberButtonsClickListener(2));
        calc3Button.setOnClickListener(getNumberButtonsClickListener(3));
        calc4Button.setOnClickListener(getNumberButtonsClickListener(4));
        calc5Button.setOnClickListener(getNumberButtonsClickListener(5));
        calc6Button.setOnClickListener(getNumberButtonsClickListener(6));
        calc7Button.setOnClickListener(getNumberButtonsClickListener(7));
        calc8Button.setOnClickListener(getNumberButtonsClickListener(8));
        calc9Button.setOnClickListener(getNumberButtonsClickListener(9));
        clearButton.setOnClickListener(clearButtonClickListener);
        additionButton.setOnClickListener(getProcessButtonsClickListener(" + "));
        extractionButton.setOnClickListener(getProcessButtonsClickListener(" - "));
        multipleButton.setOnClickListener(getProcessButtonsClickListener(" * "));
        divisionButton.setOnClickListener(getProcessButtonsClickListener(" / "));
        moduloButton.setOnClickListener(getProcessButtonsClickListener(" % "));
        deleteButton.setOnClickListener(deleteButtonClickListener);
        equalButton.setOnClickListener(equalsButtonClickListener);
        dotButton.setOnClickListener(dotButtonClickListener);
        reverseButton.setOnClickListener(reverseButtonClickListener);

        return view;
    }

    private void startAppTheme(){
        themeManager.setBackgroundTint(requireContext(), calc0Button);
        themeManager.setBackgroundTint(requireContext(), calc1Button);
        themeManager.setBackgroundTint(requireContext(), calc2Button);
        themeManager.setBackgroundTint(requireContext(), calc3Button);
        themeManager.setBackgroundTint(requireContext(), calc4Button);
        themeManager.setBackgroundTint(requireContext(), calc5Button);
        themeManager.setBackgroundTint(requireContext(), calc6Button);
        themeManager.setBackgroundTint(requireContext(), calc7Button);
        themeManager.setBackgroundTint(requireContext(), calc8Button);
        themeManager.setBackgroundTint(requireContext(), calc9Button);
    }

    View.OnClickListener getNumberButtonsClickListener(int getNumber){
        return view -> {
            String getNumberString = String.valueOf(getNumber);
            String getInputValueString = calcShowValue.getText().toString();

            int selectPos = calcShowValue.getSelectionEnd();

            if(getNumber == 0){
                if(selectPos > 0){
                    String getBefore = String.valueOf(getInputValueString.charAt(selectPos-1));
                    Pattern pattern = Pattern.compile("([ %+*/-])");
                    Matcher matcher = pattern.matcher(getBefore);
                    if(!matcher.find()) {
                        if(calcShowValue.getText().length()>0 && !getInputValueString.equals("0")) {
                            calcShowValue.getText().insert(selectPos, getNumberString);
                            calcShowValue.setSelection(selectPos);
                        }
                    }
                }
            }
            else{
                if(getInputValueString.length() <= 1 && getInputValueString.equals("0")){
                    calcShowValue.setText(getNumberString);
                    calcShowValue.setSelection(calcShowValue.getText().length());
                }
                else{
                    if (selectPos <= -1)
                        calcShowValue.append(getNumberString);
                    else
                        calcShowValue.getText().insert(selectPos, getNumberString);
                }
            }

            String saveNewValue = calcShowValue.getText().toString();
            appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, saveNewValue);
        };
    }

    View.OnClickListener clearButtonClickListener = view -> {
        calcShowPrevious.setText("");
        calcShowValue.setText("0");
        calcShowValue.requestFocus();
        calcShowValue.setSelection(calcShowValue.getText().length());
        DOT_RESET = true;

        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_HISTORY, "");
        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, "");
    };

    View.OnClickListener getProcessButtonsClickListener(String getProcess) {
        return view -> {
            String getInputValueString = calcShowValue.getText().toString();
            int getValueInputLength = getInputValueString.length();

            if(getValueInputLength > 0){
                if(getValueInputLength > 2){
                    String checkInputValueProcess = String.valueOf(getInputValueString.charAt(getValueInputLength-2));

                    Pattern pattern = Pattern.compile("([ ().0-9])");
                    Matcher matcher = pattern.matcher(checkInputValueProcess);
                    if(matcher.matches())
                        calcShowValue.append(getProcess);
                    else {
                        int s = getValueInputLength - 2,
                                p = getValueInputLength - 1;
                        calcShowValue.getText().delete(s, p);
                        calcShowValue.getText().insert(s, getProcess.trim());
                    }
                }
                else
                    calcShowValue.append(getProcess);
            }
            calcShowValue.setSelection(calcShowValue.getText().length());

            DOT_RESET = true;

            String saveNewValue = calcShowValue.getText().toString();
            appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, saveNewValue);
        };
    }

    View.OnClickListener deleteButtonClickListener = view -> {
        if(calcShowValue.getText().length()>0) {
            int selectPos = calcShowValue.getSelectionEnd();
            if (selectPos > 0) {
                String getBefore = String.valueOf(calcShowValue.getText().charAt(selectPos-1));
                if(getBefore.equals("."))
                    DOT_RESET = true;

                calcShowValue.getText().delete(selectPos - 1, selectPos);
            }
        }

        String saveNewValue = calcShowValue.getText().toString();
        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, saveNewValue);
    };

    View.OnClickListener dotButtonClickListener = view -> {
        String getInputValueString = calcShowValue.getText().toString();

        int selectPos = calcShowValue.getSelectionEnd();

        if(DOT_RESET) {
            if(selectPos > 0){
                String getBefore = String.valueOf(getInputValueString.charAt(selectPos-1));
                Pattern pattern = Pattern.compile("([ .%+*/-])");
                Matcher matcher = pattern.matcher(getBefore);
                if(!matcher.find()) {
                    calcShowValue.getText().insert(selectPos, ".");
                    calcShowValue.setSelection(calcShowValue.getText().length());

                    DOT_RESET = false;
                }
            }
        }

        String saveNewValue = calcShowValue.getText().toString();
        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, saveNewValue);
    };

    View.OnClickListener reverseButtonClickListener = view -> {
        String getInputValueString = calcShowValue.getText().toString();

        if (getInputValueString.length() > 0) {
            Pattern pattern = Pattern.compile("([0-9]{1,100})");
            Matcher matcher = pattern.matcher(getInputValueString);

            String val = "";
            int i = 0;
            while (matcher.find()){
                val = matcher.group(i);
                i++;
            }

            if(getInputValueString.contains("-"+val))
                calcShowValue.setText(getInputValueString.replace("-"+val, val));
            else
                calcShowValue.setText(getInputValueString.replace(val, "-"+val));

            calcShowValue.setSelection(calcShowValue.getText().length());
        }
    };

    View.OnClickListener equalsButtonClickListener = view -> {
        String getInputValueString = calcShowValue.getText().toString();
        if(getInputValueString.isEmpty())
            getInputValueString="0";

        Pattern pattern = Pattern.compile("([ .%+*/-])");
        Matcher matcher = pattern.matcher(getInputValueString);
        String topStr;
        if(matcher.find()) {
            String ch = String.valueOf(getInputValueString.charAt(getInputValueString.length()-1));

            if (getInputValueString.length() > 2) {
                String ch2 = String.valueOf(getInputValueString.charAt(getInputValueString.length()-2));
                String ch3 = String.valueOf(getInputValueString.charAt(getInputValueString.length()-3));
                topStr = ch3+ch2+ch;
            }
            else if (getInputValueString.length() > 1) {
                String ch2 = String.valueOf(getInputValueString.charAt(0));
                topStr = ch2+ch;
            }
            else
                topStr = ch;

            if(topStr.equals(" + ") || topStr.equals(" +") || topStr.equals("+ ") || ch.equals("+") ||
                    topStr.equals(" - ") || topStr.equals(" -") || topStr.equals("- ") || ch.equals("-") ||
                    ch.equals("."))
                getInputValueString += "0";
            else if(topStr.equals(" * ") || topStr.equals(" *") || topStr.equals("* ") || ch.equals("*") ||
                    topStr.equals(" / ") || topStr.equals(" /") || topStr.equals("/ ") || ch.equals("/") ||
                    topStr.equals(" % ") || topStr.equals(" %") || topStr.equals("% ") || ch.equals("%"))
                getInputValueString += "1";
        }


        String convertedValue = "";
        try {
            Expression e = new ExpressionBuilder(getInputValueString).build();

            String saveNewValue = String.valueOf(e.evaluate());
            convertedValue = saveNewValue.replace(".0","");

            historyManager.addHistory(getInputValueString, convertedValue);
        }
        catch (Exception e) {
            convertedValue = requireContext().getString(R.string.str_error);
        }
        calcShowPrevious.setText(getInputValueString);
        calcShowValue.setText(convertedValue);
        calcShowValue.setSelection(0);
        //calcShowValue.setSelection(calcShowValue.getText().length());

        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_HISTORY, getInputValueString);
        appPreferenceManager.setPreference(_APP_KEY_CALCULATOR_CURRENT_VALUE, convertedValue);
    };
}
