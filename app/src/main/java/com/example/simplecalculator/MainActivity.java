package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultReference;
    private Button currentButtonReference = null;
    private boolean isOperatorEnteredBefore = false;
    private StringBuilder currentOperand;
    private Stack<Character> operators;
    private Stack<Float> operands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentOperand = new StringBuilder();
        operators = new Stack<>();
        operands = new Stack<>();
        Button btnZero = findViewById(R.id.zero);
        Button btnDoubleZero = findViewById(R.id.doubleZero);
        Button btnOne = findViewById(R.id.one);
        Button btnTwo = findViewById(R.id.two);
        Button btnThree = findViewById(R.id.three);
        Button btnFour = findViewById(R.id.four);
        Button btnFive = findViewById(R.id.five);
        Button btnSix = findViewById(R.id.six);
        Button btnSeven = findViewById(R.id.seven);
        Button btnEight = findViewById(R.id.eight);
        Button btnNine = findViewById(R.id.nine);
        Button btnDot = findViewById(R.id.dot);
        Button btnSignChange = findViewById(R.id.signChange);
        Button btnAdd = findViewById(R.id.Add);
        Button btnSubtract = findViewById(R.id.subtract);
        Button btnMul = findViewById(R.id.mul);
        Button btnDivide = findViewById(R.id.division);
        Button btnPercentage = findViewById(R.id.percentage);
        Button btnEquals = findViewById(R.id.equals);
        Button btnAllClear = findViewById(R.id.clearButton);
        resultReference = findViewById(R.id.ResultTextView);
        btnZero.setOnClickListener(this);
        btnDoubleZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnSignChange.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnPercentage.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnAllClear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero:
                changeResult("0");
                break;
            case R.id.doubleZero:
                changeResult("00");
                break;
            case R.id.one:
                changeResult("1");
                break;
            case R.id.two:
                changeResult("2");
                break;
            case R.id.three:
                changeResult("3");
                break;
            case R.id.four:
                changeResult("4");
                break;
            case R.id.five:
                changeResult("5");
                break;
            case R.id.six:
                changeResult("6");
                break;
            case R.id.seven:
                changeResult("7");
                break;
            case R.id.eight:
                changeResult("8");
                break;
            case R.id.nine:
                changeResult("9");
                break;
            case R.id.dot:
                changeResult(".");
                break;
            case R.id.signChange:
                if(!isOperatorEnteredBefore && !currentOperand.toString().equals("") && !currentOperand.toString().equals("0")){
                    currentOperand.insert(0,"-");
                    resultReference.setText(currentOperand.toString());
                }
                break;
            case R.id.Add:
                currentButtonReference = (Button) v;
                pushOperator('+');
                break;
            case R.id.subtract:
                currentButtonReference = (Button) v;
                pushOperator('-');
                break;
            case R.id.mul:
                currentButtonReference = (Button) v;
                pushOperator('*');
                break;
            case R.id.division:
                currentButtonReference = (Button) v;
                pushOperator('/');
                break;
            case R.id.equals:
                calculateResult();
                break;
            case R.id.clearButton :
                currentOperand.delete(0, currentOperand.length());
                if (currentButtonReference != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    currentButtonReference.setBackground(getDrawable(R.drawable.rounded_corners_inactive));
                }
                currentButtonReference = null;
                isOperatorEnteredBefore = false;
                operands.removeAllElements();
                operators.removeAllElements();
                resultReference.setText("0");
                break;
        }
    }

    private void calculateResult() {
        isOperatorEnteredBefore = false;
        if (currentButtonReference != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentButtonReference.setBackground(getDrawable(R.drawable.rounded_corners_inactive));
        }
        if(!(currentOperand.toString()).equals("")) {
            operands.push(Float.parseFloat(currentOperand.toString()));
            currentOperand.delete(0, currentOperand.length());
        }
        if (!operators.empty() && !operands.empty() ) {
            if(operands.size() == operators.size()){
                operators.pop();
            }
            while(!operators.empty())
            operands.push(applyOp(operators.pop(), operands.pop(), operands.pop()));
        }
        if(!operands.empty()) {
            currentOperand.append((operands.peek()).toString());
            resultReference.setText((operands.peek()).toString());
        }
    }

    private static boolean hasPrecedence(char op1, char op2) {
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private static float applyOp(char operator, float rightOperand, float leftOperand) {
        switch (operator) {
            case '+':
                return leftOperand + rightOperand;
            case '-':
                return leftOperand - rightOperand;
            case '*':
                return leftOperand * rightOperand;
            case '/':
                if (rightOperand == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return leftOperand / rightOperand;
        }
        return 0;
    }

    private void changeResult(String numberPressed) {
        if (currentButtonReference != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentButtonReference.setBackground(getDrawable(R.drawable.rounded_corners_inactive));
        }
        isOperatorEnteredBefore = false;
        if ((currentOperand.toString()).equals("")) {
            if (numberPressed.equals("00")) {
                resultReference.setText("0");
            } else {
                resultReference.setText(numberPressed);
                currentOperand.append(numberPressed);
            }
        } else {
            currentOperand.append(numberPressed);
            resultReference.setText(currentOperand);
        }
    }

    private void pushOperator(char op) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentButtonReference.setBackground(getDrawable(R.drawable.rounded_corners_active));
        }
        if (isOperatorEnteredBefore) {
            operators.pop();
        } else {
            if (!(currentOperand.toString()).equals(""))
                operands.push(Float.parseFloat(currentOperand.toString()));
            currentOperand.delete(0, currentOperand.length());
            isOperatorEnteredBefore = true;
        }
        while (!operators.empty() && hasPrecedence(op, operators.peek())) {
            operands.push(applyOp(operators.pop(), operands.pop(), operands.pop()));
            resultReference.setText((operands.peek()).toString());
        }
        operators.push(op);

    }
}
