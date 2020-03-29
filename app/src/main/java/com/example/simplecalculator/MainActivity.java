package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultReference;
    Button currentButtonReference = null;
    boolean isOperatorEnteredBefore = false;
    StringBuilder currentOperand;
    Stack<Character> operators = new Stack<Character>();
    Stack<Integer> operands = new Stack<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentOperand = new StringBuilder();
        Button btnZero = (Button) findViewById(R.id.zero);
        Button btnDoubleZero = (Button) findViewById(R.id.doubleZero);
        Button btnOne = (Button) findViewById(R.id.one);
        Button btnTwo = (Button) findViewById(R.id.two);
        Button btnThree = (Button) findViewById(R.id.three);
        Button btnFour = (Button) findViewById(R.id.four);
        Button btnFive = (Button) findViewById(R.id.five);
        Button btnSix = (Button) findViewById(R.id.six);
        Button btnSeven = (Button) findViewById(R.id.seven);
        Button btnEight = (Button) findViewById(R.id.eight);
        Button btnNine = (Button) findViewById(R.id.nine);
        Button btnAdd = (Button) findViewById(R.id.Add);
        Button btnSubtract = (Button) findViewById(R.id.subtract);
        Button btnMul = (Button) findViewById(R.id.mul);
        Button btnDivide = (Button) findViewById(R.id.division);
        Button btnPercentage = (Button) findViewById(R.id.percentage);
        Button btnEquals = (Button) findViewById(R.id.equals);
        Button btnAllClear = (Button)findViewById(R.id.clearButton);
        resultReference = (TextView) findViewById(R.id.ResultTextView);
        btnZero.setOnClickListener(this);
        btnDoubleZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
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
                resultReference.setText(resultReference.getText().toString() + ".");
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
        }
    }

    private void calculateResult() {
        isOperatorEnteredBefore = false;
        if (currentButtonReference != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentButtonReference.setBackground(getDrawable(R.drawable.rounded_corners_inactive));
        }
        if(!(currentOperand.toString()).equals(""))
        operands.push(Integer.parseInt(currentOperand.toString()));
        if (!operators.empty() && !operands.empty() ) {
            if(operands.size() == operators.size()){
                operators.pop();
            }
            while(!operators.empty())
            operands.push(applyOp(operators.pop(), operands.pop(), operands.pop()));
        }
        if(!operands.empty())
        resultReference.setText((operands.peek()).toString());
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static int applyOp(char operator, int rightOperand, int leftOperand) {
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
        if (isOperatorEnteredBefore == true) {
            operators.pop();
        } else {
            if (!(currentOperand.toString()).equals(""))
                operands.push(Integer.parseInt(currentOperand.toString()));
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
